package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

// 2° lugar faremos o que estava faltando na primeira parte, que é a criação da Calculadora
public class Calculadora {

	// 3° ele vai estar dizendo que o método 'somar' no teste não existe, então façamos aqui
	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int divide(int a, int b) throws NaoPodeDividirPorZeroException {
		if(b == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		return a / b;
	}

}
