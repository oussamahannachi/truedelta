package Beans;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import services.CompteService;

@ManagedBean(name="ajouterBean")
@SessionScoped
public class AjouterBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String banquename;
	
	@EJB
	CompteService cs;
	
	public AjouterBean() {}
	
	public String choisrBanque() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		banquename = params.get("name");
		return "";
	}

	public String getBanquename() {
		return banquename;
	}

	public void setBanquename(String banquename) {
		this.banquename = banquename;
	}
	
	
}
