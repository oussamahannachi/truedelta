package interfaces;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.itextpdf.text.DocumentException;

import entities.*;

@Remote
public interface ProcurationServiceRemote {

	ProcurationPK ajouterProcuration(Procuration proc);

	void deleteProc(ProcurationPK id);

	void modifdescrip(ProcurationPK id, String descr);

	void modifetat(ProcurationPK id, String etat);

	void modifScore(ProcurationPK id, int score);

	Procuration findprocById(ProcurationPK pk);

	List<Procuration> findAllProc();

	List<Procuration> getProcByEtat(String etat);

	List<Procuration> getProcOrderByDate();

	List<Procuration> GetProcByType(String type);

	Long nbrProc();

	int procnbreType(String type);

	int procnbre(int idclient);

	int procnbrecourtier(int idcourtier);

	int nbreComptesClient(int idclient);

	int scoreClient(ProcurationPK pk);

	int scoreProcuration(ProcurationPK p);

	void etatProc(ProcurationPK pk);

	void GainPeriode(Date d, Date date) throws ParseException;

	Date addDays(ProcurationPK pk, Integer days);

	void dateTraitement(ProcurationPK pk);

	void calculPourcentage(int year);

	String TypeDeContratRentable();

	List<Procuration> ProcCourtiers(int id);

	Long getNbProcurationByDate(String date) throws ParseException;

	void KNN(int k, int nbrAct, int nbrObg, float solde);

	void calculgain(ProcurationPK pk);

	List<Procuration> getProcByClient(int idc);

	int NbCourtier();

	int VerifierExistanceProc(int idc, int idcr);

	List<Procuration> getProcByCourtier(int idc);

	String nomCourtier(int idc);

	void test();

	void modifDateTraitement(ProcurationPK id, Date t);

	void AjouterContrat(int num, Client c) throws MalformedURLException, IOException, DocumentException;

	int CalculerNb(String n);

	List<Courtier> getCouriters();

	double totalgain();

	int NombreProcurationTraiteParCourtier(int id);

}
