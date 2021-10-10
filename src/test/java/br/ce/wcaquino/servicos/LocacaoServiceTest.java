package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
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

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {

		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);

		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	// Forma elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception { // exce��o lan�ada p/ o JUnit
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);

		// acao
		service.alugarFilme(usuario, filme);
		
		/*
		 * Na forma elegante, como na forma nova, n�o acontecer� a execu��o ap�s a a��o
		 * caso uma exce��o seja capturada pois nessa forma a exce��o foi enviada ao JUnit
		 * para que o mesmo fa�a o tratamento da exce��o
		 */
	}
	
	@Test // Forma robusta
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 2", 1, 4.0);
		
		// a��o
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}
		
		/*
		 * Na forma robusta, caso queira colocar outra coisa depois do catch,
		 * ela ser� lida porque o fluxo segue mesmo tendo a exce��o
		 */
	}
	
	@Test // Forma nova
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usu�rio 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// a��o
		service.alugarFilme(usuario, null);
		
		/*
		 * Na forma nova, como na forma elegante, n�o acontecer� a execu��o ap�s a a��o
		 * caso uma exce��o seja capturada pois nessa forma a exce��o foi enviada ao JUnit
		 * para que o mesmo fa�a o tratamento da exce��o
		 */
	}
	
}




























