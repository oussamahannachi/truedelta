package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Procuration */
//@SuppressWarnings("serial")
@Entity
public class Procuration implements Serializable {


	private static final long serialVersionUID =  8983558202217591746L;
	
	@EmbeddedId
	private ProcurationPK id;
	
	@Column(name="date_creation")
	private Date dateCreation;
	
	@Column(unique=true)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int numero; // numero de procuration pour que chaque procuration soit unique
	
	@ManyToOne
	@JoinColumn(name="idClient", referencedColumnName="utilisateurID", insertable=false , updatable=false, unique=false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="idCourtier", referencedColumnName="utilisateurID", insertable=false , updatable=false,unique=false)
	private Courtier courtier;
	
	private String etat; // Etat de procuration : en cours , terminé , bloqué ...
	@Enumerated(EnumType.STRING)
	private Type type;
	@Column(nullable=true)
	private float rendement;
	@Column(nullable=true)
	private float risque;
	@Column(nullable=true)
	private int dure; // Duree du mondat exprimé en jours
	@Column(nullable=true)
	private String description; // Pour ecrire la proposition
	
	public Procuration() {}
	public Procuration(ProcurationPK id, Date dateCreation, int numero, String etat,
			Type type, float rendement, float risque, int dure, String description) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.numero = numero;
		this.etat = etat;
		this.type = type;
		this.rendement = rendement;
		this.risque = risque;
		this.dure = dure;
		this.description = description;
	}

	public ProcurationPK getId() {
		return id;
	}

	public void setId(ProcurationPK id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Courtier getCourtier() {
		return courtier;
	}

	public void setCourtier(Courtier courtier) {
		this.courtier = courtier;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public float getRendement() {
		return rendement;
	}

	public void setRendement(float rendement) {
		this.rendement = rendement;
	}

	public float getRisque() {
		return risque;
	}

	public void setRisque(float risque) {
		this.risque = risque;
	}

	public int getDure() {
		return dure;
	}

	public void setDure(int dure) {
		this.dure = dure;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Procuration [dateCreation=" + dateCreation + ", etat=" + etat + "]";
	}
   
}
