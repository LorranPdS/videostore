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
import java.util.Date;
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

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

// na linha abaixo estamos avisando ao JUnit que a execução do método 'deveDevolverNaSegundaAoAlugarNoSabado()' deve ser gerenciada pelo powermock
//esse RunWith é você dizendo que é pra rodar com o que está entre parênteses
//para o PowerMock conseguir fazer essas alterações no ambiente, ele precisa mexer com muita coisa por trás, então ele altera algumas coisas na classe para que ela responda às solicitações do PowerMock. Então nessa linha pedimos que ele prepare a classe para teste, no caso preparar a classe LocacaoService que está entre parênteses
@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class, DataUtils.class}) // Esse DataUtils é eu pedindo ao Powermock preparar também a classe utilizada pelo 'ehHoje', e para isso vimos onde o 'new Date()' é feito no 'ehHoje'. Como vamos passar mais de uma classe, passamos entre colchetes.
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
	}

	@Test
	public void deveAlugarFilme() throws Exception {

		// cenário
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = asList(umFilme().comValor(5.0).agora());
		
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));
		
		// ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// verificação
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getDataLocacao(), ehHoje());
		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
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
		
		// Agora vamos mockar o construtor do Date para que, pelo menos durante a execução desse teste seja um sábado
		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));
		// acima está dizendo: quando eu solicitar uma nova instância do Date que não possui argumentos, então retorne a data acima que cai em um sábado
		
		// ação
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		// verificação
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments(); // para verificarmos se o construtor foi chamado.
		// veja também como foi a integração do PowerMockito com o Mockito (no caso o Mockito.times(2))
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













