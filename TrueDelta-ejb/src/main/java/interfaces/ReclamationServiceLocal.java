package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Reclamation;



@Local
public interface ReclamationServiceLocal {
	
	public int AddReclam(Reclamation rec, int id_user);
	public void deleteReclamById(int RecId);
	void UpdateReclam(Reclamation rec);
	List<Reclamation> GetAllReclams();
	Reclamation GetReclamById(int id);
	List<Reclamation> GetReclamByState(String State);




}
