package managedBeans;

import java.io.Serializable;
import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import implementations.TransactionServiceImp;
@ManagedBean(name="chartsj")
@ViewScoped
public class ChartsJBean  {
	private int nbTransValidej;
	private int nbTransNonValidej;
	

	@EJB
	TransactionServiceImp ts;


@PostConstruct
public void init() throws ParseException
{
	nbTransValidej = ts.getNbtransvalideparJour();
	nbTransNonValidej = ts.getNbtransNvalideparJour();
	
	}





public int getNbTransValidej() {
	return nbTransValidej;
}


public void setNbTransValidej(int nbTransValidej) {
	this.nbTransValidej = nbTransValidej;
}


public int getNbTransNonValidej() {
	return nbTransNonValidej;
}


public void setNbTransNonValidej(int nbTransNonValidej) {
	this.nbTransNonValidej = nbTransNonValidej;
}

}
