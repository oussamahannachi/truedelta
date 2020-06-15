package services;

import java.sql.Date;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import entities.ReclamStatistics;
import interfaces.IReclamStatisticsLocal;
import interfaces.IReclamStatisticsRemote;



@Stateless
public class ReclamStatisticsImp implements IReclamStatisticsLocal, IReclamStatisticsRemote {
	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;
	@EJB
	ReclamationImp RecImpl;
	
	@Override
	public void AddStatReclam(ReclamStatistics Cs) {
		
		/*javax.persistence.Query q = em.createQuery("SELECT count(c) FROM Reclamation c where c.state='Opened'");
		int nbrO =((Number) q.getSingleResult()).intValue();
		javax.persistence.Query q2 = em.createQuery("SELECT count(c) FROM Reclamation c where c.state='in_progress'");
		int nbrIP =((Number) q2.getSingleResult()).intValue();
		javax.persistence.Query q3 = em.createQuery("SELECT count(c) FROM Reclamation c where c.state='treated'");
		int nbrT =((Number) q3.getSingleResult()).intValue();
		*/
		
		int nbrO= RecImpl.NbReclamByState("Opened");
		int nbrIP=RecImpl.NbReclamByState("in_progress");
		int nbrT = RecImpl.NbReclamByState("treated");
		int nbrTot = RecImpl.GetAllReclams().size();
		
		//javax.persistence.Query q4 = em.createQuery("SELECT count (*) FROM Reclamation  ");
		//int nbrTot =((Number) q4.getSingleResult()).intValue();
		
		Calendar currenttime = Calendar.getInstance();
		Date now = new Date((currenttime.getTime()).getTime());
		Cs.setDateStat(now);
		Cs.setNbReclams(nbrTot);
		Cs.setNbOpenedReclam((nbrO*100)/nbrTot);
		Cs.setNbinprogressReclam((nbrIP*100)/nbrTot);
		
		Cs.setNbTreatedReclam((nbrT*100)/nbrTot);
		em.persist(Cs);
		
	}

	@Override
	public List<ReclamStatistics> GetAllStatReclam() {
		TypedQuery<ReclamStatistics> q = em.createQuery("SELECT c FROM ReclamStatistics c ORDER BY c.DateStat DESC", ReclamStatistics.class);
		return (List<ReclamStatistics>) q.getResultList();
	}

	@Override
	public List<ReclamStatistics> GetStatReclamByDate(Date d) {
		TypedQuery<ReclamStatistics> q = em.createQuery("SELECT c FROM ReclamStatistics c WHERE c.DateStat= :datestat", ReclamStatistics.class);
		q.setParameter("datestat", d);
		return (List<ReclamStatistics>) q.getResultList();
	}


}
