package br.edu.ifce.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import com.sun.faces.util.MessageFactory;

@FacesValidator("br.edu.ifce.ImagemLugar")
public class UploadValidator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		Part arquivo = (Part) value;
		
		if (arquivo != null && !"image/jpeg".equals(arquivo.getContentType()) && !"image/png".equals(arquivo.getContentType())) {
			Object label = MessageFactory.getLabel(context, component);
			
			String descricaoErro = label + " não corresponde a uma imagem válida! (.jpeg ou .png)" ;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					descricaoErro, descricaoErro);
			throw new ValidatorException(message);
		}
		
	}
}
