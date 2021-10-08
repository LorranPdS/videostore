package br.ce.wcaquino.servicos;

import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

import org.junit.Assert;

public class AssertTest {
	
	@Test
	public void test() {
		// Se eu quiser verificar se um valor � true
		Assert.assertTrue(true);
		
		// Se eu quiser verificar se um valor � false
		Assert.assertFalse(false);
		
		// Tem como fazer um true dessa forma tamb�m, que � negando o false, mas evite o uso de nega��es
		Assert.assertTrue(!false);
		
		// Ele checa se um valor � igual ao outro e ver cada tipo de uma forma diferente
		Assert.assertEquals(1, 1);
		
		// Com double, � necess�rio que voc� coloque um delta de compara��o, que � aquela margem de erro 0.01
		Assert.assertEquals(0.51, 0.51, 0.01);
		
		// Comparando wrapper class com tipo primitivo
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2); // 1� forma: passando tipo primitivo para objeto
		Assert.assertEquals(i, i2.intValue()); // 2� forma: passando objeto para tipo primitivo
		
		// Compara��o de Strings
		Assert.assertEquals("bola", "bola"); // quando id�ntico
		Assert.assertTrue("bola".equalsIgnoreCase("Bola")); // desconsiderando mai�sculas de min�sculas
		Assert.assertTrue("bola".startsWith("bo")); // pegando partes espec�ficas
		
		/*
		 * Assim como a String, a igualdade dos objetos ser� verificada atrav�s do m�todo equals do pr�prio objeto,
		 * pois ningu�m melhor que o pr�prio objeto para dizer se ele � ou n�o igual ao outro
		 */
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Assert.assertEquals(u1, u2); // Ser� necess�rio implementar o hashCode/Equals no objeto
		
		// Quero verificar se os objetos s�o da mesma inst�ncia
		Assert.assertSame(u2, u2); // Se for u1 e u2 quebra por n�o apontar para a mesma inst�ncia
		
		// Para verificarmos se um objeto � null ou n�o
		Usuario u3 = null;
		Assert.assertTrue(u3 == null); // 1� forma
		Assert.assertNull(u3); // 2� forma
		
		/*
		 * Para todas as assertivas acima H� UMA NEGA��O para cada uma delas, ou seja:
		 * AssertTrue = AssertNotTrue
		 * AssertFalse = AssertNotFalse
		 * AssertEquals = AssertNotEquals
		 * AssertSame = AssertNotSame
		 */
		Assert.assertNotEquals("bola", "casa");
		
		// Uma outra forma de declara��o de assertivas � colocando uma String no in�cio
		// Assert.assertEquals("Erro de compara��o:", 1, 2); -> mostra uma msg na pilha te indicando
		
		// Preste aten��o na ordem: primeiro o valor esperado e em segundo o valor atual
	}

}
