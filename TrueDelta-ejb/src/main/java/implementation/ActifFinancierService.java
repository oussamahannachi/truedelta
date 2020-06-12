package implementation;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;



import entities.ActifFinancier;
import entities.Client;
import entities.Company;
import entities.Compte;
import entities.Transaction;
import entities.TypeActif;
import entities.Utilisateur;
import interfaces.ActifFinancierServiceRemote;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;

//import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import java.awt.Desktop;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

@Stateless
@LocalBean
public class ActifFinancierService implements ActifFinancierServiceRemote {
	@PersistenceContext
	EntityManager em;

	@Override
	public void RemoveActif(int id) {
		em.remove(em.find(ActifFinancier.class, id));		
	}
	
	@Override
	public int AddStock(ActifFinancier ActifFinancier) {
	//	CompaniesService cs = new CompaniesService();
	//	if (cs.GetCompanyBySymbol(ActifFinancier.getCompany().getSymbol()) == null)
		//	{cs.AddCompany(ActifFinancier.getCompany());
	//	em.persist(ActifFinancier);
		//return ActifFinancier.getId();
		//}
		//else 	
			em.persist(ActifFinancier);
		return ActifFinancier.getId();
	}
	@Override
	public void affecterActifAcompany(int ActifId, int compid) {
				ActifFinancier ActifFinancierManagedEntity = em.find(ActifFinancier.class, ActifId);
				Company	 CompanyManagedEntity = em.find(Company.class, compid);
				ActifFinancierManagedEntity.setCompany(CompanyManagedEntity);
	}


	@Override
	public ActifFinancier GetStockById(int StockId) {
		return em.find(ActifFinancier.class, StockId);
	}

	@Override
	public  List<ActifFinancier> GetStockByCompany(String name) {
		TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a join a.Company c  where c.Symbol=:name ORDER BY a.date", ActifFinancier.class);
		query.setParameter("name", name);		
		return query.getResultList();
	}
	@Override
	public  List<ActifFinancier> GetStockBySector(String sector) {
		TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a join a.Company c  where c.Sector=:sector ORDER BY a.date", ActifFinancier.class);
		query.setParameter("sector", sector);		
		return query.getResultList();
	}
	@Override
	public  List<ActifFinancier> GetStockByCompanyPeriode(String name, Date Period1, Date Period2) {
			TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a join a.Company c  where c.Symbol=:name and a.date>:Period1 and a.date<:Period2", ActifFinancier.class);
			query.setParameter("name", name);
			query.setParameter("Period2", Period2);
			query.setParameter("Period1", Period1);
			return query.getResultList();	
	}
	@Override
	public  List<ActifFinancier> GetStockByCompanyDate(String name, Date Date) {
			TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a join a.Company c  where c.Symbol=:name and a.date=:Date", ActifFinancier.class);
			query.setParameter("name", name);
			query.setParameter("Date", Date);
			
			return query.getResultList();	
	}
	@Override
	public void updateStock(ActifFinancier ActifFinancier) {
		ActifFinancier NewActifFi = em.find(ActifFinancier.class, ActifFinancier.getId()); 
		NewActifFi.setClosedPrice(ActifFinancier.getClosedPrice());
		NewActifFi.setHighPrice(ActifFinancier.getHighPrice());
		NewActifFi.setLowPrice(ActifFinancier.getLowPrice());
		NewActifFi.setClosedPrice(ActifFinancier.getClosedPrice());
		
		
	}

	@Override
	public List<ActifFinancier> FindAllstock() {
		System.out.println("In findAllstock2 : ");
		List<ActifFinancier> Actifs = em.createQuery("from ActifFinancier", ActifFinancier.class).getResultList();
		System.out.println("Out of findAllstock2 : "); 
		return Actifs;
	}

	@Override
	public List<ActifFinancier> Top5base() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActifFinancier> Top5hause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActifFinancier> SortAscending(String criteria ) {
		TypedQuery<ActifFinancier>  sortQuery = em.createQuery("select a from ActifFinancier a where a.type=:type ORDER BY a."+criteria+" ASC", ActifFinancier.class);
		sortQuery.setParameter("type", TypeActif.action);
		return sortQuery.getResultList();
	}

