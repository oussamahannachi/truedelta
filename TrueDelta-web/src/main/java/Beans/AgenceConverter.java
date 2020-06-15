package Beans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import entities.Agence;
import services.CompteService;

@ManagedBean
@RequestScoped
public class AgenceConverter implements Converter {

	@EJB
	CompteService cs;
	
	@Override
	public Agence getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		 return cs.getEm().find(Agence.class,Integer.parseInt(submittedValue));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object o) {
		if (o == null || !(o instanceof Agence)) {
            return null;
        }
		
        return String.valueOf(((Agence) o).getId());
	}
	
}
