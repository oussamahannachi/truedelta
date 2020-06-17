package managedBeans;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import entities.ActifFinancier;
import entities.Transaction;
import implementations.TransactionServiceImp;

@ManagedBean(name= "profit")
@SessionScoped
public class Profit implements Serializable {
	private int id;
	private Date date;
	private String type; // Achat ou vente
    private float rendementTransaction ;
    private Boolean isValide;
    private long numcompte ;
    private int scoreTransaction;
    private ActifFinancier actif;
	private List<Transaction> transactions;
	private int j;
	private int m;
	private int y;
	private double r ;
	private double rj ;
	private double rm ;
	private double ry ;
	private int tvt;
	private int tvtj;
	private int tvtm;
	private int tvty;
	
	
	private List<Integer> jours = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
	private List<Integer> mois = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
	

	@EJB
	TransactionServiceImp ts;

	public double getRj() {
		return rj;
	}
	public void setRj(double rj) {
		this.rj = rj;
	}
	public double getRm() {
		return rm;
	}
	public void setRm(double rm) {
		this.rm = rm;
	}
	public double getRy() {
		return ry;
	}
	public void setRy(double ry) {
		this.ry = ry;
	}
	public int getTvt() {
		return tvt;
	}
	public void setTvt(int tvt) {
		this.tvt = tvt;
	}
	public int getTvtj() {
		return tvtj;
	}
	public void setTvtj(int tvtj) {
		this.tvtj = tvtj;
	}
	public int getTvtm() {
		return tvtm;
	}
	public void setTvtm(int tvtm) {
		this.tvtm = tvtm;
	}
	public int getTvty() {
		return tvty;
	}
	public void setTvty(int tvty) {
		this.tvty = tvty;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
	public double getR() {
		return r;
	}
	public void setR(double r) {
		this.r = r;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public List<Integer> getJours() {
		return jours;
	}
	public void setJours(List<Integer> jours) {
		this.jours = jours;
	}
	public List<Integer> getMois() {
		return mois;
	}
	public void setMois(List<Integer> mois) {
		this.mois = mois;
	}
	public Profit() {};
	public void profittotal() {
	r = ts.profittotal();
		}
	public void  nbTVtotal() {
		tvt = ts.getNbtransvalide();
	}
	public void profittotalj() {
	rj = ts.profitparJ();
		}
	public void  nbTVtotalj() throws ParseException {
		tvtj = ts.getNbtransvalideparJour();
	}
	public void profittotalm() {
	rm = ts.profitparM();
		}
	public void  nbTVtotalm() throws ParseException {
		tvtm = ts.getNbtransvalideparMois();
	}
	public void profittotaly() {
	ry = ts.profitparannee();
		}
	public void  nbTVtotaly() throws ParseException {
		tvty = ts.getNbtransvalideparAnnee();
	}

}
