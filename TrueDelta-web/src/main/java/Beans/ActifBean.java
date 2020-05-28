package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entities.ActifFinancier;
import entities.Company;
import entities.Compte;
import entities.Transaction;
import entities.TypeActif;
import implementation.ActifFinancierService;
import implementation.CompaniesService;



@ManagedBean(name = "actifBean")
@SessionScoped
public class ActifBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String entreprise;private float prix;private float rendement;private float risque;private TypeActif type; // action ou obligation 
	private float interet;private Date date;private Compte compte;private Transaction transaction;
	 private BigDecimal HighPrice;private BigDecimal LowPrice;private BigDecimal  closedprice ; private BigDecimal OpenPrice ;
	 private BigDecimal	AdjClose ; private String Currency;	private Company Company;	 
	private Date dateEcheance; // Pour obligation 
	private BigDecimal BondPrice ;private BigDecimal Parvalue;private BigDecimal Tauxcoupon ;private int Duree;
	private BigDecimal TauxActuariel;private int Fréquence;
		
	
	
	@EJB
	CompaniesService CompaniesService;
	@EJB
	ActifFinancierService ActifFinancierService;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public TypeActif getType() {
		return type;
	}

	public void setType(TypeActif type) {
		this.type = type;
	}

	public float getInteret() {
		return interet;
	}

	public void setInteret(float interet) {
		this.interet = interet;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public BigDecimal getHighPrice() {
		return HighPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		HighPrice = highPrice;
	}

	public BigDecimal getLowPrice() {
		return LowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		LowPrice = lowPrice;
	}


	public BigDecimal getOpenPrice() {
		return OpenPrice;
	}

	public void setOpenPrice(BigDecimal openPrice) {
		OpenPrice = openPrice;
	}

	public BigDecimal getAdjClose() {
		return AdjClose;
	}

	public void setAdjClose(BigDecimal adjClose) {
		AdjClose = adjClose;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public Company getCompany() {
		return Company;
	}

	public void setCompany(Company company) {
		Company = company;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public BigDecimal getBondPrice() {
		return BondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		BondPrice = bondPrice;
	}

	public BigDecimal getParvalue() {
		return Parvalue;
	}

	public void setParvalue(BigDecimal parvalue) {
		Parvalue = parvalue;
	}

	public BigDecimal getTauxcoupon() {
		return Tauxcoupon;
	}

	public void setTauxcoupon(BigDecimal tauxcoupon) {
		Tauxcoupon = tauxcoupon;
	}

	public int getDuree() {
		return Duree;
	}

	public void setDuree(int duree) {
		Duree = duree;
	}

	public BigDecimal getTauxActuariel() {
		return TauxActuariel;
	}

	public void setTauxActuariel(BigDecimal tauxActuariel) {
		TauxActuariel = tauxActuariel;
	}

	public int getFréquence() {
		return Fréquence;
	}

	public void setFréquence(int fréquence) {
		Fréquence = fréquence;
	}

	public ActifFinancierService getActifFinancierService() {
		return ActifFinancierService;
	}

	public void setActifFinancierService(ActifFinancierService actifFinancierService) {
		ActifFinancierService = actifFinancierService;
	}
	
	/*====================================methode=======================================*/
	private List<ActifFinancier> actifs ;
	private ActifFinancier actif;

	
	public List<ActifFinancier> getActifs() {
	//	actifs = ActifFinancierService.GetAction(); 
		return actifs;
	}

	public void setActifs(List<ActifFinancier> actifs) {
		this.actifs = actifs;
	}

	public void displayActif(ActifFinancier ActifFinancier) { 
		this.setClosedprice(ActifFinancier.getClosedPrice());
		this.setHighPrice(ActifFinancier.getHighPrice());
		this.setLowPrice(ActifFinancier.getLowPrice());
		this.setClosedprice(ActifFinancier.getClosedPrice()); 
		this.setCurrency(ActifFinancier.getCurrency());
		
	}

	public BigDecimal getClosedprice() {
		return closedprice;
	}

	public void setClosedprice(BigDecimal closedprice) {
		this.closedprice = closedprice;
	}

	public ActifFinancier getActif() {
		return actif;
	}

	public void setActif(ActifFinancier actif) {
		this.actif = actif;
	}
	public String getCompany(ActifFinancier actif) {
		this.actif = actif;
		return "/pages/company?faces-redirect=true"; 
		}
	public void addactif() { ActifFinancierService.AddStock(new ActifFinancier(" entreprise", 0, 0, 0 ,type,
			0, null, null, null, HighPrice,LowPrice,  closedprice, Currency, null)); }
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	private String search;
	public String seearch() {
		actifs=ActifFinancierService.GetStockByCompany(search);
		return  "/pages/pieChart?faces-redirect=true";
	}
	//public String Asc(String criteria ) {
		public String Asc(String criteria) {
		this.actifs= ActifFinancierService.SortAscending(criteria);
		System.out.println("ASC");
		return  "/pages/actif?faces-redirect=true";
	}
		public String Dsc(String criteria) {
			this.actifs= ActifFinancierService.SortDescending(criteria);
			System.out.println("DSC");
			return  "/pages/actif?faces-redirect=true";
		}
		public String Redirect() {
			System.out.println("redirect");
		this.actifs=ActifFinancierService.GetAction();
		return  "/pages/actif?faces-redirect=true";
	}
		ArrayList<String> listsector = new ArrayList<>();  
		
		public ArrayList<String> getListsector() {
			listsector =(ArrayList<String>) CompaniesService.GetAllSector();
			return listsector;
		}

		public void setListsector(ArrayList<String> listsector) {
			this.listsector = listsector;
		}

		String countryName;  
		public List<String> countryList() {  
		ArrayList<String> list = new ArrayList<>();  
		list = (ArrayList<String>) CompaniesService.GetAllSymbol();
	
		return list;  
		}  
		public String getCountryName() {  
		return countryName;  
		}  
		public void setCountryName(String CountryName) {  
		this.countryName = CountryName;  
		} 
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		
		public Map<String, Integer> getSurveyMap() {
			surveyMap.put("Java", 40);
			surveyMap.put("Dev oops", 25);
			surveyMap.put("Python", 20);
			surveyMap.put(".Net", 15);
			return surveyMap;
		}

		public void setSurveyMap(Map<String, Integer> surveyMap) {
			this.surveyMap = surveyMap;
		}

		public String barGraph() {
			Map<String, Integer> surveyMap = new LinkedHashMap<>();
			surveyMap.put("Java", 40);
			surveyMap.put("Dev oops", 25);
			surveyMap.put("Python", 20);
			surveyMap.put(".Net", 15);
		//	model.addAttribute("surveyMap", surveyMap);
			return "barGraph";
		}
		
		public void pdf(int id) throws IOException {
		    ActifFinancierService.pdfToGenerate(id);
		   // chemin = "Votre pdf se trouve à C:\\Product\\eclipse-wildfly-configured\\wildfly-11.0.0.Final-configured\\bin";
		System.out.println("msg");
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
