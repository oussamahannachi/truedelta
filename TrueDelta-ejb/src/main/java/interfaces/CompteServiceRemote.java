package interfaces;

import java.util.List;
import javax.ejb.Remote;
import entities.Compte;

@Remote
public interface CompteServiceRemote {

	public long ajouterCompte(Compte c);
	public Compte getCompteByNumero(long num);
	public List<Compte> getAllCompte();
	public void autoriseCompte(long num);
	public void verifierCompte(long num); 
	public void supprimerCompte(long num);
	
}
