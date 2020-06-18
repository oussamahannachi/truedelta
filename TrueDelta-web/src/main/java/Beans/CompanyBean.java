package Beans;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


import javax.faces.context.PartialResponseWriter;
import javax.servlet.http.Part;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import entities.Company;
import services.*;

@ManagedBean(name = "companyBean")
@SessionScoped
public class CompanyBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	CompaniesService CompaniesService;
	private int id;
	private String name;
	private String symbol;
	private String sector;
	private String industry;
	private String address;
	private String site;
	private String description;
	private long fullTimeEmployer;
	private float revenu;
	private int telephoneNumber ;
	private BigDecimal annualYield;
	private BigDecimal annualYieldPercent;
	private Date exDate;
	private Date payDate;
	private String logo;
	
	
	 //**********************************************************************//
	
	
	private List<Company> companiesclient;
	
	public List<Company> getCompaniesclient() {
		companies= CompaniesService.GetAllCompanies();
		return companiesclient;
	}
	public void setCompaniesclient(List<Company> companiesclient) {
		this.companiesclient = companiesclient;
	}


	private List<Company> companies;

		public List<Company> getAllCompanies() {
			companies= CompaniesService.GetAllCompanies();
			return companies;
		}
		public List<Company> getCompanies() {
			//companies= CompaniesService.GetAllCompanies();
			return companies;
		}
		public void setCompanies(List<Company> companies) {
			this.companies = companies;
		}
		
		
		private String compan;
		
		private String num;
		

		
		 public String getNum() {
			 num = "  617594,\r\n" + 
			 		"            181045,\r\n" + 
			 		"            153060,\r\n"  ;
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
	//**********************************************************************//
	
	public String getCompan() {
		
		compan = "'Boston', 'Worcester', 'Springfield', 'Lowell', 'Cambridge', 'New Bedford'";
		
		return compan;
	}
	public void setCompan(String compan) {
		this.compan = compan;
	}

	private Integer companyIdToBeUpdated; 
	
	public void addcompany() { 
		this.saveFile();
		CompaniesService.AddCompany(new  Company( name,  symbol,  sector,  industry,  address,  site,
			 description, fullTimeEmployer, revenu,   telephoneNumber,  annualYield,
		annualYieldPercent,  exDate,  payDate,  logo));	
	}

	public void removeCompany(int id) { CompaniesService.RemoveCompany(id); }
	
	public void displayCompany(Company c) { 
		
		this.setName(c.getName()) ;
		this.setSymbol( c.getSymbol()) ;
		this.setSector( c.getSector()); setIndustry( c.getIndustry());
		this.setAddress( c.getAddress());this.setSite( c.getSite()); this.setDescription( c.getDescription()); 
		this.setFullTimeEmployer( c.getFullTimeEmployer()); 
		this.setRevenu(c.getRevenu()) ;
		this.setTelephoneNumber( c.getTelephoneNumber()) ;
		this.setAnnualYield( c.getAnnualYield()) ;
		this.setAnnualYieldPercent( c.getAnnualYieldPercent()) ;
		this.setExDate( c.getExDate());
		this.setPayDate( c.getPayDate()) ;
		this.setLogo( c.getLogo()) ;
		this.setCompanyIdToBeUpdated( c.getId()); }
	public void reset() { 
		this.setName("") ;
		this.setSymbol("") ;
		this.setSector( "");
		this.setAddress( "");this.setSite( ""); this.setDescription( ""); 
		this.setFullTimeEmployer( 0); 
		this.setRevenu(0) ;
		this.setTelephoneNumber( 0) ;
		this.setAnnualYield(BigDecimal.ZERO) ;
		this.setAnnualYieldPercent( BigDecimal.ZERO) ;
		this.setExDate( new Date());
		this.setPayDate( new Date()) ;
		this.setLogo( "") ;
		this.setCompanyIdToBeUpdated( 0); }
	public void updatecompany() 
	{ 
	CompaniesService.UpdateCompany(new  Company(companyIdToBeUpdated, name,  symbol,  sector,  industry,  address,  site,
			 description, fullTimeEmployer, revenu,   telephoneNumber,  annualYield,
		annualYieldPercent,  exDate,  payDate,  logo)); 
	}
	
	private String search;
	

	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public List<Company> GetCompByAdresse() {
		companies = CompaniesService.GetCompByAdresse(search);
		return companies;
	}
	public Company GetCompByMaxReveuu() {
		return CompaniesService.GetCompByMaxReveuu();
	}
	
	private Part uploadedFile;
	private String folder = "C:\\Works\\workspace-esprit\\truedelta\\TrueDelta-web\\src\\main\\webapp\\vendor\\img\\Company";

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	
	public void saveFile(){
		
		try (InputStream input = uploadedFile.getInputStream()) {
			String fileName = uploadedFile.getSubmittedFileName();
		//	this.logo=fileName;
	        Files.copy(input, new File(folder, fileName).toPath());
	       // Files.newBufferedReader(path)
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getFullTimeEmployer() {
		return fullTimeEmployer;
	}
	public void setFullTimeEmployer(long fullTimeEmployer) {
		this.fullTimeEmployer = fullTimeEmployer;
	}
	public float getRevenu() {
		return revenu;
	}
	public void setRevenu(float revenu) {
		this.revenu = revenu;
	}
	public int getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(int telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public BigDecimal getAnnualYield() {
		return annualYield;
	}
	public void setAnnualYield(BigDecimal annualYield) {
		this.annualYield = annualYield;
	}
	public BigDecimal getAnnualYieldPercent() {
		return annualYieldPercent;
	}
	public void setAnnualYieldPercent(BigDecimal annualYieldPercent) {
		this.annualYieldPercent = annualYieldPercent;
	}
	public Date getExDate() {
		return exDate;
	}
	public void setExDate(Date exDate) {
		this.exDate = exDate;
	}
	public Date getPayDate() {
		return payDate;
	}
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getCompanyIdToBeUpdated() {
		return companyIdToBeUpdated;
	}
	public void setCompanyIdToBeUpdated(Integer companyIdToBeUpdated) {
		this.companyIdToBeUpdated = companyIdToBeUpdated;
	}

	

}
