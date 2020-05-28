package Beans;
import java.io.Serializable;

import javax.faces.bean.*;

import entities.TypeActif;


@ManagedBean(name = "data") 
@ApplicationScoped 
public class Data implements Serializable { 
	private static final long serialVersionUID = 1L; 
	public TypeActif[] getTypeActif() { return TypeActif.values(); 
	
}
}