	@Override
	public List<ActifFinancier> SortDescending(String criteria ) {
		TypedQuery<ActifFinancier>  sortQuery = em.createQuery("select a from ActifFinancier a where a.type=:type ORDER BY a."+criteria+" DESC", ActifFinancier.class);
		sortQuery.setParameter("type", TypeActif.action);
		return sortQuery.getResultList();
	}

	@Override
	public List<ActifFinancier> findOrderedBySeatNumberLimitedTo(int limit) {
		//basprise for top
	     return em.createQuery("SELECT a FROM ActifFinancier a ORDER BY a.ClosedPrice",
	        		ActifFinancier.class).setMaxResults(limit).getResultList();
	    
	}
	
	/*================================YahooFinance==========================================================================*/
	public  ActifFinancier getStock(String stockName) throws IOException {
		ActifFinancier dto = null;
		Stock stock = YahooFinance.get(stockName);
		
	//	dto = new ActifFinancier(stock.getName(), stock.getQuote().getPrice(), stock.getQuote().getChange(),stock.getCurrency(), stock.getQuote().getBid());
		
	//	dto = new ActifFinancier(stock.getName(), stock.getQuote().getPrice(),stock.getCurrency(), stock.getQuote().getBid());
		//dto = new  ActifFinancier(stock.getName(), stock.getQuote().getPrice(), 0, 0, "action",
			//	0, convertDate(quote.getDate())), Compte compte, Transaction transaction, BigDecimal highPrice,
				//BigDecimal lowPrice, BigDecimal closedPrice, String currency, entities.Company company);
		
		return dto;
		
	}
	@Override
	public Map<String, Stock> getStock(String[] stockNames) throws IOException {
		Map<String, Stock> stock = YahooFinance.get(stockNames);
		System.out.println(stock.toString());
		return stock;
		
	}
	@Override
	public List<ActifFinancier> getHistory(String stockName) throws IOException {

		
		List<ActifFinancier> listactif = new ArrayList<ActifFinancier>();
	
		Stock stock = YahooFinance.get(stockName);
		
		List<HistoricalQuote> history = stock.getHistory();
		for (HistoricalQuote quote : history) {
			ActifFinancier actifFinancier = new ActifFinancier();
			Company cp = new Company();
			cp.setSymbol(stockName);
		   cp.setName(stock.getName());
		   if (!stock.getDividend(true).equals(null)) {
		   cp.setAnnualYield(stock.getDividend(true).getAnnualYield());
		   cp.setAnnualYieldPercent(stock.getDividend(true).getAnnualYieldPercent());}
			if  (stock.getDividend(true).getExDate() !=null)   cp.setExDate(stock.getDividend(true).getExDate().getTime());
			if (stock.getDividend(true).getPayDate()!= null )   cp.setPayDate(stock.getDividend(true).getPayDate().getTime());
			 
		   actifFinancier.setPrix(stock.getQuote(true).getPrice().floatValue());
			//actifFinancier.getCompany(). stock.getDividend(true)
			actifFinancier.setCurrency(stock.getCurrency());
			actifFinancier.setClosedPrice(quote.getClose());
			actifFinancier.setLowPrice(quote.getLow());
			actifFinancier.setHighPrice(quote.getLow());
			actifFinancier.setDate(quote.getDate().getTime());
			actifFinancier.setCompany(cp);
			actifFinancier.setOpenPrice(quote.getOpen());
			actifFinancier.setAdjClose(quote.getAdjClose());
			
			//quote.getVolume();

		//	System.out.println("date : " + convertDate(quote.getDate()));
			listactif.add(actifFinancier);
			
		}
	/*	List<HistoricalDividend> devi = stock.getDividendHistory();
		for (HistoricalDividend quote : devi) {
			System.out.println("==================HistoricalDividend==================");
			System.out.println("symobol : " + quote.getSymbol());
			System.out.println("date : " + convertDate(quote.getDate()));
			System.out.println("getAdjDividend : " + quote.getAdjDividend());
		
			System.out.println("=========================================");
		} */

		return listactif;
	}
	@Override
	public List<ActifFinancier> getHistory(String stockName, int year, String searchType) throws IOException {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, Integer.valueOf("-" + year));

		
		List<ActifFinancier> listactif  = new ArrayList<ActifFinancier>();
		
