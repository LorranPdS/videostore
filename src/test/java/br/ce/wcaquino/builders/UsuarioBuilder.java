package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

// 1) Aqui temos o nosso primeiro Builder montado. Come�amos montando um builder de usu�rio
public class UsuarioBuilder {

	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	/*
	 *  O m�todo abaixo ficou p�blico e est�tico para que ele possa ser acessado externamente 
	 *  sem a necessidade de uma inst�ncia, e ele ser� a porta de entrada para a cria��o de um usu�rio
	 */
	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Usu�rio 1");
		return builder;
	}
	
	public Usuario agora() {
		return usuario;
	}
}
