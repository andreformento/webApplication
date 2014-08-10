package br.com.formento.webApplication.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.formento.webApplication.model.Idioma;

@Named
@SessionScoped
public class LanguageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7900302688122643039L;

	private static List<Idioma> listIdioma;

	static {
		listIdioma = new ArrayList<>();
		listIdioma.add(new Idioma(new Locale("pt", "BR")));
		listIdioma.add(new Idioma(Locale.ENGLISH));
	}

	private Idioma idioma;

	public List<Idioma> getListIdioma() {
		return listIdioma;
	}

	public LanguageBean() {
		idioma = new Idioma(FacesContext.getCurrentInstance().getViewRoot().getLocale());
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = new Idioma(idioma.getLocale());
		FacesContext.getCurrentInstance().getViewRoot().setLocale(this.idioma.getLocale());
	}

}
