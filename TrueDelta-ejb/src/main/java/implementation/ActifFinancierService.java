package implementation;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.ActifFinancier;
import entities.Company;
import entities.Compte;
import entities.Transaction;
import entities.TypeActif;
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
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
@Stateless
@LocalBean
public class ActifFinancierService implements ActifFinancierServiceRemote {
	@PersistenceContext
	EntityManager em;

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
		TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a join a.Company c  where c.Symbol=:name", ActifFinancier.class);
		query.setParameter("name", name);		
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
			actifFinancier.setDateEcheance(quote.getDate().getTime());
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
			actifFinancier.setDateEcheance(quote.getDate().getTime());
			actifFinancier.setCompany(cp);
			actifFinancier.setOpenPrice(quote.getOpen());
			actifFinancier.setAdjClose(quote.getAdjClose());
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
		double valt2=List.get(List.size()).getAdjClose().floatValue();
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
		TypedQuery<ActifFinancier> query = em.createQuery("select a from ActifFinancier a where a.type=:type", ActifFinancier.class);
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
			System.out.println(x);
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
	 
	@Override
	public List<ActifFinancier> getStockByClient(int idp,int ida) {	
		TypedQuery<ActifFinancier> query= em.createQuery("select DISTINCT a from ActifFinancier a join a.compte c join c.proprietaire p join c.agence g where p.id=:idp  and g.id=:ida", ActifFinancier.class);
		query.setParameter("idp", idp);
		query.setParameter("ida", ida);
		return query.getResultList();
	}
	
	//actifs
	@Override
	public List<ActifFinancier> getStockByClient(int idp) {	
		TypedQuery<ActifFinancier> query= em.createQuery("select DISTINCT a from ActifFinancier a join a.compte c join c.proprietaire p  where p.id=:idp ", ActifFinancier.class);
		query.setParameter("idp", idp);
		
		return query.getResultList();
	}
	@Override
	public List<ActifFinancier> getStockBynumeroCompte(String numeroCompte) {	
		TypedQuery<ActifFinancier> query= em.createQuery("select DISTINCT a from ActifFinancier a join a.compte c where c.numeroCompte=:numeroCompte ", ActifFinancier.class);
		query.setParameter("numeroCompte", numeroCompte);
		
		return query.getResultList();
	}
	
	@Override
	 public void pdfToGenerate(int clientId) throws  IOException {
	       Font blueFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 100, 100, 0));
	        Font redFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new CMYKColor(0, 255, 0, 0));
	        Font yellowFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new CMYKColor(0, 0, 255, 0));
	        Document document = new Document();
	        try {
	            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("../vendor/img/portfolio.pdf"));
	          //  String pdf = getStockByClient(clientId).toString();
	            
	            LocalDateTime date = LocalDateTime.now();
	            document.open();

	            document.add(new Paragraph("TrueDelta Project", blueFont));
	            document.add(new Paragraph("N° : "+clientId+ "(Compte LIBRE INDIVIDUEL)",redFont));
	            document.add(new Paragraph("Votre Conseiller : Mme.Ezzeddine Mouna",redFont));
	            document.add(new Paragraph("Veuillez trouver ci-jointe la situation au"+date+"de votre compte :"
	));

	            document.add(new Paragraph("La liste de vos action est : "));
	           // document.add(new Paragraph(pdf));
	            document.addAuthor("Mouna Ezzeddine");
	            document.addCreationDate();
	            document.addSubject(" Portefeuille - Compte LIBRE INDIVIDUEL : "+clientId);
	            document.getHtmlStyleClass();
	        //Add Image
	           // Image image1 = Image.getInstance("temp.jpg");
	            //Fixed Positioning
	         //   image1.setAbsolutePosition(5f, 300f);
	            //Scale to new height and new width of image
	        //    image1.scaleAbsolute(70, 120);
	            //Add to document
	        //    document.add(image1);


	            document.close();
	            writer.close();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	        System.out.println("msg11");
	    }
	}
