package interfaces;

import java.util.List;
import javax.ejb.Remote;

import org.bson.Document;

import entities.Compte;

@Remote
public interface CompteServiceRemote {

	public long ajouterCompte(Compte c);
	public Compte getCompteByNumero(long num);
	public List<Compte> getAllCompte();
	public List<Compte> gelAllCompteByAgence(int id);
	public void autoriseCompte(long num);
	public void verifierCompte(long num); 
	public long modifierCompte(Compte c);
	public void supprimerCompte(long num);
	

	public boolean verifierCollection(String nom);
	public Document createDocByCompte(Compte c);
	public void docByTrueDelta(int id);
	
}
