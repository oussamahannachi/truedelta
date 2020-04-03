package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import entities.Reclamation;
import entities.ReclamationState;
import entities.Utilisateur;
import interfaces.ReclamationServiceLocal;
import interfaces.ReclamationServiceRemote;


@Stateless
public class ReclamationImp implements ReclamationServiceLocal, ReclamationServiceRemote {
	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;
	
	
	@Override
	public int AddReclam(Reclamation rec , int id_user) {
		
	

	            Utilisateur user = em.find(Utilisateur.class, id_user); 
	            if(user!=null)
	            {
	              rec.setUser(user);

	            em.persist(rec);
	            return 1;
	            }
	            else
	            	return 0;}

	    

	        
		

	@Override
	public void deleteReclamById(int RecId) {
		em.remove(em.find(Reclamation.class, RecId));

	}

	@Override
	public void UpdateReclam(Reclamation rec) {
		  {
			  Reclamation c=em.find(Reclamation.class, rec.getId());
			    c.setDescription(rec.getDescription());
			    c.setDateCreation(rec.getDateCreation());
			    c.setSubject(rec.getSubject());
			    c.setState(rec.getState());

			    }

	}

	@Override
	public List<Reclamation> GetAllReclams() {
	    TypedQuery<Reclamation> q = em.createQuery("SELECT c FROM Reclamation c", Reclamation.class);
        return (List<Reclamation>) q.getResultList();
	}

	@Override
	public Reclamation GetReclamById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reclamation> GetReclamByState(String State) {
		ReclamationState cm = ReclamationState.valueOf(State);
		TypedQuery<Reclamation> q = em.createQuery("SELECT R FROM Reclamation R WHERE R.state = :state",
				Reclamation.class);
		q.setParameter("state", cm);
		return (List<Reclamation>) q.getResultList();
	}

	
	@Override
	public List<Reclamation> GetReclamsOrderByDateASC() {

		TypedQuery<Reclamation> q = em.createQuery("SELECT c FROM Reclamation c ORDER BY c.dateCreation ASC",
				Reclamation.class);

		return (List<Reclamation>) q.getResultList();
	}

	@Override
	public List<Reclamation> GetReclamsOrderByDateDESC() {

		TypedQuery<Reclamation> q = em.createQuery("SELECT c FROM Reclamation c ORDER BY c.dateCreation DESC",
				Reclamation.class);

		return (List<Reclamation>) q.getResultList();
	}
	
	
	
	@Override
	public List<Reclamation> SearchComplaint(String motclé) {
		TypedQuery<Reclamation> query = em.createQuery(
				"select c from Reclamation c WHERE c.description LIKE :code or c.subject LIKE :code or c.state LIKE :code ORDER BY c.dateCreation DESC",
				Reclamation.class);
		query.setParameter("code", "%" + motclé + "%");
		return query.getResultList();
	}
	
	
	
	
	@Override
	public int NbReclamsByUser(int idUser) {

		Utilisateur user = em.find(Utilisateur.class, idUser);
		Query q = em.createQuery("SELECT Count(c) FROM Reclamation c WHERE c.user = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}
	

}
