package br.ce.wcaquino.servicos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	/*
	 * Como as classes de testes n�o v�o para a produ��o, n�s colocamos essas classes de testes
	 * em um outro source folder, e por isso ela est� aqui agora no src/test/java
	 */
	
	/*
	 * Tendo os mesmos nomes de pacotes, apesar do teste estar fisicamente em outra pasta
	 * (uma no source folder de c�digo e outra no source folder de teste), em tempo de
	 * execu��o, o Java entende que ambas est�o no mesmo pacote (fisicamente separadas
	 * por�m logicamente juntas)
	 */
	
	/*
	 * Veja que ao colocarmos na mesma estrutura de pacotes, enxergamos as vari�veis
	 * default, protegida e p�blica, como nos exemplos das linhas 43, 44 e 45
	 * Essa l�gica � a mesma para m�todos
	 */
	
	@Test
	public void teste() {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		// linhas 45 a 47 s�o apenas para exemplos, podem ser removidas
		String padrao = service.variavelDefault;
		String protegida = service.variavelProtegida;
		String publica = service.variavelPublica;
		
		//verificacao
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}
}
