package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

// 1° Começamos por aqui criando o teste de calculadora completamente
public class CalculadoraTest {
	
	// Avançando mais as coisas
	private Calculadora calc;
	
	// Vamos tirar a instância da calculadora de dentro dos testes
	@Before
	public void setup() {
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		// cenário
		int a = 5;
		int b = 3;
		
//		Calculadora calc = new Calculadora();
		
		// ação
		int resultado = calc.somar(a, b);
		
		// verificação
		Assert.assertEquals(8, resultado);
	}
	
	// Aqui teremos um segundo exemplo que seguirá a mesma sequência
	@Test
	public void deveSubtrairDoisValores() {
		// cenário
		int a = 8;
		int b = 5;
		
//		Calculadora calc = new Calculadora();
		
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
		
//		Calculadora calc = new Calculadora();
		
		// ação
		int resultado = calc.divide(a, b);
		
		// verificação
		Assert.assertEquals(2, resultado);
	}
	
	// Nesse teste vamos criar uma exception personalizada
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// cenário
		int a = 10;
		int b = 0;
		
//		Calculadora calc = new Calculadora();
		
		// ação
		calc.divide(a, b);
	}
}















