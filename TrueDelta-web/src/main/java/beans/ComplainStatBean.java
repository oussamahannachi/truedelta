package Beans;


import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import entities.ReclamStatistics;
import interfaces.IReclamStatisticsRemote;
import services.ReclamationImp;

@ManagedBean(name= "ComplainStatBean")
@SessionScoped
public class ComplainStatBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<ReclamStatistics> complainstats;
	private int NbinprogressComplaint ;
	private int NbOpenedComplaint ;
	private int NbTreatedComplaint;
	private int AllRec;
	private Date De;
	private Date a;
	private int Reslt;
	
	
	
	public int getReslt() {
		return Reslt;
	}

	public void setReslt(int reslt) {
		Reslt = reslt;
	}

	public Date getDe() {
		return De;
	}

	public void setDe(Date de) {
		De = de;
	}

	public Date getA() {
		return a;
	}

	public void setA(Date a) {
		this.a = a;
	}

	public int getAllRec() {
		return AllRec;
	}

	public void setAllRec(int allRec) {
		AllRec = allRec;
	}

	@EJB
	IReclamStatisticsRemote complainStatservice;
	 @EJB
	    ReclamationImp recImp;
	
	public ComplainStatBean() {
		super();
	}

	public ComplainStatBean(IReclamStatisticsRemote complainStatservice, List<ReclamStatistics> complainstats,
			int nbinprogressComplaint, int nbOpenedComplaint, int nbTreatedComplaint) {
		super();
		this.complainStatservice = complainStatservice;
		this.complainstats = complainstats;
		NbinprogressComplaint = nbinprogressComplaint;
		NbOpenedComplaint = nbOpenedComplaint;
		NbTreatedComplaint = nbTreatedComplaint;
		
	}

	public IReclamStatisticsRemote getComplainStatservice() {
		return complainStatservice;
	}

	public void setComplainStatservice(IReclamStatisticsRemote complainStatservice) {
		this.complainStatservice = complainStatservice;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
    public String addstatClaim()
    
	{
    	ReclamStatistics c = new ReclamStatistics();
		complainStatservice.AddStatReclam(c);
	    return "/Stat.xhtml?faces-redirect=true";

		
	}
    
public void GetNbByPeriodClaim()
    
	{
    	
	int r=recImp.NbReclamationByperiod(De, a);
	Reslt=r;
	//this.setReslt(r);
	    //return "/Stat.xhtml?faces-redirect=true";

		
	}
	
	public List<ReclamStatistics> getComplainstats() {
		complainstats =complainStatservice.GetAllStatReclam();
		
		 
		return complainstats;
	}
	public void setComplainstats(List<ReclamStatistics> complainstats) {
		this.complainstats = complainstats;
	}
	public int getNbinprogressComplaint() {
		return NbinprogressComplaint;
	}
	public void setNbinprogressComplaint(int nbinprogressComplaint) {
		NbinprogressComplaint = nbinprogressComplaint;
	}
	public int getNbOpenedComplaint() {
		return NbOpenedComplaint;
	}
	public void setNbOpenedComplaint(int nbOpenedComplaint) {
		NbOpenedComplaint = nbOpenedComplaint;
	}
	public int getNbTreatedComplaint() {
		return NbTreatedComplaint;
	}
	public void setNbTreatedComplaint(int nbTreatedComplaint) {
		NbTreatedComplaint = nbTreatedComplaint;
	}

	public ComplainStatBean(List<ReclamStatistics> complainstats, int nbinprogressComplaint, int nbOpenedComplaint,
			int nbTreatedComplaint, int allRec, Date de, Date a, int reslt, IReclamStatisticsRemote complainStatservice,
			ReclamationImp recImp) {
		super();
		this.complainstats = complainstats;
		NbinprogressComplaint = nbinprogressComplaint;
		NbOpenedComplaint = nbOpenedComplaint;
		NbTreatedComplaint = nbTreatedComplaint;
		AllRec = allRec;
		De = de;
		this.a = a;
		Reslt = reslt;
		this.complainStatservice = complainStatservice;
		this.recImp = recImp;
	}
	
	
	

}

