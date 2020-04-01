package implementation;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.ActifFinancier;
import entities.Company;
import interfaces.ActifFinancierServiceRemote;


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

}
