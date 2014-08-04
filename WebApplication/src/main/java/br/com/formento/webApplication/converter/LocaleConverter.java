package br.com.formento.webApplication.converter;

import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

@FacesConverter("localeConverter")
public class LocaleConverter implements Converter {

	public Locale getAsObject(FacesContext facesContext, UIComponent uiComponent, String jsonObjectStr) {
		// if (language != null && language.trim().length() > 0) {
		// Locale locale = new Locale(language);
		// return locale;
		// } else
		// return null;
		if (jsonObjectStr == null || jsonObjectStr.trim().length() == 0)
			return null;
		else {
			try {
				JSONObject jsonObject = new JSONObject(jsonObjectStr);
				Object object = jsonObject.get("key");
				if (object instanceof Locale)
					return (Locale) object;
				else
					return null;
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object object) {
		if (object instanceof Locale) {
			Locale locale = (Locale) object;

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("key", locale);
				return jsonObject.toString();
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		} else
			return null;
	}

}