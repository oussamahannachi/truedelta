package entities;

import entities.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Client
 *
 */
@Entity
@DiscriminatorValue(value="client")
public class Client extends Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column(nullable=true)
	private int age;
	private String metier;
	@Column(nullable=true)
	private String etatCivil;
	private float salaire;
	
	@Column(nullable=true,name="date_naissance", updatable=false)
	private Date datenaissance;
	
	@OneToMany(mappedBy="proprietaire", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Compte> comptes= new ArrayList<Compte>();
	
	@OneToMany(mappedBy="client", cascade = CascadeType.ALL)
	private List<Procuration> procurations= new ArrayList<Procuration>();
	
	public Client() { super(); }
	
	public Client(String email,String username,String nom,String prenom,String adresse,String password,String confirmpassword,String etatCivil) {
		super(email,username,nom,prenom,adresse,password,confirmpassword);
		this.etatCivil = etatCivil;
	}

	public Client(String role,String email,String username,String nom,String prenom,String adresse,String password,String confirmpassword,String etatCivil) {
		super(email,username,nom,prenom,adresse,password,confirmpassword);
		this.etatCivil = etatCivil;
	}

	public Client(String role,String email,String username,String nom,String prenom, Date datenaissance,String adresse,String password,String confirmpassword,String etatCivil) {
		super(email,username,nom,prenom,adresse,password,confirmpassword);
		this.etatCivil = etatCivil;
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMetier() {
		return metier;
	}

	public void setMetier(String metier) {
		this.metier = metier;
	}

	public String getEtatCivil() {
		return etatCivil;
	}

	public void setEtatCivil(String etatCivil) {
		this.etatCivil = etatCivil;
	}

	public float getSalaire() {
		return salaire;
	}

	public void setSalaire(float salaire) {
		this.salaire = salaire;
	}

	
	public List<Compte> getComptes() {
		return comptes;
	}

	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}

	
	public List<Procuration> getProcurations() {
		return procurations;
	}

	public void setProcurations(List<Procuration> procurations) {
		this.procurations = procurations;
	}



	@Override
	public String toString() {
		return "Client [metier=" + metier + "]";
	}    
	
}
