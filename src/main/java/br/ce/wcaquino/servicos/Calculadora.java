package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

// 2� lugar faremos o que estava faltando na primeira parte, que � a cria��o da Calculadora
public class Calculadora {

	// 3� ele vai estar dizendo que o m�todo 'somar' no teste n�o existe, ent�o fa�amos aqui
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
