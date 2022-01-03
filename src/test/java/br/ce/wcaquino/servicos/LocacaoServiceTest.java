package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	private SPCService spc;
	private LocacaoDao dao;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		dao = Mockito.mock(LocacaoDao.class);
		service.setLocacaoDAO(dao);
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		// Ao testarmos no s�bado
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());
		
		// a��o
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verifica��o
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		// cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());
		
		// a��o
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		// cen�rio
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		// a��o
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		// cen�rio
		Usuario usuario = umUsuario().agora();
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// a��o
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		// a��o
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verifica��o
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = umUsuario().agora();
//		Usuario usuario2 = umUsuario().comNome("Usu�rio 2").agora(); -> caso queira testar para um usu�rio diferente
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		/*
		 * A leitura abaixo � a seguinte:
		 * Quando o spc.possuiNegativacao for chamado passando como par�metro um usu�rio,
		 * ent�o retorne (thenReturn) true
		 * 
		 * Dessa forma conseguimos fazer a altera��o de comportamento de um objeto mockado,
		 * que no nosso exemplo, como sempre iria retornar false, ele ir� retornar true abaixo
		 */
		when(spc.possuiNegativacao(usuario)).thenReturn(true);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Usu�rio negativado");
		
		// a��o
		service.alugarFilme(usuario, filmes);
//		service.alugarFilme(usuario2, filmes); -> relativo ao teste com usuario2
	}
}
