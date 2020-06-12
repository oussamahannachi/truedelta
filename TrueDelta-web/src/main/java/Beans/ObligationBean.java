package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.ActifFinancier;
import implementation.ActifFinancierService;
import implementation.CompaniesService;
@ManagedBean(name = "obligationBean")
@SessionScoped
public class ObligationBean implements Serializable  {
	
	
	private static final long serialVersionUID = 1L;
	@EJB
	CompaniesService CompaniesService;
	@EJB
	ActifFinancierService ActifFinancierService;
	
	
	
	/***********************obligation***********************************/
	
	
	
	public ActifFinancier findObligation() {
		return ActifFinancierService.GetStockById(46);
	}
	private List<ActifFinancier> obligations ;
	public List<ActifFinancier> getObligationbase() {	
		 List<ActifFinancier> obligation= ActifFinancierService.GetObligation();
		return obligation;
	}
	public BigDecimal duration(ActifFinancier a) {
		BigDecimal d = ActifFinancierService.Duration(a);
		return d;
	}
	
	public List<ActifFinancier> getObligations() {
		System.out.println("tstt");
			try {
				obligations=ActifFinancierService.getobligationScraping();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	//	obligations= ActifFinancierService.GetObligation();
		return obligations;
	}

	public void setObligations(List<ActifFinancier> obligations) {
		this.obligations = obligations;
	}
	private ActifFinancier obligation ;
	
	public ActifFinancier getObligation() {
		return obligation;
	}

	public void setObligation(ActifFinancier obligation) {
		this.obligation = obligation;
	}
	private Map<String, String> detail ;
	
	public Map<String, String> getDetail() {
		 detail = new HashMap<>();
			try {
				detail=ActifFinancierService.Detait("xxx");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return detail;
	}

	public void setDetail(Map<String, String> detail) {
		this.detail = detail;
	}
	public String goObligation(ActifFinancier obligation) {
		this.obligation = obligation;
		
		return "/pages/obligation?faces-redirect=true"; 
		}

	public BigDecimal sensibilite(ActifFinancier a) {
		BigDecimal s = ActifFinancierService.Sensibilite(a);
		return s;
	}
	
	public BigDecimal convexite(ActifFinancier a) {
		BigDecimal s = ActifFinancierService.Convexité(a);
		return s;
	}
	public  List<BigDecimal> cashFlow(ActifFinancier a) {
		List<BigDecimal> Cf = ActifFinancierService.CashFlow(a);
		return Cf;
	}
}
