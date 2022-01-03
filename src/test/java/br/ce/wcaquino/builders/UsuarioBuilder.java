package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

// 1) Aqui temos o nosso primeiro Builder montado. Começamos montando um builder de usuário
public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	/*
	 *  O método abaixo ficou público e estático para que ele possa ser acessado externamente 
	 *  sem a necessidade de uma instância, e ele será a porta de entrada para a criação de um usuário
	 */
	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Usuário 1");
		return builder;
	}
	
	public Usuario agora() {
		return usuario;
	}
}
