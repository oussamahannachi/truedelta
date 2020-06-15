package interfaces;

import java.sql.Date;

import java.util.List;

import javax.ejb.Local;

import entities.Reclamation;
import entities.State;



@Local
public interface ReclamationServiceLocal {

	public int AddReclam(Reclamation rec, int id_user);
	
	void UpdateReclam(Reclamation rec);
	List<Reclamation> GetAllReclams();
	public int  deleteComplain(int id_c);
	public int NbReclamByState(String state);
	List<Reclamation> GetReclamByState(String State);
	public int NbReclamsByUser(int idUser);
	public List<Reclamation> GetReclamsOrderByDateASC() ;
	public List<Reclamation> GetReclamsOrderByDateDESC();
	public List<Reclamation> SearchComplaint(String motclé);
	public boolean TreatComplaint(int idcomplaint, String state, String resp);
	public int NbReclamationByperiod(Date d1, Date d2);
    public String verifBadWord(int idRec )  throws InterruptedException;
    public List<Reclamation> GetReclamByclient(int CltID);
    public List<Object[]> NbTrimestreRec();
    public List<Object[]> NbTrimestreOpenedRec(String state);
    //public  String searchWord(int  idRec);

}
