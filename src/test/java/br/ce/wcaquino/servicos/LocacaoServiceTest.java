package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule // criada para a forma nova
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {

		// 1� FORMA
//		// cenario
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0);
//
//		// acao
//		Locacao locacao;
//		try {
//			locacao = service.alugarFilme(usuario, filme);
//
//			// verificacao
//			error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//			error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
//			error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
//		} catch (Exception e) {
//			e.printStackTrace();
//			Assert.fail("N�o deveria lan�ar exce��o");
//			/*
//			 * Uma forma que as pessoas usam para que o JUnit n�o d� um falso positivo �
//			 * colocando a linha acima
//			 */
//		}
		
		// 2� FORMA
		// cen�rio
		// N�s subimos a exce��o ao inv�s de tratar, da� o JUnit � quem far� o tratamento
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0); // usada para causar exce��o
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	// 1. forma elegante
	@Test(expected = Exception.class)
	public void testLocacao_filmeSemEstoque() throws Exception { // exce��o lan�ada p/ o JUnit
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		service.alugarFilme(usuario, filme);
	}
	
	// 2. forma robusta - permite um controle maior sobre a execu��o do teste que a elegante n�o d�
	@Test
	public void testLocacao_filmeSemEstoque_2() { // exce��o lan�ada p/ o JUnit
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// acao
		try {
			service.alugarFilme(usuario, filme);
			fail("Deveria ter lan�ado uma exce��o");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	
	// 3. forma nova (que na verdade n�o � t�o nova assim)
	@Test
	public void testLocacao_filmeSemEstoque_3() throws Exception { // exce��o lan�ada p/ o JUnit
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");

		// acao
		service.alugarFilme(usuario, filme);
	}
}
