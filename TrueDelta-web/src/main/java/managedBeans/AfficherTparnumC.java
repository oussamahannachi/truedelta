package managedBeans;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entities.*;
import implementations.TransactionServiceImp;

@ManagedBean(name= "ahtc")
@SessionScoped
public class AfficherTparnumC implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date date;
	private String type; // Achat ou vente
    private float rendementTransaction ;
    private Boolean isValide;
    private long numcompte ;
    private int scoreTransaction;
    private ActifFinancier actif;
    private Transaction transaction1 ;
    private Compte compte ;
    private boolean loggedIn ;
    
	private List<Transaction> transactions1;
	private List<Compte> comptes ;
	private List<ActifFinancier> actifs ;
	private List<String> typesT = Arrays.asList("achat", "vente");
	private List<String> valides = Arrays.asList("true", "false");
	@EJB
	TransactionServiceImp ts;
public AfficherTparnumC() {};


public String dochercherlistT()
{
	String navigateTo = "null"; 
	transactions1 = ts.consulterTransactionParCompte1(numcompte) ;

	if (transactions1.isEmpty()==false) {
		navigateTo = "../pages/aft?faces-redirect=true";
		loggedIn = true; 
	}
	else 
	{
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Bad Credentials"));
	}
	return navigateTo; 
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


public Transaction getTransaction1() {
	return transaction1;
}


public void setTransaction1(Transaction transaction1) {
	this.transaction1 = transaction1;
}


public Compte getCompte() {
	return compte;
}


public void setCompte(Compte compte) {
	this.compte = compte;
}


public boolean isLoggedIn() {
	return loggedIn;
}


public void setLoggedIn(boolean loggedIn) {
	this.loggedIn = loggedIn;
}


public List<Transaction> getTransactions1() {
	return transactions1;
}


public void setTransactions1(List<Transaction> transactions1) {
	this.transactions1 = transactions1;
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


public List<String> getTypesT() {
	return typesT;
}


public void setTypesT(List<String> typesT) {
	this.typesT = typesT;
}


public List<String> getValides() {
	return valides;
}


public void setValides(List<String> valides) {
	this.valides = valides;
}

}
