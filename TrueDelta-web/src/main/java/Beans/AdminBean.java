package Beans;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import entities.Agence;
import entities.Compte;
import services.CompteService;

@ManagedBean(name = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

	private static final long serialVersionUID = 1L;
	public List<Agence> agences;
	public String etat="";
	public long num;
	public List<Compte> comptes;
	public Compte compte;
	Document doc;

	@EJB
	CompteService cs;

	public AdminBean() {
	}

	@PostConstruct
	public void init() {
		agences = cs.getEm().createQuery("select a from Agence a ORDER BY a.banqueName ", Agence.class).getResultList();

	}

	public List<Agence> getAgences() {
		return agences;
	}

	public void setAgences(List<Agence> agences) {
		this.agences = agences;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public List<Compte> getComptes() {
		comptes = new ArrayList<Compte>();
		List<Compte> list =cs.getAllCompte();
		for (Compte compte : list) {
			if(compte.getIsAutorise()&&compte.getIsActif()) {
				Compte c = cs.getCompteByNumero(compte.getNumero());
				comptes.add(c);
			}
		}
		return comptes;
	}

	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}
	
	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}
	
	public Compte getCompte() {
		return cs.getCompteByNumero(num);
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	public Document getDoc() {
		System.out.println("aaaaaaaaaaaaaaaaaa");
		Compte c=cs.getCompteByNumero(num);
		String name=c.getAgence().getBanqueName().toLowerCase()+c.getAgence().getAgenceName().toLowerCase();
		System.out.println(name);
		doc=cs.getDoc(name, num);
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public String valider(int id) {
		Agence agence = cs.getEm().find(Agence.class, id);
		List<Compte> comptes = cs.getAllCompteByBanque(id);
		LocalDate now = LocalDate.now();
		if(!comptes.isEmpty()) {
			Compte c=comptes.get(0);
			String nom = agence.getBanqueName()+"-"+agence.getAgenceName();
			if(c.getLastVerif().getDate() == now.getDayOfMonth() && (c.getLastVerif().getMonth())+1 ==now.getMonthValue()) {
				etat="Les données de "+nom+" sont déjà traitées.";
			}
			else {
				String path = "C:\\Users\\OUSSAMA HANNACHI\\Desktop\\Uploads\\" + agence.getBanqueName().toUpperCase() + "\\";
				String name = agence.getAgenceName() + now.getDayOfMonth() + now.getMonthValue() + ".xlsx";
				File file = new File(path + name);
				System.out.println(path + name);
				if (! file.exists()) {
					etat = nom+" : n'a pas encore fourni ces données";
					
				}
				else {
					cs.matchData(id);
					etat="Les données de "+nom+" sont bien traitées";
					
				}		
			}
		}
		else {
			etat="Cette agence ne contient aucun compte à verifier";
		
		}
		return "";
	}
	
	public String revalider() {
		List<Agence> agences = cs.getEm().createQuery("select a from Agence a", Agence.class).getResultList();
		for (Agence a : agences) {
			String nom = a.getBanqueName()+"-"+a.getAgenceName();
			if(etat.contains(nom)) {
				Agence agence = cs.getEm().find(Agence.class, a.getId());
				LocalDate now = LocalDate.now();
				String path = "C:\\Users\\OUSSAMA HANNACHI\\Desktop\\Uploads\\" + a.getBanqueName().toUpperCase() + "\\";
				String name = a.getAgenceName() + now.getDayOfMonth() + now.getMonthValue() + ".xlsx";
				File file = new File(path + name);
				if (! file.exists()) {
					etat = nom+" : n'a pas encore fourni ces données";
				}
				else {
					cs.matchData(a.getId());
					etat="Les données de "+nom+" sont traitées une autre fois";
				}
			}
		}
		return "";
	}
	
	public void supprimer(long num) {
		cs.supprimerCompte(num);
	}
	public String informations(long num) {
		this.num=num;
		System.out.println(num);
		return "informations?faces-redirect=true";
	}
}
