package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Procuration;
import entities.ProcurationPK;

@Local
public interface ProcurationServiceLocal {
	
	public ProcurationPK ajouterProcuration(Procuration proc);
	public void deleteProc(ProcurationPK id);
	public void modifdescrip(ProcurationPK id,String descr);
	public void modifetat(ProcurationPK id,String etat);
	public void modifduree(ProcurationPK id,int duree);
	public Procuration findprocById(ProcurationPK pk);
	public List findAllProc();
}
