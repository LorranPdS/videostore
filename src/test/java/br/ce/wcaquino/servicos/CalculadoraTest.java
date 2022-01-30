package br.ce.wcaquino.servicos;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;

// No in�cio do curso, o professor falou que, quando uma classe n�o est� determinando explicitamente qual � o Runner dela, � como se tivesse essa anota��o abaixo
//@RunWith(JUnit4.class)

// Por�m, veja que usaremos para execu��o o nosso Runner paralelo que acabamos de criar.
@RunWith(ParallelRunner.class) // Rode antes com essa anota��o comentada (para que rode com o JUnit4.class, depois rode com essa anota��o aqui e compare o tempo de execu��o das duas
public class CalculadoraTest {
	
	// A execu��o foi t�o r�pida que n�o deu pra perceber o paralelismo, ent�o colocamos algumas impress�es para que o mesmo seja notado
	// Sem o ParallelRunner, voc� ir� notar que as impress�es do Before e do After vir�o uma a uma, mas com o uso do ParallelRunner, elas vir�o de duas em duas
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
		System.out.println("Iniciando...");
	}
	
	@After
	public void tearDown() {
		System.out.println("Finalizando...");
	}

	@Test
	public void deveSomarDoisValores() {
		// cen�rio
		int a = 5;
		int b = 3;
		
		// a��o
		int resultado = calc.somar(a, b);
		
		// verifica��o
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		// cen�rio
		int a = 8;
		int b = 5;
		
		// a��o
		int resultado = calc.subtrair(a, b);
		
		// verifica��o
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		// cen�rio
		int a = 6;
		int b = 3;
		
		// a��o
		int resultado = calc.divide(a, b);
		
		// verifica��o
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// cen�rio
		int a = 10;
		int b = 0;
		
		// a��o
		calc.divide(a, b);
	}
	
	@Test
	public void deveDividir() {
		String a = "6";
		String b = "3";
		
		int resultado = calc.divide(a, b);
		
		Assert.assertEquals(2, resultado);
	}
}
