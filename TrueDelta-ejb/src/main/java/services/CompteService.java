package services;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.Compte;
import interfaces.CompteServiceLocal;
import interfaces.CompteServiceRemote;

@Stateful
public class CompteService implements CompteServiceLocal, CompteServiceRemote {

	@PersistenceContext(unitName="truedelta-ejb")
	EntityManager em;
	
	@Override
	public long ajouterCompte(Compte c) {
		c.setIsAutorise(false);
		c.setIsVerifie(false);
		c.setScore(0);
		c.setGab(0);
		c.setNbAction(0);
		c.setNbObligation(0);
		em.persist(c);
		return c.getNumero();
	}

	@Override
	public Compte getCompteByNumero(long num) {
		Compte c = em.createQuery("select c from compte c where c.numero= ? ", Compte.class)
					 .setParameter(1, num)
					 .getSingleResult();
		return c;
	}

	@Override
	public List<Compte> getAllCompte() {
		List<Compte> list = em.createQuery("select c from compte c", Compte.class)
				 .getResultList();
		return list;
	}

	@Override
	public void autoriseCompte(long num) {
		Compte c = getCompteByNumero(num);
		c.setIsAutorise(true);
	}

	@Override
	public void verifierCompte(long num) {
		Compte c = getCompteByNumero(num);
		c.setIsVerifie(true);
	}

	
	@Override
	public long modifierCompte(Compte c) {
		em.merge(c);
		return (c.getNumero());
	}

	@Override
	public void supprimerCompte(long num) {
		em.createQuery("delete from compte c where c.numero=?")
		.setParameter(1, num)
		.executeUpdate();
	}

}
