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
	// Antes mesmo de tentar aplicar o teste já vai dar erro informando que EmailService é uma interface então não poderá ser espionada
	
	@Mock
	private EmailService email;
	// Caso queira que o Mock chame a implementação de um método, é possível (Exemplo 3)
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void devoMostrarDiferencaEntreMockSpy() {

		// Execute o teste para entender pela resposta apresentada no console
		
		// primeiro faremos um exemplo com Mock
//		Mockito.when(calcMock.somar(1, 2)).thenReturn(8);
//		System.out.println("Mock: " + calcMock.somar(1, 2)); -> Situação 1: resultados iguais
//		Mockito.when(calcMock.somar(1, 2)).thenReturn(5);
//		Mockito.when(calcMock.somar(1, 2)).thenCallRealMethod(); // agora ele retorna a implementação correta usando os métodos
		Mockito.when(calcMock.somar(1, 2)).thenReturn(5);
		System.out.println("Mock: " + calcMock.somar(1, 2)); // Situação 2: o Mock como não sabia o que fazer, retornou zero
		
		
		// agora faremos um exemplo com Spy
//		Mockito.when(calcSpy.somar(1, 3)).thenReturn(8);
//		System.out.println("Spy: " + calcSpy.somar(1, 2)); -> Situação 1: resultados iguais
//		Mockito.when(calcSpy.somar(1, 2)).thenReturn(5);
		Mockito.doReturn(5).when(calcSpy).somar(1, 2);
		Mockito.doNothing().when(calcSpy).imprime(); // colocando essa anotação, o spy não passará pelo método também
		System.out.println("Spy: " + calcSpy.somar(1, 2)); // Situação 2: o Spy como não sabia o que fazer, fez a execução real do método
		
		// Portanto, a principal diferença entre o Mock e o Spy é que o Mock retornará o valor padrão zero, enquanto o Spy retornará o valor do método
		// Exatamente por isso que o Spy não funciona com interfaces, apenas com classes concretas, e faremos outro exemplo acima mas com interface
		
		System.out.println("Mock");
		calcMock.imprime(); // O mock não passará pelo método de calculadora quando é void lá no método
		
		System.out.println("Spy");
		calcSpy.imprime(); // Já o spy passará pelo método de calculadora, tanto é que imprimiu no console sendo método void
		
		// REASSISTA A AULA PARA ENTENDER MELHOR PORQUE ESTÁ MUITO BAGUNÇADO
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void teste() {
		Calculadora calc = Mockito.mock(Calculadora.class);
//		Mockito.when(calc.somar(1, 2)).thenReturn(5); // claro que 1 + 2 não é 5, mas é só para você ver o funcionamento do mock pois, se você passar outro valor no println, você verá que não será retornado 5 mas sim 0
//		Mockito.when(calc.somar(1, Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer número somado com 1 dê 5, mas o mockito não aceita fazer valor fixo com um matcher (no caso o any())
//		Mockito.when(calc.somar(Mockito.anyInt(), Mockito.anyInt())).thenReturn(5); // aqui eu digo que qualquer número somado com 1 dê 5, mas o mockito não aceita fazer valor fixo com um matcher (no caso o any())
		
		ArgumentCaptor<Integer> argCapt = ArgumentCaptor.forClass(Integer.class);
		Mockito.when(calc.somar(argCapt.capture(), argCapt.capture())).thenReturn(5);
				
		Assert.assertEquals(5, calc.somar(134345, -234));
		System.out.println(argCapt.getAllValues());
	}
}
