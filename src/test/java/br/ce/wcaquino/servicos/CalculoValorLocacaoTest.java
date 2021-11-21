package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

// Esse � o chamado Data Driven Test (Teste Orientado a Dados)

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	/*
	 * 1) Vou definir as vari�veis que preciso para executar o teste
	 * Nesse teste, o que fica variando � a Lista de filmes e o resultado do valor da loca��o (14.0)
	 */
	
	@Parameter // 5) ser� o primeiro registro do array getParametros
	public List<Filme> filmes;
	
	@Parameter(value = 1) // 5) ser� o segundo registro do array getParametros
	public Double valorLocacao;
	
	// 2) trago abaixo a instancia��o do servi�o
	private LocacaoService service;
	
	// 7) para descrevermos melhor os testes
	@Parameter(value = 2)
	public String cenario;
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
	private static Filme filme6 = new Filme("Filme 6", 2, 4.0);
	private static Filme filme7 = new Filme("Filme 7", 2, 4.0);
	
	// 4) a anota��o abaixo � para informar ao Junit que essa � a nossa fonte de dados
	@Parameters(name = "{2}") // esse n�mero 2 vem da linha 43 do Parameter
	public static Collection<Object[]> getParametros(){
		// 3) abaixo ser�o colocados os cen�rios com 3, 4, 5 e 6 filmes
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"}, // aumentamos um caso de teste
			{Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto"}, // aumentamos outro caso de teste
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderendoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
			
		// a��o
		Locacao resultado = service.alugarFilme(usuario, filmes);
			
		// verifica��o
		// O valor seria o seguinte: 4+4+3+2+1=14
		assertThat(resultado.getValor(), is(valorLocacao));
	}
	
	// 6) a cria��o do teste abaixo � s� para mostrar o funcionamento
	@Test
	public void print() {
		// veja que ele vai imprimir os valores e agora fazer 8 execu��es e em cada massa(vetor) ele fez 2 testes
		System.out.println(valorLocacao);
	}
}
