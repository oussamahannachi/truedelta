package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Company")
public class Company implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="CompanyID")
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
	//private String logo;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="Company")
	private List<ActifFinancier> Stocks;
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
	public Company(int id, String name, String symbol, String sector, String industry, String address, String site,
			String description, long fullTimeEmployer, float revenu, int telephoneNumber) {
		super();
		this.id = id;
		Name = name;
		Symbol = symbol;
		Sector = sector;
		Industry = industry;
		Address = address;
		Site = site;
		Description = description;
		FullTimeEmployer = fullTimeEmployer;
		Revenu = revenu;
		TelephoneNumber = telephoneNumber;
	}
	public Company() {
		super();
	}
	public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	public List<ActifFinancier> getStocks() {
		return Stocks;
	}
	public void setStocks(List<ActifFinancier> stocks) {
		Stocks = stocks;
	}
	

	public void addStockes(ActifFinancier ActifFinancier){
		ActifFinancier.setCompany(this);
		this.Stocks.add(ActifFinancier);
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", Name=" + Name + ", Symbol=" + Symbol + ", Sector=" + Sector + ", Industry="
				+ Industry + ", Address=" + Address + ", Site=" + Site + ", Description=" + Description
				+ ", FullTimeEmployer=" + FullTimeEmployer + ", Revenu=" + Revenu + ", TelephoneNumber="
				+ TelephoneNumber + "]";
	}
	
}
