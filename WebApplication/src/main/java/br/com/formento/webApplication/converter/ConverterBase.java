package br.com.formento.webApplication.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.com.formento.webApplication.model.ControleEntidade;

import com.google.gson.Gson;

public class ConverterBase<T> implements Converter {

	private ControleEntidade<T> controleEntidade;

	public ControleEntidade<T> getControleEntidade() {
		if (controleEntidade == null)
			controleEntidade = new ControleEntidade<T>(this);
		return controleEntidade;
	}

	public T getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		if (submittedValue.trim().equals("")) {
			return null;
		} else {
			Gson gson = new Gson();
			T fromJson = gson.fromJson(submittedValue, getControleEntidade().getEntidadeClass());
			return fromJson;
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		} else {
			Gson gson = new Gson();
			String json = gson.toJson(value);
			return json;
		}
	}

}
