package br.ce.wcaquino.servicos;

import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

import org.junit.Assert;

public class AssertTest {
	
	@Test
	public void test() {
		// Se eu quiser verificar se um valor é true
		Assert.assertTrue(true);
		
		// Se eu quiser verificar se um valor é false
		Assert.assertFalse(false);
		
		// Tem como fazer um true dessa forma também, que é negando o false, mas evite o uso de negações
		Assert.assertTrue(!false);
		
		// Ele checa se um valor é igual ao outro e ver cada tipo de uma forma diferente
		Assert.assertEquals(1, 1);
		
		// Com double, é necessário que você coloque um delta de comparação, que é aquela margem de erro 0.01
		Assert.assertEquals(0.51, 0.51, 0.01);
		
		// Comparando wrapper class com tipo primitivo
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2); // 1ª forma: passando tipo primitivo para objeto
		Assert.assertEquals(i, i2.intValue()); // 2ª forma: passando objeto para tipo primitivo
		
		// Comparação de Strings
		Assert.assertEquals("bola", "bola"); // quando idêntico
		Assert.assertTrue("bola".equalsIgnoreCase("Bola")); // desconsiderando maiúsculas de minúsculas
		Assert.assertTrue("bola".startsWith("bo")); // pegando partes específicas
		
		/*
		 * Assim como a String, a igualdade dos objetos será verificada através do método equals do próprio objeto,
		 * pois ninguém melhor que o próprio objeto para dizer se ele é ou não igual ao outro
		 */
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Assert.assertEquals(u1, u2); // Será necessário implementar o hashCode/Equals no objeto
		
		// Quero verificar se os objetos são da mesma instância
		Assert.assertSame(u2, u2); // Se for u1 e u2 quebra por não apontar para a mesma instância
		
		// Para verificarmos se um objeto é null ou não
		Usuario u3 = null;
		Assert.assertTrue(u3 == null); // 1ª forma
		Assert.assertNull(u3); // 2ª forma
		
		/*
		 * Para todas as assertivas acima HÁ UMA NEGAÇÃO para cada uma delas, ou seja:
		 * AssertTrue = AssertNotTrue
		 * AssertFalse = AssertNotFalse
		 * AssertEquals = AssertNotEquals
		 * AssertSame = AssertNotSame
		 */
		Assert.assertNotEquals("bola", "casa");
		
		// Uma outra forma de declaração de assertivas é colocando uma String no início
		// Assert.assertEquals("Erro de comparação:", 1, 2); -> mostra uma msg na pilha te indicando
		
		// Preste atenção na ordem: primeiro o valor esperado e em segundo o valor atual
	}

}
