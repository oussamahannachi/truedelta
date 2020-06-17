package managedBeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import implementations.TransactionServiceImp;

@ManagedBean(name="charts")
@ViewScoped
public class ChartsBean {
	


		private int nbTransValide;
		private int nbTransNonValide;
		

		@EJB
		TransactionServiceImp ts;


	@PostConstruct
	public void init()
	{
		nbTransValide = ts.getNbtransvalide();
		nbTransNonValide = ts.getNbtransNonvalide();
		
		}


	public int getNbTransValide() {
		return nbTransValide;
	}


	public void setNbTransValide(int nbTransValide) {
		this.nbTransValide = nbTransValide;
	}


	public int getNbTransNonValide() {
		return nbTransNonValide;
	}


	public void setNbTransNonValide(int nbTransNonValide) {
		this.nbTransNonValide = nbTransNonValide;
	}
		



	}

