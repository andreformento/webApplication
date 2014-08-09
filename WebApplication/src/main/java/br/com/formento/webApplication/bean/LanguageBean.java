package br.com.formento.webApplication.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class LanguageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7900302688122643039L;

	private static List<Locale> locales;

	static {
		locales = new ArrayList<>();
		locales.add(Locale.ENGLISH);
		locales.add(new Locale("pt", "BR"));
	}

	public LanguageBean() {
	}

	public List<Locale> getLocales() {
		return locales;
	}

	public Locale getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}

	public void setLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

}
