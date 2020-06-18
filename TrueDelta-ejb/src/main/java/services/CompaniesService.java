package services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
			Company cp = null;
		try{
					
		cp = query.getSingleResult();
				
		}catch (NoResultException nre){
			System.out.println("No result forund for... "+nre);
			//Ignore this because as per your logic this is ok!
			}
		return cp;
	
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
	@Override
	public Float GetMaxReveuu() {
	  TypedQuery<Float> query = em.createQuery("select max(c.Revenu) from Company c",Float.class);
	  return  query.getSingleResult();
	}
	@Override
	public Company GetCompByMaxReveuu() {
	  TypedQuery<Company> query = em.createQuery("select c from Company c where c.Revenu = max (c.Revenu)",Company.class);
	  return  query.getSingleResult(); //à couriger
	}
	@Override
	public List<String> GetAllSector() {
		List<String> Sector = em.createQuery("Select DISTINCT (c.Sector) from Company c", String.class).getResultList();
		return Sector;
		
	}

	@Override
	public List<Company> GetCompByAdresse(String address) {
		
		//utuliser %like%
		TypedQuery<Company> query= em.createQuery("select c from Company c where c.Address like '%"+address+"%'",Company.class) ;
	//	query.setParameter("address", address);
			
		return query.getResultList();
	}

	@Override
	public List<Company> GetCompBy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> GetAllSymbol() {
		List<String> Sector = em.createQuery("Select DISTINCT (c.Symbol) from Company c", String.class).getResultList();
		return Sector;
	}
}
