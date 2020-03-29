package interfaces;

import java.util.List;

import entities.Company;

public interface CompaniesServiceRemote {

	public String AddCompany(Company Company);
	public Company GetCompanyBySymbol(String Symbol);
	public List<Company> GetAllCompanies();
	public void UpdateCompany(Company Company);
	public void RemoveCompany(int id);
}
