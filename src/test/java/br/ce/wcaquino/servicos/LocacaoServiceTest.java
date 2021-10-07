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
	 * Como as classes de testes não vão para a produção, nós colocamos essas classes de testes
	 * em um outro source folder, e por isso ela está aqui agora no src/test/java
	 */
	
	/*
	 * Tendo os mesmos nomes de pacotes, apesar do teste estar fisicamente em outra pasta
	 * (uma no source folder de código e outra no source folder de teste), em tempo de
	 * execução, o Java entende que ambas estão no mesmo pacote (fisicamente separadas
	 * porém logicamente juntas)
	 */
	
	/*
	 * Veja que ao colocarmos na mesma estrutura de pacotes, enxergamos as variáveis
	 * default, protegida e pública, como nos exemplos das linhas 43, 44 e 45
	 * Essa lógica é a mesma para métodos
	 */
	
	@Test
	public void teste() {
		
		//cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		// linhas 45 a 47 são apenas para exemplos, podem ser removidas
		String padrao = service.variavelDefault;
		String protegida = service.variavelProtegida;
		String publica = service.variavelPublica;
		
		//verificacao
		Assert.assertTrue(locacao.getValor() == 5.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}
}
