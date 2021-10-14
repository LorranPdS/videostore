package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

// 1� Come�amos por aqui criando o teste de calculadora completamente
public class CalculadoraTest {
	
	// Avan�ando mais as coisas
	private Calculadora calc;
	
	// Vamos tirar a inst�ncia da calculadora de dentro dos testes
	@Before
	public void setup() {
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		// cen�rio
		int a = 5;
		int b = 3;
		
//		Calculadora calc = new Calculadora();
		
		// a��o
		int resultado = calc.somar(a, b);
		
		// verifica��o
		Assert.assertEquals(8, resultado);
	}
	
	// Aqui teremos um segundo exemplo que seguir� a mesma sequ�ncia
	@Test
	public void deveSubtrairDoisValores() {
		// cen�rio
		int a = 8;
		int b = 5;
		
//		Calculadora calc = new Calculadora();
		
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
		
//		Calculadora calc = new Calculadora();
		
		// a��o
		int resultado = calc.divide(a, b);
		
		// verifica��o
		Assert.assertEquals(2, resultado);
	}
	
	// Nesse teste vamos criar uma exception personalizada
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// cen�rio
		int a = 10;
		int b = 0;
		
//		Calculadora calc = new Calculadora();
		
		// a��o
		calc.divide(a, b);
	}
}















