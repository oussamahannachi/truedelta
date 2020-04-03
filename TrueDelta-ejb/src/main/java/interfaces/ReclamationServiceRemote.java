package interfaces;

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
	List<Reclamation> GetReclamByState(String State);
	public int NbReclamsByUser(int idUser);
	public List<Reclamation> GetReclamsOrderByDateASC() ;
	public List<Reclamation> GetReclamsOrderByDateDESC();
	public List<Reclamation> SearchComplaint(String motclé);
	

}
