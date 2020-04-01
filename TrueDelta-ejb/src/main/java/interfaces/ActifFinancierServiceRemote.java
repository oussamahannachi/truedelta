package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.ActifFinancier;


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
	List<ActifFinancier> findOrderedBySeatNumberLimitedTo(int limit);
	void affecterActifAcompany(int ActifId, int compid);
}
