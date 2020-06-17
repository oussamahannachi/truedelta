package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ActifFinancier
 *
 */
@Entity
@Table(name="actif_financier")
public class ActifFinancier implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actifID")
	private int id;
	private String entreprise;
	private float prix;
	private float rendement;
	private float risque;
	private String type; // action ou obligation 
	private float interet;
	private String Currency;
	@Temporal(TemporalType.DATE)
	@Column(name="date_echeane",nullable=true)
	private Date dateEcheance; // Pour obligation
	
	@ManyToOne()
	private Compte compte;
	
	@OneToOne(mappedBy="actif", cascade = CascadeType.ALL, orphanRemoval = true)
	private Transaction transaction;
	
	public ActifFinancier() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntreprise() {
		return entreprise;
	}
	public String getCurrency() {
	    return Currency;	
	}
    public void setCurrency() {
    	this.Currency = Currency;
    }
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getInteret() {
		return interet;
	}

	public void setInteret(float interet) {
		this.interet = interet;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "ActifFinancier [entreprise=" + entreprise + ", prix=" + prix + ", type=" + type + "]";
	}
}
