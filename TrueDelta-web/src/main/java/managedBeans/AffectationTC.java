
package managedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jsoup.Jsoup;

import entities.ActifFinancier;
import entities.Agence;
import entities.Client;
import entities.Compte;
import entities.ComptePK;
import entities.Devise;
import entities.Transaction;
import entities.Utilisateur;
import implementations.TransactionServiceImp;
@ManagedBean(name = "af")
@SessionScoped
public class AffectationTC implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date date;
	private String type; // Achat ou vente
    private float rendementTransaction ;
    private Boolean isValide;
    private long numcompte ;
    private int scoreTransaction;
    private ActifFinancier actif;
    private Transaction transaction ;
    private Compte compte ;
	private int ida;
	private String entreprise;
	private float prix;
	private float rendement;
	private float risque;
	private String typea; // action ou obligation 
	private float interet;
	private String Currency;
	private Agence agence ;
	private Utilisateur utilisateur;
	
	private Date dateEcheance; 
	private ComptePK idC;
	private String numeroCompte;
	private float solde;
	private long numero;
	private Client proprietaire;
	private String devisecompte;
	
	
	private Devise devise;

	private int nbAction;
	
	private int nbObligation;
	
	private Boolean isAutorise; // Autorisation par la banque
	private Boolean isVerifie; // Verification par l'admin
	
	private Date lastVerif;
    private Date dateOuverture;
    private int idAgence;
    private int idBanque ;
	private int idClient;

	private List<Transaction> transactions;
	private List<Compte> comptes ;
	private List<ActifFinancier> actifs ;
private List<String> devises = Arrays.asList("euro", "dollar", "dinar", "dollarcanadien", "yen",
		"francsuisse", "dirham", "livresterling", "rouble", "baht", "dollaraustralien", "rialduqatar", "riyal");
	

	@EJB
	TransactionServiceImp ts;
	public AffectationTC() {}



	
	
	public String doAffecterCAT() throws IOException {
		Transaction trans = ts.getTransactionById(id);
		ActifFinancier actif = ts.getActifById(ida);
		Compte compt = ts.getCompteByNumero( numero);
	   
	
				
	    
	 		
	 		
	 	
		ts.affecterTransactionCompte1(numero , ida, id, devisecompte);
	return "accueil?faces-redirect=true";
	}
	@PostConstruct
	public void init() {
	     idC = new ComptePK();
	}
	public int getIdAgence() {
		return idAgence;
	}


	public void setIdAgence(int idAgence) {
		this.idAgence = idAgence;
	}





	public ComptePK getIdC() {
		return idC;
	}


	public void setIdC(ComptePK idC) {
		
		this.idC = idC;
	}
	public int getIdClient() {
		return idClient;
	}


	


	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdBanque() {
		return idAgence;
	}

	public void setIdBanque(int idBanque) {
		this.idAgence = idBanque;
	}

	public String getNumeroCompte() {
		return numeroCompte;
	}


	public void setNumeroCompte(String numeroCompte) {
		this.numeroCompte = numeroCompte;
	}


	public float getSolde() {
		return solde;
	}


	public void setSolde(float solde) {
		this.solde = solde;
	}


	public Client getProprietaire() {
		return proprietaire;
	}


	public void setProprietaire(Client proprietaire) {
		this.proprietaire = proprietaire;
	}





	public Devise getDevise() {
		return devise;
	}


	public void setDevise(Devise devise) {
		this.devise = devise;
	}


	public int getNbAction() {
		return nbAction;
	}


	public void setNbAction(int nbAction) {
		this.nbAction = nbAction;
	}


	public int getNbObligation() {
		return nbObligation;
	}


	public void setNbObligation(int nbObligation) {
		this.nbObligation = nbObligation;
	}


	public Boolean getIsAutorise() {
		return isAutorise;
	}


	public void setIsAutorise(Boolean isAutorise) {
		this.isAutorise = isAutorise;
	}


	public Boolean getIsVerifie() {
		return isVerifie;
	}


	public void setIsVerifie(Boolean isVerifie) {
		this.isVerifie = isVerifie;
	}


	public Date getLastVerif() {
		return lastVerif;
	}


	public void setLastVerif(Date lastVerif) {
		this.lastVerif = lastVerif;
	}


	public Date getDateOuverture() {
		return dateOuverture;
	}


	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
	}


	public String getDevisecompte() {
		return devisecompte;
	}


	public void setDevisecompte(String devisecompte) {
		this.devisecompte = devisecompte;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public float getRendementTransaction() {
		return rendementTransaction;
	}


	public void setRendementTransaction(float rendementTransaction) {
		this.rendementTransaction = rendementTransaction;
	}


	public Boolean getIsValide() {
		return isValide;
	}


	public void setIsValide(Boolean isValide) {
		this.isValide = isValide;
	}


	public long getNumcompte() {
		return numcompte;
	}


	public void setNumcompte(long numcompte) {
		this.numcompte = numcompte;
	}


	public int getScoreTransaction() {
		return scoreTransaction;
	}


	public void setScoreTransaction(int scoreTransaction) {
		this.scoreTransaction = scoreTransaction;
	}


	public ActifFinancier getActif() {
		return actif;
	}


	public void setActif(ActifFinancier actif) {
		this.actif = actif;
	}


	public Transaction getTransaction() {
		return transaction;
	}


	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


	public Compte getCompte() {
		return compte;
	}


	public void setCompte(Compte compte) {
		this.compte = compte;
	}


	public int getIda() {
		return ida;
	}


	public void setIda(int ida) {
		this.ida = ida;
	}


	public String getEntreprise() {
		return entreprise;
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


	public String getTypea() {
		return typea;
	}


	public void setTypea(String typea) {
		this.typea = typea;
	}


	public float getInteret() {
		return interet;
	}


	public void setInteret(float interet) {
		this.interet = interet;
	}


	public String getCurrency() {
		return Currency;
	}


	public void setCurrency(String currency) {
		Currency = currency;
	}


	public Date getDateEcheance() {
		return dateEcheance;
	}


	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}


	public List<Transaction> getTransactions() {
		return transactions;
	}


	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}


	public List<Compte> getComptes() {
		return comptes;
	}


	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}


	public List<ActifFinancier> getActifs() {
		return actifs;
	}


	public void setActifs(List<ActifFinancier> actifs) {
		this.actifs = actifs;
	}


	public List<String> getDevises() {
		return devises;
	}


	public void setDevises(List<String> devises) {
		this.devises = devises;
	}


	public Agence getAgence() {
		return agence;
	}


	public void setAgence(Agence agence) {
		this.agence = agence;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public long getNumero() {
		return numero;
	}


	public void setNumero(long numero) {
		this.numero = numero;
	}



	

}
