package interfaces;

import java.util.List;
import javax.ejb.Local;

import org.bson.Document;

import entities.Compte;

@Local
public interface CompteServiceLocal {

	//MySQL
	public long ajouterCompte(Compte c);
	public Compte getCompteByNumero(long num);
	public List<Compte> getAllCompte();
	public List<Compte> gelAllCompteByAgence(int id);
	public void autoriseCompte(long num);
	public void verifierCompte(long num); 
	public long modifierCompte(Compte c);
	public void supprimerCompte(long num);
	
	//MongoDB
	public boolean verifierCollection(String nom);
	public Document createDocByCompte(Compte c);
	public void docByTrueDelta(int id);
	
}
