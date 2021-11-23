package br.ce.wcaquino.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

// 1) primeiro vamos informar ao JUnit que a execução dessa classe será diferente, usando a anotação RunWith
/*
 * 2) depois eu vou colocar outra anotação (SuiteClasses) e dentro dela nós definiremos todos os testes que eu 
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
	 * 3) A classe SuiteExecucao em que estamos não tem muito uso, mas pelo Java nós somos obrigados a ter
	 * uma classe declarada. Caso você use uma outra linguagem que não precise de declaração de classe, pode
	 * remover a classe. Com essas 3 ações, a Suíte de testes está pronta.
	 */
	
	/*
	 * Na hora de executar, você verá que estará a um nível a mais (que no caso é a própria Suíte que criamos),
	 * daí dentro da suíte estarão as 3 classes de testes que declaramos na anotação SuiteClasses e cada classe
	 * possui seus métodos
	 */
	
	/*
	 * As anotações BeforeClass e AfterClass podem ser aplicadas nesse ponto também. Colocando-as na Suíte, eu 
	 * consigo realizar uma operação antes e após toda a bateria de testes respectivamente. Vamos fazer um exemplo
	 * usando o BeforeClass e o AfterClass abaixo e fazer uma impressão no CalculoValorLocacao para vermos que o 
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
