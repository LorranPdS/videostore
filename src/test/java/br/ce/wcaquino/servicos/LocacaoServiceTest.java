package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}

	// esse m�todo aqui s� n�o vai funcionar se for s�bado
	@Test
	public void deveAlugarFilme() throws Exception {

		// Ao testarmos no s�bado
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 4.0));
		
		// acao
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		// cen�rio
		List<Filme> filmes = Arrays.asList(new Filme("Filme 2", 1, 4.0));
		
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
		Usuario usuario = new Usuario("Usu�rio 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// a��o
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0));
		
		// a��o
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// verifica��o
		// O valor seria o seguinte: 4+4+3=11
		assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0));
		
		// a��o
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// verifica��o
		// O valor seria o seguinte: 4+4+3+2=13
		assertThat(resultado.getValor(), is(13.0));
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0));
			
		// a��o
		Locacao resultado = service.alugarFilme(usuario, filmes);
			
		// verifica��o
		// O valor seria o seguinte: 4+4+3+2+1=14
		assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void devePagarZeroPctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0),
				new Filme("Filme 2", 2, 4.0), 
				new Filme("Filme 3", 2, 4.0),
				new Filme("Filme 4", 2, 4.0),
				new Filme("Filme 5", 2, 4.0),
				new Filme("Filme 6", 2, 4.0));
		
		// a��o
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		// verifica��o
		// O valor seria o seguinte: 4+4+3+2+1+0=14
		assertThat(resultado.getValor(), is(14.0));
	}
	
	// Solu��o 1: tirar a anota��o de teste do m�todo abaixo (n�o recomendado porque se voc� tiver uma bateria muito grande de testes, voc� vai se esquecer que esse teste foi comentado)
	// Solu��o 2: utilizar a anota��o @Ignore abaixo da anota��o @Test (a diferen�a � que ela vai dizer que os 9 testes foram executados mas vai dizer que um deles foi pulado [skipped])
	// 		- com essa anota��o, ele diz que existe o teste mas o teste n�o foi executado. Vamos evitar que ao chegar no s�bado tenhamos que trocar o @Ignore pro outro m�todo
	// Solu��o 3: vamos usar o Assumptions e tirar o @Ignore
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		
		// vai ser semelhante ao @Ignore porque abaixo temos uma l�gica (troque o dia para s�bado no seu computador para testar)
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));
		
		// a��o
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verifica��o
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
}
