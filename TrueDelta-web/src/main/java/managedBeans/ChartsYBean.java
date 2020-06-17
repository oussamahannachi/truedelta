package managedBeans;

import java.io.Serializable;
import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import implementations.TransactionServiceImp;
@ManagedBean(name="chartsy")
@ViewScoped
public class ChartsYBean {
	private int nbTransValidey;
	private int nbTransNonValidey;
	

	@EJB
	TransactionServiceImp ts;


@PostConstruct
public void init() throws ParseException
{
	nbTransValidey = ts.getNbtransvalideparAnnee();
	nbTransNonValidey = ts.getNbtransNvalideparAnnee();

}


public int getNbTransValidey() {
	return nbTransValidey;
}


public void setNbTransValidey(int nbTransValidey) {
	this.nbTransValidey = nbTransValidey;
}


public int getNbTransNonValidey() {
	return nbTransNonValidey;
}


public void setNbTransNonValidey(int nbTransNonValidey) {
	this.nbTransNonValidey = nbTransNonValidey;
}
}
