package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // Segunda forma e melhor
public class OrdemTest {

	public static int contador = 0;
	
	// Primeira forma para garantir que os testes fiquem em ordem --------------------
	
//	public void inicia() {
//		contador = 1;
//	}
//	
//	public void verifica() {
//		assertEquals(1, contador);
//	}
//	
//	@Test
//	public void testGeral() {
//		inicia();
//		verifica();
//	}
	
	// � estranha e bem complicada por perder em rastreabilidade --------------------
	
	// Na segunda forma com a anota��o, ele pega pela ordem alfab�tica abaixo
	@Test
	public void inicia() {
		contador = 1;
	}
	
	@Test
	public void verifica() {
		assertEquals(1, contador);
	}
	// Ela te deixa um peso na consci�ncia mas seria a mais adequada
}
