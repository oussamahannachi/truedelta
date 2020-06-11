package services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Agence;
import entities.Client;
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
	
	public Agence loginAgence(String login,String password) {
		TypedQuery<Agence> query=em.createQuery("Select c from Agence c where c.agenceName=? AND c.password=? ", Agence.class);
		query.setParameter(1, login);
		query.setParameter(2, password);
		Agence agence= null;
		try {
			agence = query.getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return agence;
	}
	
}