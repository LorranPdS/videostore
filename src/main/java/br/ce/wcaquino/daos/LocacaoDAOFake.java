package br.ce.wcaquino.daos;

import java.util.List;

import br.ce.wcaquino.entidades.Locacao;

public class LocacaoDAOFake implements LocacaoDao {

	@Override
	public void salvar(Locacao locacao) {
		// TODO aqui seria como um método salvar e não colocar nada por ser void
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() {
		return null;
	}

}
