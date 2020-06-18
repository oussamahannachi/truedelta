package Beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.*;

import entities.TypeActif;
import services.*;

@ManagedBean(name = "data") 
@ApplicationScoped 
public class Data implements Serializable { 
	
	private static final long serialVersionUID = 1L; 
	public TypeActif[] getTypeActif() { 
		System.out.println("testtype");
		return TypeActif.values(); 
}
	@EJB 
	CompaniesService CompaniesService;
	public List<String> getListsector() {
		return CompaniesService.GetAllSector();
		
	}
}
