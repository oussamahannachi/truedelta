package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Company;
@Remote
public interface CompaniesServiceRemote {

	public int AddCompany(Company Company);
	public Company GetCompanyBySymbol(String Symbol);
	public List<Company> GetAllCompanies();
	public void UpdateCompany(Company Company);
	public void RemoveCompany(int id);
	Company GetCompanyByID(int id);
}
