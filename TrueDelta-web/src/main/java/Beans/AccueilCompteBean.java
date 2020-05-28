package Beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import entities.*;
import services.CompteService;

@ManagedBean(name="accueilCompteBean",eager=true)
@SessionScoped
public class AccueilCompteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Compte> mescomptes;
	private Compte compte;
	private long numero;
	private float solde;
	private String devise;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean lb;
	
	private List<Compte> allcomptes; 
	private List<Compte> comptesbanques;
	
	
	private List<String> banques = Arrays.asList("Tous","AMEN", "ATB", "ATTIJARI", "BH", "BIAT",
			"BNA", "BT", "STB", "UBCI", "UIB", "ZITOUNA");
	private String filtreBanque="Tous";
	private List<String> devises = Arrays.asList("Tous","dinar", "dollar", "euro");
	private String filtreDevise="Tous";
	private List<String> actifs = Arrays.asList("Tous","Actifs financiers < 5 ", "Actifs financiers < 10", "Actifs financier < 20"," Autres");
	private String filtreActif="Tous";

	
	@EJB
	CompteService cs;
	
	public AccueilCompteBean() {
	}
	
	public LoginBean getLb() {
		return lb;
	}

	public void setLb(LoginBean lb) {
		this.lb = lb;
	}
	
	public List<Compte> getMescomptes() {
		if(lb.getUser()==null)
			return null;
		return cs.getAllCompteByClient(lb.getUser().getId()); // id client
	}

	public void setMescomptes(List<Compte> mescomptes) {
		this.mescomptes = mescomptes;
	}

	public String doDetails(long num) {
		numero=num;
		return "details?faces-redirect=true";
	}
	
	public String doSuprimer() {
		cs.supprimerCompte(numero);
		return "accueil?faces-redirect=true";
	}
	
	public void doModifier() {
		Compte c = cs.getCompteByNumero(numero);
		c.setSolde(solde);
		if(devise.equals("euro"))
			c.setDevise(Devise.euro);
		else if(devise.equals("dollar"))
			c.setDevise(Devise.dollar);
		else c.setDevise(Devise.dinar);
		cs.modifierCompte(c);
	}
	
	public String doActiver() {
		Compte c=cs.getCompteByNumero(numero);
		if(c.getIsActif()) {
			c.setIsActif(false);
			c.setIsVerifie(false);
			cs.modifierCompte(c);
		}
		else {
			c.setIsActif(true);
			cs.modifierCompte(c);
		}
		return "details?faces-redirect=true";
	}

	public void doFiltrer() {
	}
	
	public Compte getCompte() {
		return cs.getCompteByNumero(numero);
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public float getSolde() {
		solde =cs.getCompteByNumero(numero).getSolde();
		return solde;
	}

	public void setSolde(float solde) {
		this.solde = solde;
	}

	public String getDevise() {
		devise = cs.getCompteByNumero(numero).getDevise().toString();
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public List<Compte> getAllcomptes() {
		int actif=actifs.indexOf(filtreActif);
		return cs.filtrerComptes(filtreBanque,filtreDevise,actif);
	}

	public void setAllcomptes(List<Compte> allcomptes) {
		this.allcomptes = allcomptes;
	}

	public List<String> getBanques() {
		return banques;
	}

	public void setBanques(List<String> banques) {
		this.banques = banques;
	}

	public String getFiltreBanque() {
		return filtreBanque;
	}

	public void setFiltreBanque(String filtreBanque) {
		this.filtreBanque = filtreBanque;
	}

	public List<String> getDevises() {
		return devises;
	}

	public void setDevises(List<String> devises) {
		this.devises = devises;
	}

	public String getFiltreDevise() {
		return filtreDevise;
	}

	public void setFiltreDevise(String filtreDevise) {
		this.filtreDevise = filtreDevise;
	}

	public List<String> getActifs() {
		return actifs;
	}

	public void setActifs(List<String> actifs) {
		this.actifs = actifs;
	}

	public String getFiltreActif() {
		return filtreActif;
	}

	public void setFiltreActif(String filtreActif) {
		this.filtreActif = filtreActif;
	}

	public List<Compte> getComptesbanques() {
		if(lb.getAgence()==null)
			return null;
		return cs.getAllCompteByBanque(lb.getAgence().getId()); //id banque
	}

	public void setComptesbanques(List<Compte> comptesbanques) {
		this.comptesbanques = comptesbanques;
	}
	
}
