package br.com.formento.webApplication.model;

import java.io.Serializable;
import java.util.Locale;

public class CopyOfIdioma implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2006796622105827017L;

	private final Locale locale;

	private transient Mensagem mensagem;
	private transient String pais;
	private transient String descricao;
	private transient String identificador;

	public CopyOfIdioma(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public Mensagem getMensagem() {
		if (mensagem == null)
			mensagem = new Mensagem();
		return mensagem;
	}

	public String getIdentificador() {
		if (identificador == null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(locale.getLanguage());
			if (locale.getCountry() != null && locale.getCountry().length() > 0) {
				stringBuilder.append("_");
				stringBuilder.append(locale.getCountry());
			}

			identificador = stringBuilder.toString();
		}
		return identificador;
	}

	public String getPais() {
		if (pais == null)
			pais = getMensagem().getTexto("idioma.pais." + getIdentificador());
		return pais;
	}

	public String getDescricao() {
		if (descricao == null)
			descricao = getMensagem().getTexto("idioma.lingua." + getIdentificador());
		return descricao;
	}

}
