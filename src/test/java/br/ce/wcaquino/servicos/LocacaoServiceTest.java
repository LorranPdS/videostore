package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterData;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.runners.ParallelRunner;
import br.ce.wcaquino.utils.DataUtils;

@RunWith(ParallelRunner.class)
public class LocacaoServiceTest {

	@InjectMocks @Spy
	private LocacaoService service;
	
	@Mock
	private SPCService spc;
	@Mock
	private LocacaoDao dao;
	@Mock
	private EmailService email;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		initMocks(this);
		System.out.println("Iniciando 2...");
	}
	
	@After
	public void tearDown() {
		System.out.println("Finalizando 2...");
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().comValor(5.0).agora());
		
		Mockito.doReturn(DataUtils.obterData(28, 4, 2017)).when(service).obterData();
		
		// ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterData(29, 4, 2017)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilmeSemEstoque().agora());
		
		// ação
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		// cenário
		List<Filme> filmes = asList(umFilme().agora());
		
		// ação
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio"));
		}
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		
		// cenário
		Usuario usuario = umUsuario().agora();
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// ação
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		Mockito.doReturn(DataUtils.obterData(29, 4, 2017)).when(service).obterData();
		
		// ação
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verificação
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		
		// ação
		try {
			service.alugarFilme(usuario, filmes);
		
		// verificação
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuário negativado"));
		}
		
		Mockito.verify(spc).possuiNegativacao(usuario);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// cenário
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora();
		Usuario usuario3 = umUsuario().comNome("Outro atrasado").agora();
		
		List<Locacao> locacoes = Arrays.asList(
				umLocacao()
					.atrasado()
					.comUsuario(usuario)
					.agora(),
				umLocacao()
					.comUsuario(usuario2)
					.agora(),
				umLocacao()
					.atrasado()
					.comUsuario(usuario3)
					.agora(),
				umLocacao()
					.atrasado()
					.comUsuario(usuario3)
					.agora()
					);
		
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		// ação
		service.notificarAtrasos();
		
		// verificação
		verify(email, times(3)).notificarAtraso(Mockito.any(Usuario.class));
		verify(email).notificarAtraso(usuario);
		verify(email, Mockito.atLeastOnce()).notificarAtraso(usuario3);
		
		verify(email, Mockito.never()).notificarAtraso(usuario2);
		verifyNoMoreInteractions(email);
	}
	
	@Test
	public void deveTratarErroNoSPC() throws Exception {
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha cadastrófica"));
		
		// verificação
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente");
		
		// ação
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void deveProrrogaUmaLocacao() {
		// cenário
		Locacao locacao = umLocacao().agora();
		
		// ação
		service.prorrogarLocacao(locacao, 3);
		
		// verificação
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();
		
		error.checkThat(locacaoRetornada.getValor(), is(12.0));
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3));
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		// cenário
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		// ação
		Class<LocacaoService> clazz = LocacaoService.class;
		Method metodo = clazz.getDeclaredMethod("calcularValorLocacao", List.class);
		metodo.setAccessible(true);
		Double valor = (Double) metodo.invoke(service, filmes);
		
		// verificação
		Assert.assertThat(valor, is(4.0));
	}
}
