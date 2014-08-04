package br.com.formento.webApplication.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

@Named
@SessionScoped
public class LanguageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7900302688122643039L;

	private static Map<String, Locale> countriesInMap;

	public static Map<String, Locale> getCountriesInMap() {
		if (countriesInMap == null) {
			countriesInMap = new LinkedHashMap<String, Locale>();
			countriesInMap.put("English", Locale.ENGLISH); // label, value
			Locale portuguese = new Locale("pt", "BR");
			countriesInMap.put(portuguese.getCountry(), portuguese);
		}
		return countriesInMap;
	}

	private String localeCode;

	public LanguageBean() {
	}

	public List<Locale> getLocales() {
		List<Locale> arrayList = new ArrayList<Locale>(getCountriesInMap().values());
		return arrayList;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	// value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e) {
		String newLocaleValue = e.getNewValue().toString();
		// loop country map to compare the locale code
		for (Map.Entry<String, Locale> entry : getCountriesInMap().entrySet()) {
			if (entry.getValue().toString().equals(newLocaleValue)) {
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
			}
		}
	}

}