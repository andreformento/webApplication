package br.com.formento.webserviceServer.model;

import br.com.formento.webserviceServer.interfaces.IPessoa;

public abstract class Pessoa implements IPessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1552789631121227729L;

	private String nome;

	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public void setNome(String nome) {
		this.nome = nome;
	}

}
