package interfaces;

import java.sql.Date;
import java.util.List;

import javax.ejb.Remote;

import entities.Reclamation;
@Remote
public interface ReclamationServiceRemote {

	
	
	public int AddReclam(Reclamation rec, int id_user);
	public void deleteReclamById(int RecId);
	void UpdateReclam(Reclamation rec);
	List<Reclamation> GetAllReclams();
	Reclamation GetReclamById(int id);
	public int NbReclamByState(String state);
	List<Reclamation> GetReclamByState(String State);
	public int NbReclamsByUser(int idUser);
	public List<Reclamation> GetReclamsOrderByDateASC() ;
	public List<Reclamation> GetReclamsOrderByDateDESC();
	public List<Reclamation> SearchComplaint(String motclé);
    public boolean TreatComplaint(int idcomplaint, String State) ;	
    public int NbReclamationByperiod(Date d1, Date d2);
    public String verifBadWord(int idRec )  throws InterruptedException;

}
