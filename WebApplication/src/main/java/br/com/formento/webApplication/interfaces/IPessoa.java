package br.com.formento.webApplication.interfaces;

import java.io.Serializable;

public interface IPessoa extends Serializable {

	String getNome();

	void setNome(String nome);

	String getIdentificador();

	void setIdentificador(String identificador);

}
