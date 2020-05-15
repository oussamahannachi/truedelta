package Beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import entities.*;
import services.CompteService;

@ManagedBean
@SessionScoped
public class AccueilCompteBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Compte> comptes;
	private Compte compte;
	private long numero;
	private float solde;
	private String devise;
	
	
	@EJB
	CompteService cs;
	
	public AccueilCompteBean() {}

	public List<Compte> getComptes() {
		return cs.getAllCompteByClient(1);
	}

	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
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
	
	
	
}
