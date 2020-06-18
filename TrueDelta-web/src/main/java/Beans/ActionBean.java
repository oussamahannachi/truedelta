package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.ActifFinancier;
import entities.Company;
import entities.Compte;
import entities.Transaction;
import entities.TypeActif;
import services.*;

@ManagedBean(name = "actionBean")
@SessionScoped
public class ActionBean implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String entreprise;private float prix;private TypeActif type; // action ou obligation 
	private float interet;private Date date;private Compte compte;private Transaction transaction;
	 private BigDecimal HighPrice;private BigDecimal LowPrice;private BigDecimal  closedprice ; private BigDecimal OpenPrice ;
	 private BigDecimal	AdjClose ; private String Currency;	private Company Company;	 
	private Date dateEcheance; // Pour obligation 
	private BigDecimal BondPrice ;private BigDecimal Parvalue;private BigDecimal Tauxcoupon ;private int Duree;
	private BigDecimal TauxActuariel;private int Fréquence;
	
	
	
	private String CURRENCYCOM;
	
	private ActifFinancier a ;
	
	
	public ActifFinancier getA() {
		return a;
	}


	public void setA(ActifFinancier a) {
		this.a = a;
	}
	@EJB
	UserService us;
	@EJB
	CompaniesService CompaniesService;
	@EJB
	ActifFinancierService ActifFinancierService;

	
	private List<ActifFinancier> actions;

	public List<ActifFinancier> getActions() {
		return actions;
	}
	public void setActions(List<ActifFinancier> actions) {
		this.actions = actions;
	}

	public List<ActifFinancier> getAll() {
		actions= ActifFinancierService.GetAction();
		return actions;
	}
	
	public int addaction(  ActifFinancier a) {
		 
			// System.out.println(proxycomp.GetCompanyBySymbol(a.getCompany().getSymbol())==null);
			int j ;
			 if (CompaniesService.GetCompanyBySymbol(a.getCompany().getSymbol())==null)
			 {
				j = CompaniesService.AddCompany(a.getCompany());
					a.getCompany().setId(j);
				System.out.println("if"+j);
				
			 }else
				 {  j= CompaniesService.GetCompanyBySymbol(a.getCompany().getSymbol()).getId();
				 a.getCompany().setId(j);	
				 System.out.println("else"+j);
				 } 
			 id = ActifFinancierService.AddStock(a);
			 ActifFinancierService.affecterActifAcompany(id, j);
		
		return id;
	}
	public ActifFinancier getaction(String name) {
		 a = new ActifFinancier();
		try {
			a =ActifFinancierService.getHistory(name, 0, "Daily").get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(a);
		return a;
	}
	
	
	public ActifFinancier findaction() {
		ActifFinancier a = new ActifFinancier();
		a =ActifFinancierService.GetStockById(12);
		return a;
	}
	
public String getCURRENCYCOM() {
	CURRENCYCOM ="";
	if (actif.getCompany().getSymbol() == "FB")
	{CURRENCYCOM ="CURRENCYCOM:"+actif.getCompany().getSymbol();}
	else {CURRENCYCOM =actif.getCompany().getSymbol();}
	
	return CURRENCYCOM;
}

public void setCURRENCYCOM(String cURRENCYCOM) {
	CURRENCYCOM = cURRENCYCOM;
}
private List<ActifFinancier> actifs ;
private ActifFinancier actif;
	
 
	public List<ActifFinancier> getActifs() {
		return actifs;
	}

	public void setActifs(List<ActifFinancier> actifs) {
		this.actifs = actifs;
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
	public String go(ActifFinancier actifenvoyer) {
		this.actif = actifenvoyer;
		
		return "/pages/company?faces-redirect=true"; 
		//return "company.xhtml";
		}
	ArrayList<String> listsector = new ArrayList<>();  
	
	public ArrayList<String> getListsector() {
		listsector =(ArrayList<String>) CompaniesService.GetAllSector();
		return listsector;
	}

	public void setListsector(ArrayList<String> listsector) {
		this.listsector = listsector;
	}

	/*====================================methode=======================================*/
	
	public void addactif() { ActifFinancierService.AddStock(new ActifFinancier(" entreprise", 0, 0, 0 ,type,
			0, null, null, null, HighPrice,LowPrice,  closedprice, Currency, null)); }
	
	private String stringdate ;
	public String getStringdate() {
		return stringdate;
	}

	public void setStringdate(String stringdate) {
		this.stringdate = stringdate;
	}
	private String stringdate1 ;
	public String getStringdate1() {
		return stringdate1;
	}

	public void setStringdate1(String stringdate1) {
		this.stringdate1 = stringdate1;
	}
	
	private String stringdate2 ;
	
	public String getStringdate2() {
		return stringdate2;
	}

	public void setStringdate2(String stringdate2) {
		this.stringdate2 = stringdate2;
	}
	private String search1;
	private String search2;
	
	public String getSearch1() {
		return search1;
	}

	public void setSearch1(String search1) {
		this.search1 = search1;
	}

	public String getSearch2() {
		return search2;
	}

	public void setSearch2(String search2) {
		this.search2 = search2;
	}
	private String search;
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	
	public List<ActifFinancier> namesearch() {
	actifs=ActifFinancierService.GetStockByCompany(search1); 
	return actifs;
	}
	public List<ActifFinancier> seearch() {
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
			try {
//				Date d1 = dateFormat.parse("01/05/2019");
				Date d1 = dateFormat.parse(stringdate);
				
				System.out.println("d"+d1);
				actifs=ActifFinancierService.GetStockByCompanyDate(search, d1);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return  actifs;
	}
	
	public List<ActifFinancier> seearchperiode() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
			try {
				Date d1 = dateFormat.parse(stringdate1);
				Date d2 = dateFormat.parse(stringdate2);
				
				
				actifs=ActifFinancierService.GetStockByCompanyPeriode(search2, d1, d2);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		return  actifs;
	}
	
	public List<ActifFinancier> sector(String sector) {
		actions=ActifFinancierService.GetStockBySector( sector);
		return  actions;
	}
	//public String Asc(String criteria ) {
		public List<ActifFinancier> asc() {
			actions=ActifFinancierService.SortAscending("ClosedPrice");
		System.out.println("ASC");
		return  actions;
	}
		public List<ActifFinancier> dsc(String criteria) {
			actions =ActifFinancierService.SortDescending(criteria);
			System.out.println("DSC");
			return  actions;
		}
		public List<ActifFinancier> redirect() {
			System.out.println("redirect");
			actifs =ActifFinancierService.GetAction();
		return actifs;
	}
		
	
		public List<ActifFinancier> getHist(ActifFinancier actif) {
			List<ActifFinancier> hist = new ArrayList<ActifFinancier>();
			try {
				hist = ActifFinancierService.getHistory("FB");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return hist;
			
		}

		
		
		private List<String> dates = new ArrayList<String>();

	private	 List<BigDecimal> price = new ArrayList<BigDecimal>();
	public List<String> getDates(ActifFinancier actif) {
			dates =new ArrayList<String>();
			
			for (ActifFinancier a : getHist(actif)) {
				
				dates.add("'"+a.getDate().toLocaleString()+"'");
			}
			return dates;
		}

		public void setDates(List<String> dates) {
			this.dates = dates;
		}

		public List<BigDecimal> getPrice(ActifFinancier actif) {
			price = new ArrayList<BigDecimal>();
			for (ActifFinancier a : this.getHist(actif)) {
				price.add(a.getClosedPrice());
				
			}
			return price;
		}

		public void setPrice(List<BigDecimal> price) {
			this.price = price;
		}

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

		public List<String> getDates() {
			return dates;
		}

		public List<BigDecimal> getPrice() {
			return price;
		}

		
}
