package beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;

import org.chartistjsf.model.chart.PieChartModel;
import org.primefaces.event.ItemSelectEvent;

import entities.Reclamation;
import services.ReclamationImp;



@ManagedBean
@RequestScoped
public class PieChartBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 @EJB
	    ReclamationImp recImp;
	 
	 private List<Reclamation> reclamations;
	
	private PieChartModel pieChartModel;
    public PieChartBean() {
       
    }
    @PostConstruct
    public void init() {
        pieChartModel = new PieChartModel();
        
        pieChartModel.addLabel("Opened");
        pieChartModel.addLabel("in_progress");
        pieChartModel.addLabel("treated");
       
    
  
            int x=0 ,y=0,z=0;
            
            
       x=recImp.NbReclamByState("Opened");
              y=recImp.NbReclamByState("in_progress");
             z= recImp.NbReclamByState("treated");
             
             pieChartModel.set(x);
             pieChartModel.set(y);
             pieChartModel.set(z);
             
            
             pieChartModel.setShowTooltip(true);
       
    }
    public void pieItemSelect(ItemSelectEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected", "Item Value: "
                + pieChartModel.getData().get(event.getItemIndex()));
        FacesContext.getCurrentInstance().addMessage(event.getComponent().getClientId(), msg);
    }
    public PieChartModel getPieChartModel() {
        return pieChartModel;
    }
    public void setPieChartModel(PieChartModel pieChartModel) {
        this.pieChartModel = pieChartModel;
    }
	public ReclamationImp getRecImp() {
		return recImp;
	}
	public void setRecImp(ReclamationImp recImp) {
		this.recImp = recImp;
	}
	public List<Reclamation> getReclamations() {
		return reclamations;
	}
	public void setReclamations(List<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    
    
    
}