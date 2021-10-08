package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Test
	public void teste() {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//verificacao
		// Graças ao hamcrest, o JUnit já tem uns Matchers prontos
		// Para o import estático, use o Ctrl + Shift + M
		// A leitura ficou: verifique que o valor é 5
		// Agora inverteu, pois o primeiro valor é o atual e o segundo é o matcher
		assertThat(locacao.getValor(), is(5.0)); // 1ª forma
		
		// Sem o import estático, ficaria assim
		assertThat(locacao.getValor(), CoreMatchers.is(5.0)); // Sem import estático
		
		// Outra forma de leitura mais clara ainda
		assertThat(locacao.getValor(), is(equalTo(5.0))); // 2ª forma
		
		// Se fosse uma negação
		assertThat(locacao.getValor(), not(5.0)); // 3ª forma
		
		// Vamos deixar os dois assim por enquanto e os imports estáticos
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}
}
