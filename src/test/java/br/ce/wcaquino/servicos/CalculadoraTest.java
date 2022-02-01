package br.ce.wcaquino.servicos;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraTest {
	
	// só para mostrar que o paralelismo ainda está funcionando, mesmo que pelo maven
	public static StringBuffer ordem = new StringBuffer();
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
		System.out.println("Iniciando...");
		ordem.append("1");
	}
	
	@After
	public void tearDown() {
		System.out.println("Finalizando...");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println(ordem.toString());
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
