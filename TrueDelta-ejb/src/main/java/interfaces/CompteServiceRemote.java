package interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import org.bson.Document;

import entities.Compte;

@Remote
public interface CompteServiceRemote {

	//CRUD
	public long ajouterCompte(Compte c);
	public Compte getCompteByNumero(long num);
	public List<Compte> getAllCompte();
	public List<Compte> getAllCompteByAgence(int id);
	public List<Compte> getAllCompteByClient(int id); 
	public long modifierCompte(Compte c);
	public void supprimerCompte(long num);
	
	//MongoDB
	public boolean verifierCollection(String nom);
	public Document createDocByCompte(Compte c);
	public Document createDocByExcel(Compte c);
	public void exceltoMongoDB(File f, int idAgence) throws IOException;
	
	//EXCEL Traitement
	public int isValide(File file) throws IOException; //Valider la format du fichier excel
	public int isEmpty(File file) throws IOException;
	public int nbrActions(File file, long num) throws IOException; //nbr d'action
	public int nbrObligations(File file, long num) throws IOException; // nbr d'obligation
	
	//Matching des données
	public void matchData(int idAgence);
	public int Classification(Document excel,Document truedelta);
	
	//Convertisseur Devise
	public Map<String,Float> lastTaux() throws IOException; // Map contient les devises et les taux
	public double convertisseur(String de,String a,double quantite) throws IOException;
	List<Compte> filtrerComptes(String banquename, String devise, int actif);
	List<Compte> getAllCompteByBanque(int id);
	
}
