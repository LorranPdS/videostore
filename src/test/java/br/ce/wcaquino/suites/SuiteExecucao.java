package br.ce.wcaquino.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

// 1) primeiro vamos informar ao JUnit que a execu��o dessa classe ser� diferente, usando a anota��o RunWith
/*
 * 2) depois eu vou colocar outra anota��o (SuiteClasses) e dentro dela n�s definiremos todos os testes que eu 
 * quero que sejam executados por essa Suite e os colocaremos entre chaves
 */

@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {

	/*
	 * 3) A classe SuiteExecucao em que estamos n�o tem muito uso, mas pelo Java n�s somos obrigados a ter
	 * uma classe declarada. Caso voc� use uma outra linguagem que n�o precise de declara��o de classe, pode
	 * remover a classe. Com essas 3 a��es, a Su�te de testes est� pronta.
	 */
	
	/*
	 * Na hora de executar, voc� ver� que estar� a um n�vel a mais (que no caso � a pr�pria Su�te que criamos),
	 * da� dentro da su�te estar�o as 3 classes de testes que declaramos na anota��o SuiteClasses e cada classe
	 * possui seus m�todos
	 */
	
	/*
	 * As anota��es BeforeClass e AfterClass podem ser aplicadas nesse ponto tamb�m. Colocando-as na Su�te, eu 
	 * consigo realizar uma opera��o antes e ap�s toda a bateria de testes respectivamente. Vamos fazer um exemplo
	 * usando o BeforeClass e o AfterClass abaixo e fazer uma impress�o no CalculoValorLocacao para vermos que o 
	 * BeforeClass e o AfterClass vem antes de todos os testes da bateria.
	 */
	
	// OBS.: execute seu teste por aqui
	
	@BeforeClass
	public static void before() {
		System.out.println("before");
	}
	
	@AfterClass
	public static void after() {
		System.out.println("after");
	}
}
