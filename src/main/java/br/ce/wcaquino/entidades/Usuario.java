package br.ce.wcaquino.entidades;

public class Usuario {

	private String nome;

	public Usuario() {
	}

	public Usuario(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) // verifica se o objeto é o mesmo
			return true;
		if (obj == null) // verifica se o objeto não é nulo
			return false;
		if (getClass() != obj.getClass()) // verifica se o objeto é da mesma classe
			return false;
		Usuario other = (Usuario) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}