		Stock stock = YahooFinance.get(stockName);
	
		
		List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
		for (HistoricalQuote quote : history) {
			
			
			ActifFinancier actifFinancier = new ActifFinancier();
			Company cp = new Company();
			cp.setSymbol(quote.getSymbol());
		   cp.setName(stock.getName());
		   if (!stock.getDividend(true).equals(null)) {
		   cp.setAnnualYield(stock.getDividend(true).getAnnualYield());
		  cp.setAnnualYieldPercent(stock.getDividend(true).getAnnualYieldPercent());
		 //  cp.setPayDate(stock.getDividend(true).getPayDate().getTime());
		   }
		if  (stock.getDividend(true).getExDate() !=null)   cp.setExDate(stock.getDividend(true).getExDate().getTime());
		if (stock.getDividend(true).getPayDate()!= null )   cp.setPayDate(stock.getDividend(true).getPayDate().getTime());
		 
		   actifFinancier.setPrix(stock.getQuote(true).getPrice().floatValue());
			//actifFinancier.getCompany(). stock.getDividend(true)
			actifFinancier.setCurrency(stock.getCurrency());
			actifFinancier.setClosedPrice(quote.getClose());
			actifFinancier.setLowPrice(quote.getLow());
			actifFinancier.setHighPrice(quote.getLow());
			actifFinancier.setDate(quote.getDate().getTime());
			actifFinancier.setCompany(cp);
			actifFinancier.setOpenPrice(quote.getOpen());
			actifFinancier.setAdjClose(quote.getAdjClose());
			actifFinancier.setType(TypeActif.action);
			boolean add = listactif.add(actifFinancier);
			System.out.println(add);
		}
/*		List<HistoricalDividend> devi = stock.getDividendHistory();
		for (HistoricalDividend quote : devi) {
			System.out.println("==================HistoricalDividend==================");
			System.out.println("symobol : " + quote.getSymbol());
			System.out.println("date : " + convertDate(quote.getDate()));
			System.out.println("getAdjDividend : " + quote.getAdjDividend());
		
			System.out.println("=========================================");
		}*/
       return listactif;
	}


	private String convertDate(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formatDate = format.format(cal.getTime());
		return formatDate;
	}

	private Interval getInterval(String searchType) {
		Interval interval = null;
		switch (searchType.toUpperCase()) {
		case "MONTHLY":
			interval = Interval.MONTHLY;
			break;
		case "WEEKLY":
			interval = Interval.WEEKLY;
			break;
		case "DAILY":
			interval = Interval.DAILY;
			break;

		}
		return interval;
	}

	@Override
	public double Volatility(String name) {
		double variance=0;
		double ecart_type=0;
		List<ActifFinancier> List=GetStockByCompany(name);
		long Number=List.size();
		double Mean=List.stream().mapToDouble(a->a.getAdjClose().floatValue()).average().getAsDouble();
			
		for(ActifFinancier act : List)
		{
			variance=variance+Math.pow((act.getAdjClose().floatValue()-Mean),2);				
		}
		variance=(variance/(Number-1));
		ecart_type = Math.sqrt(variance); 
		
		return ecart_type;
	}
	@Override
	public double RendementByPeriod(String name,Date d1, Date d2) {
		// TODO Auto-generated method stub
		ActifFinancier A1=GetStockByCompanyDate(name, d1).get(0);
		ActifFinancier A2=GetStockByCompanyDate(name, d2).get(0);
		double diff =( A1.getAdjClose().floatValue()-A2.getAdjClose().floatValue());
		double rendement =diff/ A2.getAdjClose().floatValue();
		
		return rendement;
	}
	@Override
	public double RendementAnnuel(String name, int annee) throws ParseException {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date d1 = dateFormat.parse("30/12/2019");
			Date d2 = dateFormat.parse("30/12/2020");
		List<ActifFinancier> List=GetStockByCompanyPeriode(name, d1, d2);
		double valt1=List.get(0).getAdjClose().floatValue();
		double valt2=List.get(List.size()-1).getAdjClose().floatValue();
		double diff = valt2-valt1;
		double rendement = diff/valt2;
		
		return rendement;
	}
	
	@Override
	public  List<ActifFinancier> GetAction() {
		TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a where a.type=:type", ActifFinancier.class);
		query.setParameter("type", TypeActif.action);		
		return query.getResultList();
	}
	/*==============================Obligation===================================*/
	@Override
	public  List<ActifFinancier> GetObligation() {
		TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a where a.type=:type ORDER BY a.date", ActifFinancier.class);
		query.setParameter("type", TypeActif.obligation);		
		return query.getResultList();
	}
	@Override
	public  List<BigDecimal> CashFlow(ActifFinancier a) {
		List<BigDecimal> Cf = new ArrayList<BigDecimal>();
		BigDecimal c = (a.getParvalue().multiply(a.getTauxcoupon()));
	//	c=c.divide(a.getFréquence());
		Cf.add(a.getBondPrice().negate());
		for(int i=1;i<=a.getDuree();i++) 
		{
			BigDecimal x = null;
			
			if(i<(a.getFréquence()*a.getDuree()))
				{
				 x =c;
			
				}
			else if (i== (a.getFréquence()*a.getDuree())) {
				
				 x =c.add(a.getParvalue());
			}
			
			Cf.add(x);
		}
				
		return Cf;
		
	}
	@Override
	public  List<BigDecimal> PVCashFlow(ActifFinancier a) {
		List<BigDecimal> PVCf = new ArrayList<BigDecimal>();
		List<BigDecimal> Cf = CashFlow(a);
		PVCf.add(BigDecimal.ZERO);
		BigDecimal x= BigDecimal.ZERO;
		for(int i=1;i<=a.getDuree();i++) 
		{
			x = (a.getTauxActuariel().add(BigDecimal.ONE)).pow(i);
			x = Cf.get(i).divide(x,2, RoundingMode.HALF_UP);
			//x = Cf.get(i).divide(x, MathContext.DECIMAL128);
			
			PVCf.add(x);
		}
				
		return PVCf;
		
	}
	@Override
	public  List<BigDecimal> DurationCalcul(ActifFinancier a) {
		List<BigDecimal> DC = new ArrayList<BigDecimal>();
		List<BigDecimal> PVCf = PVCashFlow(a);
		DC.add(BigDecimal.ZERO);
		BigDecimal x= BigDecimal.ZERO;
		BigDecimal sum= BigDecimal.ZERO;
		for(int i=1;i<=a.getDuree();i++) 
		{
			x = PVCf.get(i).multiply(BigDecimal.valueOf(i));	
			
			sum =x.add(sum);
			DC.add(x);
		}
		
		DC.add(sum);
		return DC;
	}
	@Override
	public BigDecimal Duration(ActifFinancier a) {
		//List<Double> DC = DurationCalcul(a);
		BigDecimal sum = DurationCalcul(a).get(DurationCalcul(a).size()-1);
		BigDecimal d = sum.divide(a.getBondPrice(),2, RoundingMode.HALF_UP);
				d.divide(BigDecimal.valueOf(a.getFréquence()),2, RoundingMode.HALF_UP) ;
		
		return d;
	}
	
	@Override
	public BigDecimal Sensibilite(ActifFinancier a) {
		BigDecimal Duration = Duration(a);
		//BigDecimal x = BigDecimal.ZERO;
		BigDecimal x =BigDecimal.ONE.add(a.getTauxActuariel());
		x=x.divide(BigDecimal.valueOf(a.getFréquence()),2, RoundingMode.HALF_UP);
				
		x=Duration.divide(x,2, RoundingMode.HALF_UP);
		return x;
	}
	@Override
	public BigDecimal SommeConvexité(ActifFinancier a) {
		
		List<BigDecimal> PVCashFlow = PVCashFlow(a);
		
		BigDecimal z;
		BigDecimal y =( BigDecimal.ONE.add(a.getTauxActuariel())).divide(BigDecimal.valueOf(a.getFréquence()), 2,RoundingMode.HALF_UP);
		y=y.pow(2);
		y = BigDecimal.ONE.divide(y, 2,RoundingMode.HALF_UP);
		
		BigDecimal x = BigDecimal.ZERO;
		BigDecimal s = BigDecimal.ZERO;
		for(int i=1;i<=a.getDuree();i++) 
		{
			z=BigDecimal.valueOf(i).pow(2);
			z=z.add(BigDecimal.valueOf(i));
			x=y.multiply(PVCashFlow.get(i)).multiply(z);
			s = s.add(x);
		}
		return s;
	}
	@Override
	public List<BigDecimal> ConvexitéCalcul(ActifFinancier a) {
		List<BigDecimal> PVCashFlow = PVCashFlow(a);
		List<BigDecimal> CC =new ArrayList<BigDecimal>();
		CC.add(BigDecimal.ZERO);
		BigDecimal z;
		BigDecimal y =( BigDecimal.ONE.add(a.getTauxActuariel())).divide(BigDecimal.valueOf(a.getFréquence()), 2,RoundingMode.HALF_UP);
		y=y.pow(2);
		y = BigDecimal.ONE.divide(y, 2,RoundingMode.HALF_UP);
		
		BigDecimal x = BigDecimal.ZERO;
	
		for(int i=1;i<=a.getDuree();i++) 
		{
			z=BigDecimal.valueOf(i).pow(2);
					z=z.add(BigDecimal.valueOf(i));
			x=y.multiply(PVCashFlow.get(i));
			x=x.multiply(z);
		
			CC.add(x);
		}
		return CC;
	}

	public BigDecimal Convexité(ActifFinancier a) {
		return SommeConvexité(a).divide(a.getBondPrice(),2 ,RoundingMode.HALF_UP).divide(BigDecimal.valueOf(a.getFréquence()),2, RoundingMode.HALF_UP);
	}
	
	//=====================portfeill==========================
	 


	
	public double Rendementportfeill(List<ActifFinancier> portfeill,int id) {
		double r =0;
		for( ActifFinancier a : portfeill )
			try {
				r= r+(this.RendementAnnuel(a.getCompany().getSymbol(), 1) * this.getQuantite(a.getCompany().getSymbol(),1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		;
		return r;
	}
	
	@Override
	public List<ActifFinancier> getStockByClient(int idp,int ida) {	
		TypedQuery<ActifFinancier> query= em.createQuery("select DISTINCT a from ActifFinancier a  where a.compte.id.idClient=:idp  and a.compte.id.idAgence=:ida", ActifFinancier.class);
		query.setParameter("idp", idp);
		query.setParameter("ida", ida);
		return query.getResultList();
	}
	
	@Override
	public List<ActifFinancier> getStockByClient(int idp) {	
		TypedQuery<ActifFinancier> query= em.createQuery("select  DISTINCT a  from ActifFinancier a join a.compte c join c.proprietaire p  where p.id=:idp GROUP BY a.Company", ActifFinancier.class);
		query.setParameter("idp", idp);
		
		return query.getResultList();
	}
	@Override
	public List<ActifFinancier> getStockByClient2(int idp) {	
		//SELECT * FROM table GROUP BY email HAVING COUNT(*) = 1;
		TypedQuery<ActifFinancier> query= em.createQuery("SELECT a FROM ActifFinancier a join a.compte c join c.proprietaire p  where p.id=:idp  GROUP BY a.Company", ActifFinancier.class);

		//TypedQuery<ActifFinancier> query= em.createQuery("SELECT a FROM ActifFinancier a join a.compte c join c.proprietaire p  where p.id=:idp and a.Company.Symbol in  (Select DISTINCT (c.Symbol) from Company c)", ActifFinancier.class);
		//Query query= em.createQuery("select  DISTINCT AdjClose,ClosedPrice,Currency,HighPrice,LowPrice,OpenPrice,date,entreprise,interet,prix,rendement,risque,type,Company,date_echeane  from ActifFinancier ");
		
		query.setParameter("idp", idp);
		System.out.println(2);
		return query.getResultList();
	
	}
	@Override
	public List<ActifFinancier> getStockBynumeroCompte(String numeroCompte) {	
		TypedQuery<ActifFinancier> query= em.createQuery("select DISTINCT a from ActifFinancier a join a.compte c where c.numeroCompte=:numeroCompte ", ActifFinancier.class);
		query.setParameter("numeroCompte", numeroCompte);
		
		return query.getResultList();
	} 
	@Override
	public long getQuantite(String name,int idp) {
		TypedQuery<Long> query = 
				em.createQuery("select count(a) from ActifFinancier a join a.compte c join c.proprietaire p where a.Company.Symbol=:name and p.id=:idp", Long.class);
		query.setParameter("name", name);
		query.setParameter("idp", idp);
		return query.getSingleResult();
	}
	@Override
	public double getPriceInstantly(String Sym) {
		yahoofinance.Stock stock = null;
		try {
			stock = YahooFinance.get(Sym);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			double price = stock.getQuote(true).getPrice().doubleValue();
			return price;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	 public void pdfToGenerate(int clientId) throws  IOException, ParseException {
		 String nameClient = em.find(Client.class, clientId).getNom() + em.find(Client.class, clientId).getPrenom();
	      
		 //special font sizes
	        Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL, new BaseColor(0, 0, 0)); 
	        Font bf12 = new Font(FontFamily.TIMES_ROMAN, 12); 
		    Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL, new CMYKColor(44, 21, 0, 48));
	        Font redFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new CMYKColor(0, 255, 0, 0));
	        Font yellowFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(0, 0, 255, 0));
		 
	        Document document = new Document();
	     
	        try {
	            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("portfeuil"+nameClient+"‪.pdf"));
	            
	            LocalDateTime date = LocalDateTime.now();
	            document.open();

	            document.add(new Paragraph("TrueDelta", blueFont));
	            document.add(new Paragraph("Nom Prenom : "+nameClient,blueFont));
	            document.add(new Paragraph("Date :"+date));
	           
	           
	            Paragraph paragraph = new Paragraph("La liste des action  : ");
	            	    
	            	    
	            	   //specify column widths
	            	   float[] columnWidths = {1f, 2f, 2f, 2f, 2f, 2f,2f,2f};
	            	   //create PDF table with the given widths
	            	   PdfPTable table = new PdfPTable(columnWidths);
	            	   // set table width a percentage of the page width
	            	   table.setWidthPercentage(90f);
	            	 
	            	   //insert column headings
	            	   insertCell(table, "#", Element.ALIGN_RIGHT, 1, bfBold12);
	            	   insertCell(table, "symbol", Element.ALIGN_LEFT, 1, bfBold12);
	            	   insertCell(table, "Quantite", Element.ALIGN_LEFT, 1, bfBold12);
	            	   insertCell(table, "Montant achat", Element.ALIGN_RIGHT, 1, bfBold12);
	            	   insertCell(table, "Montant actuel", Element.ALIGN_RIGHT, 1, bfBold12);
	            	   insertCell(table, "Rendement Annuel", Element.ALIGN_RIGHT, 1, bfBold12);
	            	   insertCell(table, "volatilité", Element.ALIGN_RIGHT, 1, bfBold12);
	            	   insertCell(table, "gain", Element.ALIGN_RIGHT, 1, bfBold12);

	            	   table.setHeaderRows(1);
	            	 
	            	   //insert an empty row
	            	  // insertCell(table, "", Element.ALIGN_LEFT, 4, bfBold12);
	            	   //create section heading by cell merging
	            	 //  insertCell(table, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
	            	   double orderTotal, total = 0;
	            	    
	            	   //just some random data to fill 
	            	   
	           	
	            	   double gaintt =0;
	            	   double prix =0;
	            	   
	            	   List<ActifFinancier> ls =getStockByClient2(clientId);
	            	   for( ActifFinancier a : ls)
	  				 {
	            		  
	            		   insertCell(table, "#" , Element.ALIGN_RIGHT, 1, bf12);
	            		   insertCell(table, a.getCompany().getSymbol() , Element.ALIGN_RIGHT, 1, bf12);
		            	    insertCell(table, getQuantite(a.getCompany().getSymbol(),clientId)+"", Element.ALIGN_LEFT, 1, bf12);
		            	    insertCell(table, a.getPrix()+"", Element.ALIGN_LEFT, 1, bf12);
		            	    insertCell(table, getPriceInstantly(a.getCompany().getSymbol())+"", Element.ALIGN_RIGHT, 1, bf12);
		            	    insertCell(table, RendementAnnuel(a.getCompany().getSymbol(), 1)+"", Element.ALIGN_RIGHT, 1, bf12);
		            	    double gain=  getPriceInstantly(a.getCompany().getSymbol()) - a.getPrix();
		            	    gaintt =gaintt +gain;
		            	    insertCell(table, Volatility(a.getCompany().getSymbol())+"" , Element.ALIGN_RIGHT, 1, bf12);
		            	    insertCell(table, gain+"" , Element.ALIGN_RIGHT, 1, bf12);
		            	    prix=prix + (getPriceInstantly(a.getCompany().getSymbol())) ;

	  				 } 
	            	     
	            	   //add the PDF table to the paragraph 
	            	   paragraph.add(table);
	            	   // add the paragraph to the document
	            	   document.add(paragraph);
	            	   document.add(new Paragraph("Gain total :" + gaintt ));
	            	   document.add(new Paragraph("Prix portfeuil total :" + prix ));
	            
	            document.close();
	        
	            writer.close();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	            System.out.println("catch1");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            System.out.println("catch2");
	        }
	        System.out.println("msg11");
	    

	}
	private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){
		   
		  //create a new cell with the specified Text and Font
		  PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		  //set the cell alignment
		  cell.setHorizontalAlignment(align);
		  //set the cell column span in case you want to merge two or more cells
		  cell.setColspan(colspan);
		  //in case there is no text and you wan to create an empty row
		  if(text.trim().equalsIgnoreCase("")){
		   cell.setMinimumHeight(10f);
		  }
		  //add the call to the table
		  table.addCell(cell);
		   
		 }
	@Override
	public void openpdf(int clientId) {
		 {
			 String nameClient = em.find(Client.class, clientId).getNom() + em.find(Client.class, clientId).getPrenom();

			 //Path destinationPath = Paths.get("C:\\Product\\eclipse-wildfly-configured\\wildfly-11.0.0.Final-configured\\bin\\" + "portfeuil‪.pdf");
			 File myFile = new File("C:\\Product\\eclipse-wildfly-configured\\wildfly-11.0.0.Final-configured\\bin\\" + "portfeuil"+nameClient+"‪.pdf");
		        try {
		        	 if (myFile.exists()) {
		        		 System.setProperty("java.awt.headless", "false");
		        		 Desktop.getDesktop().open(myFile);
		                } else {
		                    System.out.println("This file is not a valid one");
		                }
		           //Desktop.getDesktop().open(destinationPath.toFile());
		        } catch (IOException e1) {
		         //   JOptionPane.showMessageDialog(
		           //         null,
		             ///       "file openning fails: " + destinationPath.getFileName(),
		                //    "Error",
		                //    JOptionPane.ERROR_MESSAGE

		           // );
			} 
		}
	}

	@Override
	public  List GetPrices(String name) {
		Query query = em.createQuery("select  openPrice ,closedPrice ,highPrice ,lowPrice from ActifFinancier a join a.Company c  where c.Symbol=:name ORDER BY a.date");
		query.setParameter("name", name);		
		return query.getResultList();
	}
	@Override
	public String name(int clientId) {
		
		Client c = em.find(Client.class, clientId);
		return c.getNom();
		// String nameClient = em.find(Client.class, clientId).getNom() + em.find(Client.class, clientId).getPrenom();
	}
	@Override
	public List<ActifFinancier> getobligationScraping() throws ParseException, IOException{
		
		
		 ActifFinancier stock = new ActifFinancier();
		 Company cp = new Company();
		 List<ActifFinancier> list = new ArrayList<ActifFinancier>();
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 
		      final org.jsoup.nodes.Document document = Jsoup.connect("https://www.easybourse.com/obligations/recherche/?fbclid=IwAR0pqMFzvdZhojUCguaIE630NHOrNqWEAEJw_s4MH2rNMQN7jdWGscV3zAY").timeout(7000).get();
		 
		 
		        for (org.jsoup.nodes.Element row : document.select("tr")) {
		        	
		        	String Libellé = row.select("td.first-cell").text();
		        	if (Libellé.equals("")) ;
		        	else {
		        	String Cours = row.select("td.right-space").text();
		        	String Cpcouru= row.select("td.align-center").text().split("\\s") [0];
		        	String Cpbrut = row.select("td.right-space.hidden-xs").text().split("\\s") [0];
		        if ( row.select("td.right-space.hidden-xs").text().split("\\s") [0].length()==1)
		        	 Cpbrut = row.select("td.right-space.hidden-xs").text().split("\\s") [0]+row.select("td.right-space.hidden-xs").text().split("\\s") [1];
		        	String Txactuariel2 ;
		        	if (row.select("td.right-space.hidden-xs").text().split("\\s").length==3)
		        	 {Txactuariel2 = row.select("td.right-space.hidden-xs").text().split("\\s") [2];}
		        	else if (row.select("td.right-space.hidden-xs").text().split("\\s").length==2) 
		        		Txactuariel2 = row.select("td.right-space.hidden-xs").text().split("\\s") [1];
		        	else 	Txactuariel2 = row.select("td.right-space.hidden-xs").text().split("\\s") [0];
		        	String Calcul =  row.select("td.right-space.exs-hide").text();
		        	String Echéance =  row.select("td.align-center.exs-hide").text();
		            cp.setSymbol(Libellé);
		        	stock.setCompany(cp);
					stock.setDateEcheance(dateFormat.parse(Echéance));
			//		stock.setTauxActuariel(new BigDecimal(Txactuariel2));
		        //	stock.setBondPrice(new BigDecimal(Cours));
		    			
		        //  stock.
		        
		      final org.jsoup.nodes.Document document2 = Jsoup.connect("https://www.easybourse.com/obligations/BE0002286558-11/"+Libellé+"").timeout(7000).ignoreHttpErrors(true).get();
		      if(document2.select("tr.row_hilite").select("td").text().length()==0);
			     else {
		      stock.setCurrency(document2.select("tr.row_hilite").select("td").text().split("\\s")[89]);
		        stock.setTauxcoupon (new BigDecimal( document2.select("tr.row_hilite").select("td").text().split("\\s")[79]));	}
		        final org.jsoup.nodes.Document document3 = Jsoup.connect("https://www.easybourse.com/obligations/BE0002648294-11/"+Libellé+"/caracteristique.html").timeout(7000).ignoreHttpErrors(true).get();
		        if (document3.select("tr.row_hilite").select("td").text().length()==0);
		        else {
             stock.setBondPrice(new BigDecimal(document3.select("tr.row_hilite").select("td").text().split("\\s")[11]));
			     stock.setCategorie(document3.select("tr.row_hilite").select("td").text().split("\\s")[6]+" "+document3.select("tr.row_hilite").select("td").text().split("\\s")[7])  ;
		       
			     stock.setDateEmission(dateFormat.parse(document3.select("tr.row_hilite").select("td").text().split("\\s")[15]));
			     stock.setDateprochainCoupon(dateFormat.parse(document3.select("tr.row_hilite").select("td").text().split("\\s")[41]));
			    stock.setDatedernierCoupon(dateFormat.parse(document3.select("tr.row_hilite").select("td").text().split("\\s")[35]));
			     
			   stock.setDuree(stock.getDatedernierCoupon().getYear()-stock.getDateEmission().getYear());
		        }
			     
			   //  System.out.println("Cpcouru :"+ Cpcouru);
		        //   System.out.println(" Cpbrut "+Cpbrut);
		      // System.out.println("Calcul"+Calcul);
		    	       
		        	}
		        	  list.add(stock);
		       }
	
		return list;
	}

