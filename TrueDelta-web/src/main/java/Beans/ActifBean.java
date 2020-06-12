package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import org.primefaces.shaded.json.JSONObject;

import com.sun.mail.handlers.image_gif;

import entities.ActifFinancier;
import entities.Company;
import entities.Compte;
import entities.Transaction;
import entities.TypeActif;
import implementation.ActifFinancierService;
import implementation.CompaniesService;
import implementation.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@ManagedBean(name = "actifBean")
@SessionScoped
public class ActifBean implements Serializable {

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
	

	
	@EJB
	UserService us;
	@EJB
	CompaniesService CompaniesService;
	@EJB
	ActifFinancierService ActifFinancierService;

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
	actifs=ActifFinancierService.GetStockByCompany("FB"); 
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
		actifs=ActifFinancierService.GetStockBySector( sector);
		return  actifs;
	}
	//public String Asc(String criteria ) {
		public List<ActifFinancier> asc() {
		actifs=ActifFinancierService.SortAscending("ClosedPrice");
		System.out.println("ASC");
		return  actifs;
	}
		public List<ActifFinancier> dsc(String criteria) {
			actifs =ActifFinancierService.SortDescending(criteria);
			System.out.println("DSC");
			return  actifs;
		}
		public List<ActifFinancier> redirect() {
			System.out.println("redirect");
			actifs =ActifFinancierService.GetAction();
		return actifs;
	}
		
	
		public List<ActifFinancier> getHist() {
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
	public List<String> getDates() {
			dates =new ArrayList<String>();
			
			for (ActifFinancier a : getHist()) {
				
				dates.add("'"+a.getDate().toLocaleString()+"'");
			}
			return dates;
		}

		public void setDates(List<String> dates) {
			this.dates = dates;
		}

		public List<BigDecimal> getPrice() {
			price = new ArrayList<BigDecimal>();
			for (ActifFinancier a : this.getHist()) {
				price.add(a.getClosedPrice());
				
			}
			return price;
		}

		public void setPrice(List<BigDecimal> price) {
			this.price = price;
		}

		
		/***********************portefeuil***********************************/
		
		public void pdf() throws IOException {
			System.out.println("msg");
		    try {
				ActifFinancierService.pdfToGenerate(1);
				 ActifFinancierService.openpdf(1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		}
		private List<ActifFinancier> portefeuil ;

		public List<ActifFinancier> getPortefeuil() {
			portefeuil=ActifFinancierService.getStockByClient2(1);
			return portefeuil;
		}

		public void setPortefeuil(List<ActifFinancier> portefeuil) {
			this.portefeuil = portefeuil;
		}
		public double gain(String name,Double prix) {
			double gain=  ActifFinancierService.getPriceInstantly(name) - (prix);
			return gain;
		}
		
		ArrayList<Double> listgain;
		
		public ArrayList<Double> getListgain() {
			return listgain;
		}

		public void setListgain(ArrayList<Double> listgain) {
			this.listgain = listgain;
		}

		public double gaintotal(List<ActifFinancier> portefeuil) {
			listgain=new ArrayList<Double>();
			double gain= 0.0;
			for(ActifFinancier a : portefeuil )
			{ double x = ( ActifFinancierService.getPriceInstantly(a.getCompany().getSymbol()) - (a.getPrix()));
			 gain=gain +( ActifFinancierService.getPriceInstantly(a.getCompany().getSymbol()) - (a.getPrix()));
			 listgain.add(x);
			}
			return gain;
		}
		public double totalprix(List<ActifFinancier> portefeuil) {
			double prix= 0.0;
			for(ActifFinancier a : portefeuil )
			{
			 prix=prix + (ActifFinancierService.getPriceInstantly(a.getCompany().getSymbol())) ;
			}
			return prix;
		}
		private int quantite;
		public int quantiite(String name,int id) {
			quantite= (int)ActifFinancierService.getQuantite(name,id);
			return quantite;
		}
		public long getQuantite() {
			return quantite;
		}

		public void setQuantite(int quantite) {
			this.quantite = quantite;
		}
		
		public double RendementAnnuel;

		public double getRendementAnnuel() {
		
			return RendementAnnuel;
		}	
		public double rendementAnuel(String name) {
			try {
				RendementAnnuel = ActifFinancierService.RendementAnnuel(name, 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return RendementAnnuel;
		}
		double rendement = 0;
		
		public double getRendement() {
			return rendement;
		}

		public void setRendement(double rendement) {
			this.rendement = rendement;
		}

		public double rendementByPeriod(String name) {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
			try {
				System.out.println(stringdate1);
				System.out.println(stringdate2);
				Date d1 = dateFormat.parse("2020-01-01");
				Date d2 = dateFormat.parse("2020-04-01");
				rendement = 1;
				System.out.println("1"+rendement);
				rendement = ActifFinancierService.RendementByPeriod(name, d1, d2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("2"+rendement);
			return rendement;
		}

		public void setRendementAnnuel(double rendementAnnuel) {
			RendementAnnuel = rendementAnnuel;
		}
		public double getPriceInstantly(String sym)
		{
			return ActifFinancierService.getPriceInstantly(sym);
		}
		public BigDecimal poit(String name,int id) {

			 long q =(int)ActifFinancierService.getQuantite(name, id);			
			return  (new BigDecimal(q).divide(new BigDecimal(4)));
		}
		public BigDecimal volatility(String name) {
			return  new BigDecimal(ActifFinancierService.Volatility(name), new MathContext( 4, RoundingMode.HALF_UP));
		}
		public BigDecimal volatilitypondere(String name,int id) {
			return (this.poit(name,id).multiply(this.volatility(name), new MathContext( 4, RoundingMode.HALF_UP)));
		}
		public BigDecimal Sommevolatilitypondere(List<ActifFinancier> portefeuil) {
			BigDecimal s = BigDecimal.ZERO;
			
			for(ActifFinancier a : portefeuil) s=s.add(this.volatilitypondere(a.getCompany().getSymbol(),a.getCompte().getId().getIdClient()),new MathContext( 4, RoundingMode.HALF_UP));
			return s;
		}
		public BigDecimal ratioSharp(List<ActifFinancier> portefeuilp ,double tauxsansriqu) {
			BigDecimal ratio = BigDecimal.ZERO;
			ratio= new BigDecimal( ActifFinancierService.Rendementportfeill(portefeuilp, 1)-tauxsansriqu);
			BigDecimal s= Sommevolatilitypondere(portefeuilp);
			ratio=ratio.divide(s,4);
			return ratio;
		}
		public BigDecimal ratioSharpperson(BigDecimal ratio) {
			return ratio.multiply(new BigDecimal(100));
		}
		
		List<String> symboles = new ArrayList<String>();
		public List<String> symbole(){
			 symboles = new ArrayList<String>();
			for (ActifFinancier a : ActifFinancierService.getStockByClient2(1)) {
				
				symboles.add("'"+a.getCompany().getSymbol()+"'");
			}
		return symboles;
		}
		private List<Integer> quns = new ArrayList<Integer>();
		public List<Integer> quns(){
			quns = new ArrayList<Integer>();
			for (ActifFinancier a : portefeuil) {
				quns.add((int) quantiite(a.getCompany().getSymbol(),1));
			}
			
		return quns;
		}

		public List<String> getSympoles() {
			for (ActifFinancier a : ActifFinancierService.getStockByClient2(1)) {
				
				symboles.add("'"+a.getCompany().getSymbol()+"'");
			}
			
			return symboles;
		}

		public void setSympoles(List<String> symboles) {
			this.symboles = symboles;
		}

		public List<Integer> getQuns() {
			
			return quns;
		}

		public void setQuns(List<Integer> quns) {
			this.quns = quns;
		}

	
		private List<Integer> listid ;
		
		public List<Integer> getListid() {
			listid=us.listclientid();
			return listid;
		}

		public void setListid(List<Integer> listid) {
			this.listid = listid;
		}

		public Map<Integer,List<ActifFinancier>> ttportfeuill(){
			 Map<Integer,List<ActifFinancier>> portefeuil = new HashMap<Integer, List<ActifFinancier>>();
			 for(int id : this.listid)
			 {
				 portefeuil.put(id, ActifFinancierService.getStockByClient2(id));	
			 }
			 return portefeuil;
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
}
