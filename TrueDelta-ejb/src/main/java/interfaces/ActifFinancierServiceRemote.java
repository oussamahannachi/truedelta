package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.ActifFinancier;


@Remote
public interface ActifFinancierServiceRemote {
	
	public int AddStock(ActifFinancier ActifFinancier); 
	public List<ActifFinancier> FindAllstock();
	public ActifFinancier GetStockById(int StockId);
	public ActifFinancier GetStockByCompany(String name);
	public void updateStock(ActifFinancier ActifFinancier);
	public List<ActifFinancier> FindAllstock2();
	public List<ActifFinancier> Top5base();
	public List<ActifFinancier> Top5hause();
	public List<ActifFinancier> SortAscending();
	public List<ActifFinancier> SortDescending();
	List<ActifFinancier> findOrderedBySeatNumberLimitedTo(int limit);
}
