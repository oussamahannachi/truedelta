package Beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.ActifFinancier;
import entities.Company;
import implementation.CompaniesService;

@ManagedBean(name = "companyBean")
@SessionScoped
public class CompanyBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	CompaniesService CompaniesService;
	private int id;
	private String Name;
	private String Symbol;
	private String Sector;
	private String Industry;
	private String Address;
	private String Site;
	private String Description;
	private long FullTimeEmployer;
	private float Revenu;
	private int TelephoneNumber ;
	private BigDecimal AnnualYield;
	private BigDecimal AnnualYieldPercent;
	private Date ExDate;
	private Date PayDate;
	
	
	 //**********************************************************************//
		private List<Company> companies;

		public List<Company> getCompanies() {
			companies= CompaniesService.GetAllCompanies();
			return companies;
		}
		public void setCompanies(List<Company> companies) {
			this.companies = companies;
		}
		
		 //**********************************************************************//
	public Date getPayDate() {
		return PayDate;
	}
	public void setPayDate(Date payDate) {
		PayDate = payDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	public String getSector() {
		return Sector;
	}
	public void setSector(String sector) {
		Sector = sector;
	}
	public String getIndustry() {
		return Industry;
	}
	public void setIndustry(String industry) {
		Industry = industry;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getSite() {
		return Site;
	}
	public void setSite(String site) {
		Site = site;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public long getFullTimeEmployer() {
		return FullTimeEmployer;
	}
	public void setFullTimeEmployer(long fullTimeEmployer) {
		FullTimeEmployer = fullTimeEmployer;
	}
	public float getRevenu() {
		return Revenu;
	}
	public void setRevenu(float revenu) {
		Revenu = revenu;
	}
	public int getTelephoneNumber() {
		return TelephoneNumber;
	}
	public void setTelephoneNumber(int telephoneNumber) {
		TelephoneNumber = telephoneNumber;
	}
	public BigDecimal getAnnualYield() {
		return AnnualYield;
	}
	public void setAnnualYield(BigDecimal annualYield) {
		AnnualYield = annualYield;
	}
	public BigDecimal getAnnualYieldPercent() {
		return AnnualYieldPercent;
	}
	public void setAnnualYieldPercent(BigDecimal annualYieldPercent) {
		AnnualYieldPercent = annualYieldPercent;
	}
	public Date getExDate() {
		return ExDate;
	}
	public void setExDate(Date exDate) {
		ExDate = exDate;
	}
	

}
