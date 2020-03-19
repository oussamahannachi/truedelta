package entities;

import entities.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
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
	
	@OneToMany(mappedBy="proprietaire", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Compte> comptes= new ArrayList<Compte>();
	
	@OneToMany(mappedBy="client", cascade = CascadeType.ALL)
	private List<Procuration> procurations= new ArrayList<Procuration>();
	
	@OneToMany(mappedBy="client", cascade = CascadeType.ALL)
	private List<Reclamation> reclamations= new ArrayList<Reclamation>();
	
	public Client() { super(); }

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

	public List<Reclamation> getReclamations() {
		return reclamations;
	}

	public void setReclamations(List<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}

	@Override
	public String toString() {
		return "Client [metier=" + metier + "]";
	}    
	
}
