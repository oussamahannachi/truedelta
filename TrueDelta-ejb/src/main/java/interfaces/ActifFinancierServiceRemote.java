package interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import entities.ActifFinancier;
import yahoofinance.Stock;


@Remote
public interface ActifFinancierServiceRemote {
	
	public int AddStock(ActifFinancier ActifFinancier); 

	public ActifFinancier GetStockById(int StockId);
	public List<ActifFinancier> GetStockByCompany(String name);
	public void updateStock(ActifFinancier ActifFinancier);
	public List<ActifFinancier> FindAllstock();
	public List<ActifFinancier> Top5base();
	public List<ActifFinancier> Top5hause();
	public List<ActifFinancier> SortAscending(String criteria );
	public List<ActifFinancier> SortDescending(String criteria );
	public List<ActifFinancier> findOrderedBySeatNumberLimitedTo(int limit);
	public void affecterActifAcompany(int ActifId, int compid);

	public Map<String, Stock> getStock(String[] stockNames) throws IOException;

	public List<ActifFinancier> getHistory(String stockName) throws IOException;

	public List<ActifFinancier> getHistory(String stockName, int year, String searchType) throws IOException;
	
	double Volatility(String name);
	
	double RendementAnnuel (String name ,int annee) throws ParseException;

	List<ActifFinancier> GetStockByCompanyPeriode(String name, Date Period1, Date Period2);

	List<ActifFinancier> GetStockByCompanyDate(String name, Date Date);

	double RendementByPeriod(String name, Date d1, Date d2);
}
