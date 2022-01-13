package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class CalculadoraMockTest {

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
//		Mockito.when(calc.somar(1, 2)).thenReturn(5); // claro que 1 + 2 n�o � 5, mas � s� para voc� ver o funcionamento do mock pois, se voc� passar outro valor no println, voc� ver� que n�o ser� retornado 5 mas sim 0
//		Mockito.when(calc.somar(1, Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer n�mero somado com 1 d� 5, mas o mockito n�o aceita fazer valor fixo com um matcher (no caso o any())
//		Mockito.when(calc.somar(Mockito.anyInt(), Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer n�mero somado com 1 d� 5, mas o mockito n�o aceita fazer valor fixo com um matcher (no caso o any())
		Mockito.when(calc.somar(Mockito.eq(1), Mockito.anyInt())).thenReturn(5); // se eu quiser restringir o primeiro valor como sendo 1 (aquele eq � de equals) e o segundo pode ser qualquer valor
		
		Assert.assertEquals(5, calc.somar(1, 8));
//		System.out.println(calc.somar(4, 8)); // agora se eu trocar para um valor diferente de 1, ele vai se perder e retornar� zero ao inv�s de 5
	}
}