@Override
public Map<String, String> Detait(String Libellé) throws ParseException, IOException{
	System.out.println("prochainRemboursement");
	Map<String, String> detail = new HashMap<>();
	
	final org.jsoup.nodes.Document document3 = Jsoup.connect("https://www.easybourse.com/obligations/BE0002648294-11/"+"atenor-sa-2-87-22"+"/caracteristique.html").timeout(7000).ignoreHttpErrors(true).get();
	 // System.out.println(document3.outerHtml());
		System.out.println(document3.select("tr").select("td").text());
		
		detail.put("Nature",document3.select("tr").select("td").text().split("\\s")[12]+" "+document3.select("tr").select("td").text().split("\\s")[13]);
	    detail.put("Encours",document3.select("tr").select("td").text().split("\\s")[31]);// (en millions)
	    detail.put("Nominal",document3.select("tr").select("td").text().split("\\s")[35]);
	    detail.put("prochain coupon",document3.select("tr").select("td").text().split("\\s")[111]);
	    detail.put("Montantbrutderniercoupon",document3.select("tr").select("td").text().split("\\s")[98]);
	    detail.put("PrixEmission",document3.select("tr").select("td").text().split("\\s")[40]);
	    detail.put("Dateprochaincoupon",document3.select("tr").select("td").text().split("\\s")[104]);
	    detail.put("Datederniercoupon",document3.select("tr").select("td").text().split("\\s")[91]);
	    detail.put("prochainremboursement",document3.select("tr").select("td").text().split("\\s")[117]+document3.select("tr").select("td").text().split("\\s")[118]);
	    
	    detail.put("Emetteur",document3.select("tr").select("td").text().split("\\s")[117]);

	    return detail;
	
}
}