package managedBeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.ActifFinancier;
import entities.Compte;
import entities.Transaction;
import implementations.TransactionServiceImp;
@ManagedBean(name= "classscore")
@SessionScoped
public class Classerscore implements Serializable{
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
	private List<Transaction> transactionfaible;

	private List<Transaction> transactionmoyen;
	private List<Transaction> transactiontop;

	private List<Compte> comptes ;
	private List<ActifFinancier> actifs ;
	@EJB
	TransactionServiceImp ts;
	public Classerscore() {};
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
	public List<Transaction> getTransactionfaible() {
		transactionfaible = ts.consulterTransactionScoreFaible();
		return transactionfaible;
	}
	public void setTransactionfaible(List<Transaction> transactionfaible) {
		this.transactionfaible = transactionfaible;
	}
	public List<Transaction> getTransactionmoyen() {
		transactionmoyen = ts.consulterTransactionScoreMoyen();
		return transactionmoyen;
	}
	public void setTransactionmoyen(List<Transaction> transactionmoyen) {
		this.transactionmoyen = transactionmoyen;
	}
	public List<Transaction> getTransactiontop() {
		transactiontop = ts.consulterTransactionScoreTop();
		return transactiontop;
	}
	public void setTransactiontop(List<Transaction> transactiontop) {
		this.transactiontop = transactiontop;
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
	
}
