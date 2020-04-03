package implementation;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.ActifFinancier;
import entities.Company;
import entities.Compte;
import entities.Transaction;
import interfaces.ActifFinancierServiceRemote;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.HistoricalDividend;

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
		List<ActifFinancier>  sortQuery = em.createQuery("SELECT a FROM ActifFinancier a ORDER BY a."+criteria+" ASC", ActifFinancier.class).getResultList();
		return sortQuery;
	}

	@Override
	public List<ActifFinancier> SortDescending(String criteria ) {
		List<ActifFinancier>  sortQuery = em.createQuery("SELECT a FROM ActifFinancier a ORDER BY a."+criteria+" DESC", ActifFinancier.class).getResultList();
		return sortQuery;
	}

	@Override
	public List<ActifFinancier> findOrderedBySeatNumberLimitedTo(int limit) {
		//basprise for top
	     return em.createQuery("SELECT a FROM ActifFinancier a ORDER BY a.ClosedPrice",
	        		ActifFinancier.class).setMaxResults(limit).getResultList();
	    
	}
	
	//=======================================================
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
	//	if  (!stock.getDividend(true).getExDate().getTime().equals(null))   cp.setExDate(stock.getDividend(true).getExDate().getTime());
		if (!stock.getDividend(true).getPayDate().getTime().equals(null) )   cp.setPayDate(stock.getDividend(true).getPayDate().getTime());
		
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
		   cp.setPayDate(stock.getDividend(true).getPayDate().getTime());
		   }
	//	if  (!stock.getDividend(true).getExDate().getTime().equals(null))   cp.setExDate(stock.getDividend(true).getExDate().getTime());
	//	if (!stock.getDividend(true).getPayDate().getTime().equals(null) )   cp.setPayDate(stock.getDividend(true).getPayDate().getTime());
		 
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



}
