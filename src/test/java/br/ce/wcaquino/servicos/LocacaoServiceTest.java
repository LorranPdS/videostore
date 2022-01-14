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

	// vamos ter que criar aqueles 3 mocks com as anotações e injetar esses mocks na classe de teste abaixo (por isso a anotação injectmocks)
	@InjectMocks
	private LocacaoService service;
	
	// colocando essas anotações de mock o teste já vai saber que essas são as classes que estão mockadas
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

		// Ao testarmos no sábado
		assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().comValor(5.0).agora());
		
		// ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
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
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(verificarDiaSemana(new Date(), SATURDAY));
		
		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().agora());
		
		// ação
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verificação
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
	}
	
	@Test
	public void naoDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		// cenário
		Usuario usuario = umUsuario().agora();
//		Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
		List<Filme> filmes = asList(umFilme().agora());
		
//		when(spc.possuiNegativacao(usuario)).thenReturn(true); // ao invés de usar esse, vamos usar o matcher abaixo do any() para deixar mais genérico
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
				umLocacao() // aqui seria uma situação em que o usuário 3 recebesse 2 emails de notificação
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
		// como o método a ser verificado é do tipo void, vamos ter que fazer um outro tipo de verificação
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(dao).salvar(argCapt.capture());
		Locacao locacaoRetornada = argCapt.getValue();
		
		error.checkThat(locacaoRetornada.getValor(), is(12.0)); // você pode causar um erro pra ver como sairá o erro da terceira linha
		error.checkThat(locacaoRetornada.getDataLocacao(), ehHoje());
		error.checkThat(locacaoRetornada.getDataRetorno(), ehHojeComDiferencaDias(3)); // aqui você pode tentar causar um erro
	}
}













