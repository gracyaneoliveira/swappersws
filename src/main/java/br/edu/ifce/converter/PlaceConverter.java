package br.edu.ifce.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.ifce.dao.transaction.PlaceTransactionDAO;
import br.edu.ifce.model.Place;

@FacesConverter(forClass=Place.class)
public class PlaceConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Place retorno = null;
		
		if (value != null && !value.equals("")) {
			retorno = PlaceTransactionDAO.getPlaceById(value);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Integer codigo = ((Place) value).getId();
			return codigo == null ? "" : codigo.toString();
		}
		return null;
	}
	
}
