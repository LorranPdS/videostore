package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.MatchersProprios.caiNumaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static java.util.Arrays.asList;
import static java.util.Calendar.SATURDAY;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	// vamos ter que criar aqueles 3 mocks com as anota��es e injetar esses mocks na classe de teste abaixo (por isso a anota��o injectmocks)
	@InjectMocks
	private LocacaoService service;
	
	// colocando essas anota��es de mock o teste j� vai saber que essas s�o as classes que est�o mockadas
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
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		// Ao testarmos no s�bado
		assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().comValor(5.0).agora());
		
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
		List<Filme> filmes = asList(umFilmeSemEstoque().agora());
		
		// a��o
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		// cen�rio
		List<Filme> filmes = asList(umFilme().agora());
		
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
		Assume.assumeTrue(verificarDiaSemana(new Date(), SATURDAY));
		
		// cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		// a��o
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verifica��o
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		// cen�rio
		Usuario usuario = umUsuario().agora();
//		Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
		List<Filme> filmes = asList(umFilme().agora());
		
//		when(spc.possuiNegativacao(usuario)).thenReturn(true); // ao inv�s de usar esse, vamos usar o matcher abaixo do any() para deixar mais gen�rico
		when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		
		// a��o
		try {
			service.alugarFilme(usuario, filmes);
			// verifica��o
			
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usu�rio negativado"));
		}
		
		Mockito.verify(spc).possuiNegativacao(usuario);
//		Mockito.verify(spc).possuiNegativacao(usuario2);
	}
	
	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// cen�rio
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario em dia").agora(); //-> essa seria a linha do unhappy path para termos certeza que n�o est� sendo gerado um falso positivo
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
				umLocacao() // aqui seria uma situa��o em que o usu�rio 3 recebesse 2 emails de notifica��o
					.atrasado()
					.comUsuario(usuario3)
					.agora()
					);
		
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		// a��o
		service.notificarAtrasos();
		
		// verifica��o
		verify(email, times(3)).notificarAtraso(Mockito.any(Usuario.class)); // esse any() n�o serve apenas para objetos, mas voc� verificando-o, ver� que serve para boolean, double, int, ...
		verify(email).notificarAtraso(usuario); // veja que fizemos uma verifica��o do mesmo m�todo descrito na a��o

//		verify(email, Mockito.times(2)).notificarAtraso(usuario3); // possibilidade 1: verifica 2 vezes a notifica��o
//		verify(email, Mockito.atLeast(2)).notificarAtraso(usuario3); // possibilidade 2: verifica pelo menos 2 notifica��o
//		verify(email, Mockito.atMost(5)).notificarAtraso(usuario3); // possibilidade 3: verifica no m�ximo 5 notifica��es
		verify(email, Mockito.atLeastOnce()).notificarAtraso(usuario3); // possibilidade 4: verifica pelo menos 1 (n�o importa quantos emails forem enviados, se passar 1 j� � considerado)
		
		verify(email, Mockito.never()).notificarAtraso(usuario2); // com o aviso no Mockito, eu digo que n�o quero que o usuario2 seja notificado com atraso porque ele n�o tem atraso
		verifyNoMoreInteractions(email);
//		verifyZeroInteractions(spc); // n�o vai precisar na pr�tica, mas deixei anotado para saber que existe
	}
	
	@Test
	public void deveTratarErroNoSPC() throws Exception {
		// 1. cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		// * abaixo definiremos o comportamento do Mock
		when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha cadastr�fica"));
		
		// 2. verifica��o
		exception.expect(LocadoraException.class);
		exception.expectMessage("Problemas com SPC, tente novamente");
		
		// 3. a��o
		service.alugarFilme(usuario, filmes);
	}
	
	@Test
	public void deveProrrogaUmaLocacao() {
		// cen�rio
		Locacao locacao = umLocacao().agora();
		
		// a��o
		service.prorrogarLocacao(locacao, 3);
		
		// verifica��o
		// como o m�todo a ser verificado � do tipo void, vamos ter que fazer um outro tipo de verifica��o
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();
		
		error.checkThat(locacaoRetornada.getValor(), is(12.0)); // voc� pode causar um erro pra ver como sair� o erro da terceira linha
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3)); // aqui voc� pode tentar causar um erro
	}
}













