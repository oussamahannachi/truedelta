package implementation;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Company;
import interfaces.CompaniesServiceRemote;

@Stateless
public class CompaniesService implements CompaniesServiceRemote {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public int AddCompany(Company Company) {
		em.persist(Company);
		return Company.getId();
	}

	@Override
	public Company GetCompanyBySymbol(String Symbol) {
		return em.find(Company.class, Symbol);
	}

	@Override
	public List<Company> GetAllCompanies() {
		List<Company> Companies = em.createQuery("from company", Company.class).getResultList();
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
