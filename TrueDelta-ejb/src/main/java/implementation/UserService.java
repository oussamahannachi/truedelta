package implementation;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.ActifFinancier;
import entities.Agence;
import entities.Client;
import entities.TypeActif;
import entities.Utilisateur;

@Stateless
@LocalBean
public class UserService {

	@PersistenceContext(unitName="truedelta-ejb")
	EntityManager em;
	
	public Utilisateur loginUser(String login, String password) {
		Query query = em.createQuery("SELECT e FROM Utilisateur e WHERE e.username=? AND e.password=? ");
		query.setParameter(1, login);
		query.setParameter(2, password);
		Utilisateur user = null;
		try {
			user = (Utilisateur) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
		return user;
	}
	public List<Integer> listclientid() {
		TypedQuery<Integer> query = em.createQuery("SELECT e.id FROM Client e ",Integer.class);
		//query.setParameter("role", "client");
		return query.getResultList();
		
	}
	public List<Utilisateur> listclient() {
		System.out.println("test");
		TypedQuery<Utilisateur> query = em.createQuery("SELECT e FROM Client e",Utilisateur.class);
		System.out.println(query);
		//query.setParameter("role", "client");
		return query.getResultList();
		
	}
	

	public String msg() {return"bnj";}
	
}
