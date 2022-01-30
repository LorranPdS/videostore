package br.ce.wcaquino.servicos;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;
import br.ce.wcaquino.runners.ParallelRunner;

// No início do curso, o professor falou que, quando uma classe não está determinando explicitamente qual é o Runner dela, é como se tivesse essa anotação abaixo
//@RunWith(JUnit4.class)

// Porém, veja que usaremos para execução o nosso Runner paralelo que acabamos de criar.
@RunWith(ParallelRunner.class) // Rode antes com essa anotação comentada (para que rode com o JUnit4.class, depois rode com essa anotação aqui e compare o tempo de execução das duas
public class CalculadoraTest {
	
	// A execução foi tão rápida que não deu pra perceber o paralelismo, então colocamos algumas impressões para que o mesmo seja notado
	// Sem o ParallelRunner, você irá notar que as impressões do Before e do After virão uma a uma, mas com o uso do ParallelRunner, elas virão de duas em duas
	
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
		// cenário
		int a = 5;
		int b = 3;
		
		// ação
		int resultado = calc.somar(a, b);
		
		// verificação
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		// cenário
		int a = 8;
		int b = 5;
		
		// ação
		int resultado = calc.subtrair(a, b);
		
		// verificação
		Assert.assertEquals(3, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		// cenário
		int a = 6;
		int b = 3;
		
		// ação
		int resultado = calc.divide(a, b);
		
		// verificação
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// cenário
		int a = 10;
		int b = 0;
		
		// ação
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
