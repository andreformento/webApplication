package br.com.formento.webApplication.model;

public class PessoaFisica extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3793325595273422279L;

	// TODO depois isso vai virar CPF
	private String identificador;

	@Override
	public String getIdentificador() {
		return identificador;
	}

	@Override
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
