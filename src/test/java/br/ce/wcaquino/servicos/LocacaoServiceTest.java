package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class})
public class LocacaoServiceTest {

	@InjectMocks
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
		service = PowerMockito.spy(service); // após feito isso, posso mockar o método que tinha criado
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().comValor(5.0).agora());
		
//		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));
		
		Calendar calendar = Calendar.getInstance(); // Esse .getInstance() é uma instanciação que equivale ao 'new Date()' para o Date
		calendar.set(Calendar.DAY_OF_MONTH, 28);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2017);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		// ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//		error.checkThat(locacao.getDataLocacao(), ehHoje());
//		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));
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
		
//		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));
		Calendar calendar = Calendar.getInstance(); // Esse .getInstance() é uma instanciação que equivale ao 'new Date()' para o Date
		calendar.set(Calendar.DAY_OF_MONTH, 29);
		calendar.set(Calendar.MONTH, Calendar.APRIL);
		calendar.set(Calendar.YEAR, 2017);
		PowerMockito.mockStatic(Calendar.class);
		PowerMockito.when(Calendar.getInstance()).thenReturn(calendar);
		
		// ação
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verificação
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
//		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();
		
		// Para verificar a chamada de métodos estáticos, a anotação seria a que está abaixo
		PowerMockito.verifyStatic(Calendar.class, Mockito.times(2));
		Calendar.getInstance();
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
//		Mockito.verify(spc).possuiNegativacao(usuario2);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// cenário
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora(); //-> essa seria a linha do unhappy path para termos certeza que não está sendo gerado um falso positivo
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
		verify(email, times(3)).notificarAtraso(Mockito.any(Usuario.class)); // esse any() não serve apenas para objetos, mas você verificando-o, verá que serve para boolean, double, int, ...
		verify(email).notificarAtraso(usuario); // veja que fizemos uma verificação do mesmo método descrito na ação

//		verify(email, Mockito.times(2)).notificarAtraso(usuario3); // possibilidade 1: verifica 2 vezes a notificação
//		verify(email, Mockito.atLeast(2)).notificarAtraso(usuario3); // possibilidade 2: verifica pelo menos 2 notificação
//		verify(email, Mockito.atMost(5)).notificarAtraso(usuario3); // possibilidade 3: verifica no máximo 5 notificações
		verify(email, Mockito.atLeastOnce()).notificarAtraso(usuario3); // possibilidade 4: verifica pelo menos 1 (não importa quantos emails forem enviados, se passar 1 já é considerado)
		
		verify(email, Mockito.never()).notificarAtraso(usuario2); // com o aviso no Mockito, eu digo que não quero que o usuario2 seja notificado com atraso porque ele não tem atraso
		verifyNoMoreInteractions(email);
//		verifyZeroInteractions(spc); // não vai precisar na prática, mas deixei anotado para saber que existe
	}
	
	@Test
	public void deveTratarErroNoSPC() throws Exception {
		// 1. cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		// * abaixo definiremos o comportamento do Mock
		when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha cadastrófica"));
		
		// 2. verificação
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente");
		
		// 3. ação
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
	public void deveAlugarFilme_SemCalcularValor() throws Exception {
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		PowerMockito.doReturn(1.0).when(service, "calcularValorLocacao", filmes); // aquele service é o método que eu quero mockar e em String vai o nome do método
		
		// ação
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		// verificação
		Assert.assertThat(locacao.getValor(), is(1.0));
		PowerMockito.verifyPrivate(service).invoke("calcularValorLocacao", filmes);
	}
	
	@Test
	public void deveCalcularValorLocacao() throws Exception {
		// cenário
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		// ação
		Double valor = (Double) Whitebox.invokeMethod(service, "calcularValorLocacao", filmes);
		
		// verificação
		Assert.assertThat(valor, is(4.0));
	}
}
