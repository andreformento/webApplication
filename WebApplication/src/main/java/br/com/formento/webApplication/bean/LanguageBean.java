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

	private static List<Locale> listLocale;

	static {
		listLocale = new ArrayList<>();
		listLocale.add(new Locale("pt", "BR"));
		listLocale.add(Locale.ENGLISH);
	}
	
	public LanguageBean() {
		System.out.println("iniciou "+getLocale());
	}

	public Locale getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}

	public void setLocale(Locale locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
	}

	public List<Locale> getListLocale() {
		return listLocale;
	}

}
