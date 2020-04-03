package implementation;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import entities.Company;
import interfaces.CompaniesServiceRemote;

@Stateless
@LocalBean
public class CompaniesService implements CompaniesServiceRemote {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public int AddCompany(Company Company) {
		em.persist(Company);
		return Company.getId();
	}

	@Override
	public Company GetCompanyByID(int id) {
		return em.find(Company.class, id);
	}
	@Override
	public Company GetCompanyBySymbol(String Symbol) {
	//	return em.find(Company.class, Symbol);
    TypedQuery<Company> query = em.createQuery("select c from Company c where c.Symbol=:Symbol", Company.class);
		query.setParameter("Symbol", Symbol);		
		return query.getSingleResult();
		/*if (query.getSingleResult() == null) 
			return null;
		else 	return query.getSingleResult();*/
	}

	@Override
	public List<Company> GetAllCompanies() {
		List<Company> Companies = em.createQuery("from Company", Company.class).getResultList();
		return Companies;
	}

	@Override
	public void UpdateCompany(Company Company) {
		Company Companynew = em.find(Company.class, Company.getId()); 
		Companynew.setName(Company.getName());
		Companynew.setSymbol(Company.getSymbol());
		Companynew.setSector(Company.getSector());
		Companynew.setIndustry(Company.getIndustry());
		Companynew.setAddress(Company.getAddress());
		Companynew.setSite(Company.getSite());
		Companynew.setDescription(Company.getDescription());
		Companynew.setFullTimeEmployer(Company.getFullTimeEmployer());
		Companynew.setRevenu(Company.getRevenu());
		Companynew.setTelephoneNumber(Company.getTelephoneNumber());
	
	}

	@Override
	public void RemoveCompany(int id) {
		em.remove(em.find(Company.class, id));		
	}

}
