package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


public class CalculadoraMockTest {

	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
//		Mockito.when(calc.somar(1, 2)).thenReturn(5); // claro que 1 + 2 n�o � 5, mas � s� para voc� ver o funcionamento do mock pois, se voc� passar outro valor no println, voc� ver� que n�o ser� retornado 5 mas sim 0
//		Mockito.when(calc.somar(1, Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer n�mero somado com 1 d� 5, mas o mockito n�o aceita fazer valor fixo com um matcher (no caso o any())
//		Mockito.when(calc.somar(Mockito.anyInt(), Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer n�mero somado com 1 d� 5, mas o mockito n�o aceita fazer valor fixo com um matcher (no caso o any())
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);
				
		Assert.assertEquals(5, calc.somar(134345, -234));
		System.out.println(argCapt.getAllValues());
	}
}
