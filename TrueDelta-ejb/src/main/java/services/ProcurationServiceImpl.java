package services;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Procuration;
import entities.ProcurationPK;
import interfaces.ProcurationServiceLocal;
import interfaces.ProcurationServiceRemote;

@Stateful
public class ProcurationServiceImpl implements ProcurationServiceLocal , ProcurationServiceRemote{

	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;
	@Override
	public ProcurationPK ajouterProcuration(Procuration proc) {
		em.persist(proc);
		return proc.getId();
	}
	
	@Override
	public void deleteProc(ProcurationPK id) {
		em.remove(em.find(Procuration.class,id));
		
	}
	@Override
	public void modifdescrip(ProcurationPK id, String descr) {
		Procuration proc=em.find(Procuration.class, id);
		proc.setDescription(descr);
		
	}

	@Override
	public void modifetat(ProcurationPK id, String etat) {
		Procuration proc=em.find(Procuration.class, id);
		proc.setEtat(etat);
		
	}

	@Override
	public void modifduree(ProcurationPK id, int duree) {
		Procuration proc=em.find(Procuration.class, id);
		proc.setDure(duree);
		
	}

	@Override
	public Procuration findprocById(ProcurationPK pk) {
		Procuration proc=em.find(Procuration.class, pk);
		return proc;
	}

	@Override
	public List findAllProc() {
		List <Procuration> procs=em.createQuery("select p from Procuration p",Procuration.class).getResultList();
		
		return procs;
	}


	
}
