package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class CalculadoraMockTest {

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
//		Mockito.when(calc.somar(1, 2)).thenReturn(5); // claro que 1 + 2 não é 5, mas é só para você ver o funcionamento do mock pois, se você passar outro valor no println, você verá que não será retornado 5 mas sim 0
//		Mockito.when(calc.somar(1, Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer número somado com 1 dê 5, mas o mockito não aceita fazer valor fixo com um matcher (no caso o any())
//		Mockito.when(calc.somar(Mockito.anyInt(), Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer número somado com 1 dê 5, mas o mockito não aceita fazer valor fixo com um matcher (no caso o any())
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5); // se eu quiser restringir o primeiro valor como sendo 1 (aquele eq é de equals) e o segundo pode ser qualquer valor
		
		Assert.assertEquals(5, calc.somar(1, 8));
//		System.out.println(calc.somar(4, 8)); // agora se eu trocar para um valor diferente de 1, ele vai se perder e retornará zero ao invés de 5
	}
}
