package interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.ejb.Local;

import org.apache.poi.ss.usermodel.Workbook;
import org.bson.Document;

import entities.Compte;

@Local
public interface CompteServiceLocal {

	//CRUD
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
	
	//EXCEL Traitement
	public int isValide(File file) throws IOException; //Valider la format du fichier excel
	public int isEmpty(File file) throws IOException; //Verifier l'existance des champs null 
	public int nbrActions(File file, long num) throws IOException; //nbr d'action
	public int nbrObligations(File file, long num) throws IOException;
	
}
