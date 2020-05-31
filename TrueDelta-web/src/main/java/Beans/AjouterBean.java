package Beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import entities.Agence;
import entities.Compte;
import entities.ComptePK;
import entities.Devise;
import services.CompteService;

@ManagedBean(name="ajouterBean")
@SessionScoped
public class AjouterBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean lb;
	
	private String banquename;
	
	private int agenceID;
	private List<Agence> agences;
	
	private int numero;
	private int nbactions;
	private int nbobligation;
	private int solde;
	private String date;
	
	private String devise;
	private List<String> deviseslist = Arrays.asList("dinar", "dollar", "euro");
	
	@EJB
	CompteService cs;
	
	public AjouterBean() {}
	
	@PostConstruct
	public void init() {
		
	}
	
	public String choisirBanque() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		banquename = params.get("name");
		System.out.println(banquename);
		return "ajoutercompte?faces-redirect=true";
	}

	public String getBanquename() {
		return banquename;
	}

	public void setBanquename(String banquename) {
		this.banquename = banquename;
	}

	public int getNbactions() {
		return nbactions;
	}

	public void setNbactions(int nbactions) {
		this.nbactions = nbactions;
	}

	public int getNbobligation() {
		return nbobligation;
	}

	public void setNbobligation(int nbobligation) {
		this.nbobligation = nbobligation;
	}

	public int getSolde() {
		return solde;
	}

	public void setSolde(int solde) {
		this.solde = solde;
	}

	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<String> getDeviseslist() {
		return deviseslist;
	}

	public void setDeviseslist(List<String> devises) {
		this.deviseslist = devises;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public LoginBean getLb() {
		return lb;
	}

	public void setLb(LoginBean lb) {
		this.lb = lb;
	}

	public List<Agence> getAgences() {
		return cs.getAllAgence(banquename, lb.getUser().getId());
		
	}

	public void setAgences(List<Agence> agences) {
		this.agences = agences;
	}
	
	public int getAgenceID() {
		return agenceID;
	}

	public void setAgenceID(int agenceID) {
		this.agenceID = agenceID;
	}

	public String ajouterCompte() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String[] s=date.split("-");
		String dateouverture = s[2]+"/"+s[1]+"/"+s[0];
		
		Compte c= new Compte();
		ComptePK id= new ComptePK();
		id.setIdBanque(agenceID);
		id.setIdClient(lb.getUser().getId());
		c.setId(id);
		c.setNumero((long)numero);
		c.setNbAction(nbactions);
		c.setNbObligation(nbobligation);
		c.setDateOuverture(format.parse(dateouverture));
		c.setDevise(Devise.valueOf(devise));
		
		cs.ajouterCompte(c);
		return "accueil?faces-redirect=true";
	}
	
}
