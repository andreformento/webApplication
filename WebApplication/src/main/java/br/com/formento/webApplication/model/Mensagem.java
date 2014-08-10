package br.com.formento.webApplication.model;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Mensagem {

	private ResourceBundle resourceBundle;

	public ResourceBundle getResourceBundle() {
		if (resourceBundle == null) {
			FacesContext context = FacesContext.getCurrentInstance();
			resourceBundle = context.getApplication().getResourceBundle(context, "msg");
		}
		return resourceBundle;
	}

	public String getTexto(String key) {
		return getResourceBundle().getString(key);
	}

}
