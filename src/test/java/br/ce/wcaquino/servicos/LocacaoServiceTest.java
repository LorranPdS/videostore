package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
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

	@Test
	public void testeLocacao() throws Exception {

		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 1, 5.0);
		
		// acao
		Locacao locacao = service.alugarFilme(usuario, filme);

		// verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		
		// cen�rio
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 2", 0, 4.0);
		
		// acao
		service.alugarFilme(usuario, filme);
	}
	
	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		
		// cen�rio
		Filme filme = new Filme("Filme 2", 1, 4.0);
		
		// a��o
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio vazio"));
		}
	}
	
	@Test
	public void testLocacao_FilmeVazio() throws FilmeSemEstoqueException, LocadoraException {
		
		// cen�rio
		Usuario usuario = new Usuario("Usu�rio 1");
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		// a��o
		service.alugarFilme(usuario, null);
	}
	
}
