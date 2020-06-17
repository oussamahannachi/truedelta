package managedBeans;

import java.io.Serializable;
import java.text.ParseException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import implementations.TransactionServiceImp;
@ManagedBean(name="chartsm")
@ViewScoped
public class ChartsMBean  {
	private int nbTransValidem;
	private int nbTransNonValidem;
	

	@EJB
	TransactionServiceImp ts;


@PostConstruct
public void init() throws ParseException
{
	nbTransValidem = ts.getNbtransvalideparMois();
	nbTransNonValidem = ts.getNbtransNvalideparMois();
}


public int getNbTransValidem() {
	return nbTransValidem;
}


public void setNbTransValidem(int nbTransValidem) {
	this.nbTransValidem = nbTransValidem;
}


public int getNbTransNonValidem() {
	return nbTransNonValidem;
}


public void setNbTransNonValidem(int nbTransNonValidem) {
	this.nbTransNonValidem = nbTransNonValidem;
}
}