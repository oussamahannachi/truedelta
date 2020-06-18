package Beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.shaded.json.JSONObject;

import entities.ActifFinancier;
import entities.Company;
import services.*;

@ManagedBean(name="chartData")
@SessionScoped
public class ChartData implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@EJB
	static
	ActifFinancierService ActifService;
	
	@EJB
	CompaniesService CompaniesService ; 
	static List<String> date = new ArrayList<String>();

	static List<BigDecimal> price = new ArrayList<BigDecimal>();
	private List<ActifFinancier> stock = new ArrayList<ActifFinancier>();
	
	private Map<String,Long> stockk ;

	private JSONObject json = new JSONObject();
	
	
	public static List<String> date(String sym) {
		date =new ArrayList<String>();
		System.out.println(sym);
		for (ActifFinancier a : ActifService.GetStockByCompany("FB")) {
			
			date.add("'"+a.getDate()+"'");
		}
		
		return date;
	}

	public List<String> getDate() {
		return date;
	}

	public void setDate(List<String> date) {
		this.date = date;
	}

	public List<BigDecimal> price(String sym) {
		price = new ArrayList<BigDecimal>();
		for (ActifFinancier a : ActifService.GetStockByCompany("FB")) {
			price.add(a.getClosedPrice());
			
		}
		return price;
	}
	public List<BigDecimal> getPrice() {
		return price;
	}

	public void setPrice(List<BigDecimal> price) {
		this.price = price;
	}

	public List<ActifFinancier> getStock() {
		return stock = ActifService.GetStockByCompany("INTC");
	}

	public void setStock(List<ActifFinancier> stock) {
		this.stock = stock;
	}

	public JSONObject getJson() {
//console.log(getRandomData('01 Apr 2017 00:00 Z', 2))
for (ActifFinancier a : ActifService.GetStockByCompany("INTC")) {
			
	json.put("t",a.getDate());
	json.put("o",a.getOpenPrice());
	json.put("h", a.getHighPrice());
	json.put("l", a.getLowPrice());
	json.put("c", a.getClosedPrice());
		}
		
		System.out.println(json);
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	
	
	
	
//	private static List<KeyValue> pieDataList;
	 
//	private static List<Company> usersList; 
	

}


