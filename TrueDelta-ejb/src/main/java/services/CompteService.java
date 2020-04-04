package services;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import entities.Compte;
import interfaces.CompteServiceLocal;
import interfaces.CompteServiceRemote;

@Stateful
public class CompteService implements CompteServiceLocal, CompteServiceRemote {

	@PersistenceContext(unitName="truedelta-ejb")
	EntityManager em;
	
	MongoClient con = new MongoClient("localhost",27017);
	MongoDatabase db = con.getDatabase("truedelta");
	MongoCollection<Document> collection = null;
	Document doc = new Document();
	
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
		return em.createQuery("select c from compte c where c.numero= ? ", Compte.class)
				 .setParameter(1, num)
				 .getSingleResult();
	}

	@Override
	public List<Compte> getAllCompte() {
		return em.createQuery("select c from compte c", Compte.class)
				 .getResultList();
	}
	
	@Override
	public List<Compte> gelAllCompteByAgence(int id) {
		return  em.createQuery("select c from compte c where idAgence=? and isVerifie = ?", Compte.class)
							  .setParameter(1, id)
							  .setParameter(2, true)
							  .getResultList();
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

	@Override
	public boolean verifierCollection(String nom) {
		MongoIterable<String> names = db.listCollectionNames();
		MongoCursor<String> cursor = names.cursor(); 
		while(cursor.hasNext()) {
			if (cursor.next().equals(nom))
				return true;
		}
		return false;
	}

	@Override
	public Document createDocByCompte(Compte c) {
		return doc.append("numero",c.getNumero())
				  .append("devise", c.getDevise().toString())
				  .append("solde", c.getSolde())
				  .append("actions", c.getNbAction())
				  .append("obligations", c.getNbObligation())
				  .append("source", "truedelta");
	}

	@Override
	public void docByTrueDelta(int id) {
		List<Compte> comptes = gelAllCompteByAgence(id);
		if(!(comptes.isEmpty()))
		{
			for (Compte compte : comptes) {
				String nom = (compte.getAgence().getBanqueName()+compte.getAgence().getAgenceName()).toLowerCase();
				if(!(verifierCollection(nom))) { 
						db.createCollection(nom);
						db.getCollection(nom).insertOne(createDocByCompte(compte));
				}
				else
					db.getCollection(nom).insertOne(createDocByCompte(compte));
			}
		}
	}
	
}
