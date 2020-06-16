package Beans;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.primefaces.event.data.FilterEvent;

import entities.Reclamation;
import entities.State;
import services.ReclamationImp;


@ManagedBean(name = "ReclamationBean") 
@SessionScoped
public class ReclamationBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	
	private Date dateCreation;
	private String description;

	private String response ;
	private String subject;
	
    private State state;
	
    private Date assignmentDate;
  
    private Date closingDate;
    
    private Reclamation rec;
    private List<Reclamation> reclamations;
    private List<Reclamation> reclamationASC;
    private List<Reclamation> reclamationDSC;
    
    private List<Reclamation> reclamationsA;
    private List<State> states ;
    private String MotCle;
    
    @EJB
    ReclamationImp recImp;
    
    
    public String AjoutRec() {
    	
    	 Reclamation rec = new Reclamation(new Date(),description,subject);
    	recImp.AddReclam(rec,2);
    	
    	 /*try {
    		 recImp.verifBadWord(rec.getId());
	            }
	            catch ( Exception e)
	            {
	             System.out.println("errrrrrrr");
	             e.getMessage();}
    	 
    	 */
    	return "/pages/AffichReclamClt?faces-redirect=true";
    	
    }
    	
    
    public String DoAjoutRec() {
    	 vider();
   	 
   	return "/pages/Add?faces-redirect=true";
   	
   }
    
    
    public void vider() {
        subject = null;

        description = null;
    }
    
    
    
    
    public List<Reclamation> getReclamationsA() {
    	
    	reclamationsA = recImp.GetReclamsOrderByDateDESC();

		return reclamationsA;
	}
    
    public List<Reclamation> getReclamations()
    {
    	reclamations = recImp.GetReclamByclient(2);
    	return reclamations; 
    	
    }
    
    
    
    
    
    public void DoReclamationSR(String m) {
    	
    	reclamationsA =recImp.SearchComplaint(m); 
    	// return "/pages/AffichReclamAdmin?faces-redirect=true";
	}


	


	public void setStates(List<State> states) {
		this.states = states;
	}



	private Integer ReclamIdToBeUpdated;
	
    
    public String displayReclam(Reclamation rec)
    {
    this.setDateCreation(rec.getDateCreation());
    this.setDescription(rec.getDescription());
    this.setState(rec.getState());
    this.setSubject(rec.getSubject());
    
    this.setReclamIdToBeUpdated(rec.getId());
    
    return "DetailsRec?faces-redirect=true";
    }
    
    
    public String updateReclam()
    { recImp.UpdateReclam(new Reclamation(ReclamIdToBeUpdated, description, subject,dateCreation,state)); 
    
    return "/pages/AffichReclamClt?faces-redirect=true";} 
    
    
    
    public String DeleteReclam(int idr)
    {   recImp.deleteComplain(idr);
    
    return "/pages/AffichReclamAdmin?faces-redirect=true";} 
    
    
    public String DeleteReclamclt(int idr)
    {   recImp.deleteComplain(idr);
    
    return "/pages/AffichReclamClt?faces-redirect=true";} 
    
    
    
    
    private String statee="";
    public String goToTreatComplain(Reclamation c)
	{
    	
    	statee = State.in_progress.toString();
	this.setSubject(c.getSubject());
	this.setDescription(c.getDescription());
	c.setState(State.in_progress);
	this.setState(c.getState());
	this.setResponse(c.getResponse());
	this.setDateCreation(c.getDateCreation());
	this.setComplainIdToBeTreated(c.getId());
	recImp.TreatComplaint(complainIdToBeTreated,statee, "---"); 
    return "/pages/TreatReclam.xhtml?faces-redirect=true";

	}
	private Integer complainIdToBeTreated;
    
    
	public String treatReclam()
	
	{ 
		statee=State.treated.toString();
		recImp.TreatComplaint(complainIdToBeTreated,statee, response); 
    return "/pages/AffichReclamAdmin.xhtml?faces-redirect=true";

	}

	
	
	
	    public List<State> getStates() {
	    
	    			
		return states;
	}






    
	
	
	

	public Integer getComplainIdToBeTreated() {
		return complainIdToBeTreated;
	}


	public void setComplainIdToBeTreated(Integer complainIdToBeTreated) {
		this.complainIdToBeTreated = complainIdToBeTreated;
	}


	public String getStatee() {
		return statee;
	}


	public void setStatee(String statee) {
		this.statee = statee;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	public ReclamationImp getRecImp() {
		return recImp;
	}




	


	public void setReclamationsA(List<Reclamation> reclamationsA) {
		this.reclamationsA = reclamationsA;
	}


	public void setRecImp(ReclamationImp recImp) {
		this.recImp = recImp;
	}









	public void setReclamations(List<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}






	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	
    
    

	public ReclamationBean(String description, String subject) {
		super();
		this.description = description;
		this.subject = subject;
	}

	public ReclamationBean(Date dateCreation, String description, String subject) {
		super();
		this.dateCreation = dateCreation;
		this.description = description;
		this.subject = subject;
	}


	public ReclamationBean() {
		super();
	}






	public Integer getReclamIdToBeUpdated() {
		return ReclamIdToBeUpdated;
	}






	public void setReclamIdToBeUpdated(Integer reclamIdToBeUpdated) {
		ReclamIdToBeUpdated = reclamIdToBeUpdated;
	}


	public List<Reclamation> getReclamationASC() {
		reclamationASC=recImp.GetReclamsOrderByDateASC();
		return reclamationASC;
	}


	public void setReclamationASC(List<Reclamation> reclamationASC) {
		this.reclamationASC = reclamationASC;
	}


	public List<Reclamation> getReclamationDSC() {
		return reclamationDSC;
	}


	public void setReclamationDSC(List<Reclamation> reclamationDSC) {
		this.reclamationDSC = reclamationDSC;
	}
	
	
	
   /* public void OUPS (AjaxBehaviorEvent event)
    {
    	reclamationsA=recImp.GetReclamByState(State.treated.toString());
    	//return "/pages/AffichReclamClt?faces-redirect=true";
    }*/
    private String result;

    
    public void listener(ValueChangeEvent event) {
    	
    	//reclamationsA=recImp.GetReclamByState(State.treated.toString());
        System.out.println("listener");
        result = "called by " + event.getComponent().getClass().getName();
    	
    	

    }
    
  

	public Reclamation getRec() {
		return rec;
	}


	public void setRec(Reclamation rec) {
		this.rec = rec;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getMotCle() {
		return MotCle;
	}


	public void setMotCle(String motCle) {
		MotCle = motCle;
	}



    
	

    
    
	

}
