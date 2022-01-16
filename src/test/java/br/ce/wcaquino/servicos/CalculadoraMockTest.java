package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class CalculadoraMockTest {

	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
//	@Spy
//	private EmailService email;
	// Antes mesmo de tentar aplicar o teste j� vai dar erro informando que EmailService � uma interface ent�o n�o poder� ser espionada
	
	@Mock
	private EmailService email;
	// Caso queira que o Mock chame a implementa��o de um m�todo, � poss�vel (Exemplo 3)
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void devoMostrarDiferencaEntreMockSpy() {

		// Execute o teste para entender pela resposta apresentada no console
		
		// primeiro faremos um exemplo com Mock
//		Mockito.when(calcMock.somar(1, 2)).thenReturn(8);
//		System.out.println("Mock: " + calcMock.somar(1, 2)); -> Situa��o 1: resultados iguais
//		Mockito.when(calcMock.somar(1, 2)).thenReturn(5);
//		Mockito.when(calcMock.somar(1, 2)).thenCallRealMethod(); // agora ele retorna a implementa��o correta usando os m�todos
		Mockito.when(calcMock.somar(1, 2)).thenReturn(5);
		System.out.println("Mock: " + calcMock.somar(1, 2)); // Situa��o 2: o Mock como n�o sabia o que fazer, retornou zero
		
		
		// agora faremos um exemplo com Spy
//		Mockito.when(calcSpy.somar(1, 3)).thenReturn(8);
//		System.out.println("Spy: " + calcSpy.somar(1, 2)); -> Situa��o 1: resultados iguais
//		Mockito.when(calcSpy.somar(1, 2)).thenReturn(5);
		Mockito.doReturn(5).when(calcSpy).somar(1, 2);
		Mockito.doNothing().when(calcSpy).imprime(); // colocando essa anota��o, o spy n�o passar� pelo m�todo tamb�m
		System.out.println("Spy: " + calcSpy.somar(1, 2)); // Situa��o 2: o Spy como n�o sabia o que fazer, fez a execu��o real do m�todo
		
		// Portanto, a principal diferen�a entre o Mock e o Spy � que o Mock retornar� o valor padr�o zero, enquanto o Spy retornar� o valor do m�todo
		// Exatamente por isso que o Spy n�o funciona com interfaces, apenas com classes concretas, e faremos outro exemplo acima mas com interface
		
		System.out.println("Mock");
		calcMock.imprime(); // O mock n�o passar� pelo m�todo de calculadora quando � void l� no m�todo
		
		System.out.println("Spy");
		calcSpy.imprime(); // J� o spy passar� pelo m�todo de calculadora, tanto � que imprimiu no console sendo m�todo void
		
		// REASSISTA A AULA PARA ENTENDER MELHOR PORQUE EST� MUITO BAGUN�ADO
	}
	
	
	
	
	
	
	
	
	
	
	
	
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
