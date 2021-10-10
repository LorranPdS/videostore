package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {

	private LocacaoService service;
	
	/*
	 * E se um teste depender de um valor de outro (precisar passar os valores de um teste
	 * para o outro teste), como eu faço?
	 * 
	 * A resposta seria: se eu colocar a variável como static (a exemplo da variável "contador"), o JUnit
	 * não vai reinicializar entre um teste e outro e esse valor será mantido. Veja esse
	 * exemplo com a variável "contador"
	 */
	
	// definição do contador
	private static int contador = 0;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		// método executado antes de cada teste
		System.out.println("Before");
		service = new LocacaoService();
		
		// incremento
		contador++;
		// impressão do contador
		System.out.println(contador);
	}
	
	@After
	public void tearDown() {
		// método executado após cada teste
		System.out.println("After");
	}
	
	/*
	 * Tanto o @BeforeClass quanto o @AfterClass devem ser inicializados respectivamente
	 * antes da classe ser instanciada e após a classe ser destruída, temos que deixar
	 * esses métodos estáticos, por isso estão como static. Apenas deixando eles em static
	 * que o JUnit terá acesso a eles antes da classe ser criada
	 */
	
	@BeforeClass
	public static void setupClass() {
		// método executado antes da classe ser instanciada
		System.out.println("BeforeClass");
	}
	
	@AfterClass
	public static void tearDownClass() {
		// método executado após a classe ser finalizada
		System.out.println("AfterClass");
	}

	@Test
	public void testeLocacao() throws Exception {

		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);
		
		System.out.println("TESTE!");

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	// Forma elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception { // exceção lançada p/ o JUnit
		
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		System.out.println("TESTE!");

		// acao
		service.alugarFilme(usuario, filme);
	}
	
	@Test // Forma robusta
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		
		// cenário
		Filme filme = new Filme("Filme 2", 1, 4.0);
		
		System.out.println("TESTE!");
		
		// ação
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuário vazio"));
		}
	}
	
	@Test // Forma nova
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		
		// cenário
		Usuario usuario = new Usuario("Usuário 1");
		
		System.out.println("TESTE!");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// ação
		service.alugarFilme(usuario, null);
	}
	
}
