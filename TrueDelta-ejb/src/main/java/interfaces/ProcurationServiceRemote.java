package interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import entities.*;

@Remote
public interface ProcurationServiceRemote {

	public ProcurationPK ajouterProcuration(Procuration proc);
	public void deleteProc(ProcurationPK id);
	public void modifdescrip(ProcurationPK id,String descr);
	public void modifetat(ProcurationPK id,String etat);
	public void modifScore(ProcurationPK id, int score);
	public Procuration findprocById(ProcurationPK pk);
	public List findAllProc();
	public List<Procuration> getProcByEtat(String etat);
	public List<Procuration> getProcOrderByDate();
	public List<Procuration> GetProcByType(String type);
	public int procnbre(int idclient);
	public int procnbrecourtier(int idcourtier);
	public int nbreComptesClient(int idclient);
	public int scoreClient(ProcurationPK pk);
	public int scoreProcuration(ProcurationPK p);
	public void etatProc(ProcurationPK pk);
	public int GainPeriode(Date d ,Date date) throws ParseException;
	public Date addDays(ProcurationPK pk, Integer days);
	public void dateTraitement(ProcurationPK pk);
	public void calculPourcentage(int year);
	public int calculCompteverif(ProcurationPK id);

	
	
}
