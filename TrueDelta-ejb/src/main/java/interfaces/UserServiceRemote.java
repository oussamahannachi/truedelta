package interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;
import javax.mail.MessagingException;

import entities.*;

@Remote
public interface UserServiceRemote {
	
	public int addUser(Courtier user);
	public void deleteUser(int id);
	public void updateUser(Courtier user);
	public Courtier findUserById(int id);
	public List<Courtier> findAllUsers();
	public Compte GetComptebyNum(long numero);
	public void supprimerCompte(long num);
	public void AutoriserCompte(long id);
	public void affecterCompte(int idcourtier,int num );
	public void RejeterCompte(long id);




	public List<Compte> AllComptes();
	public int addClient(Client user);
	public void updateClient(Client user);
	public void deleteClient(int id);
	public Client findClientById(int id);
	public List<Client> findAllClients();
	public List<Procuration> gelAllProcurationByClient(int id);


	public List<Procuration> gelAllProcurationByCourtier(int id);
	public List<Client> getAllClientsByCourtier(int id);
	public List<Procuration> getAllProcs();
	
	public List<Courtier> getOrderCourtier();
	public java.util.List<Utilisateur> findAll();

	public void sendMail(String email);
	
	public Courtier affecteClientCourtier(Procuration proc);

	public float rateCourtier(int id,float scr);
	
	public Map<String,String> ShowStocks()throws IOException;
	
	public void send_Email(String msg,String adress,String subject)throws MessagingException;

	public void validation_Compte(Long num);
	public boolean isValid(String email);
	
	public Utilisateur verifierLogin(String username, String password);
	public int addAgence(Agence user);
	public Agence verifierLoginB(String mail, String password);
}
