package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.*;

import interfaces.UserServiceRemote;

@Stateless
@LocalBean
public class UserService implements UserServiceRemote {

	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void deleteUser(int id) {
		em.remove(em.find(Courtier.class, id));

	}

	@Override
	public List<Courtier> findAllUsers() {
		TypedQuery<Courtier> users = em.createQuery("SELECT c FROM Courtier c", Courtier.class);

		return (List<Courtier>) users.getResultList();
	}

	public java.util.List<Utilisateur> findAll() {
		java.util.List<Utilisateur> c = em.createQuery("SELECT c FROM  Utilisateur c ", Utilisateur.class)
				.getResultList();
		System.out.println("list Courtiers : ");
		return c;
	}

	@Override
	public void updateUser(Courtier user) {
		Courtier user1 = em.find(Courtier.class, user.getId());
		user1.setEmail(user1.getEmail());
		user1.setAdresse(user1.getAdresse());
		user1.setUsername(user1.getUsername());
		user1.setNom(user1.getNom());
		user1.setPrenom(user1.getPrenom());
		user1.setTelephone(user1.getTelephone());
		user1.setPassword(user1.getPassword());
		user1.setConfirmPassword(user1.getConfirmPassword());
		user1.setCv(user1.getCv());
		user1.setExperience(user1.getExperience());
		user1.setGrade(user1.getGrade());
		user1.setNbPF(user1.getNbPF());
		user1.setProcurations(user1.getProcurations());
		user1.setScore(user1.getScore());

	}

	@Override
	public Courtier findUserById(int id) {
		Courtier user = em.find(Courtier.class, id);
		return user;
	}

	@Override
	public int addUser(Courtier user) {
		em.persist(user);
		return user.getId();
	}

	@Override
	public int addAgence(Agence user) {
		em.persist(user);
		return user.getId();
	}

	@Override
	public void updateClient(Client user) {
		Client client1 = em.find(Client.class, user.getId());
		client1.setAdresse(client1.getAdresse());
		client1.setAge(client1.getAge());
		client1.setComptes(client1.getComptes());
		client1.setConfirmPassword(client1.getConfirmPassword());
		client1.setEmail(client1.getEmail());
		client1.setEtatCivil(client1.getEtatCivil());
		client1.setMetier(client1.getMetier());
		client1.setNom(client1.getNom());
		client1.setPassword(client1.getPassword());
		client1.setPrenom(client1.getPrenom());
		client1.setProcurations(client1.getProcurations());
		client1.setReclamations(client1.getReclamations());
		client1.setSalaire(client1.getSalaire());
		client1.setTelephone(client1.getTelephone());
		client1.setUsername(client1.getUsername());
	}

	@Override
	public void deleteClient(int id) {
		em.remove(em.find(Client.class, id));

	}

	@Override
	public Client findClientById(int id) {
		Client user = em.find(Client.class, id);
		return user;
	}

	@Override
	public Compte GetComptebyNum(long num) {
		try {
			return em.createQuery("select c from compte c where c.numero= ? ", Compte.class).setParameter(1, num)
					.getSingleResult();
		} catch (NoResultException e) {
			System.err.println("Verifier le numero du compte");
		}
		return null;
	}

	@Override
	public void supprimerCompte(long num) {
		Compte C = new Compte();
		C = em.find(Compte.class, num);

		em.remove(C);

	}

	@Override
	public List<Client> findAllClients() {
		TypedQuery<Client> users = em.createQuery("SELECT c FROM Client c", Client.class);

		return (List<Client>) users.getResultList();
	}

	@Override
	public List<Compte> AllComptes() {
		TypedQuery<Compte> comptes = em.createQuery("SELECT c FROM compte c", Compte.class);

		return (List<Compte>) comptes.getResultList();
	}

	@Override
	public int addClient(Client user) {
		em.persist(user);
		return user.getId();
	}

	@Override
	public List<Procuration> gelAllProcurationByCourtier(int id) {
		return em.createQuery("select c from Procuration c where idCourtier=?", Procuration.class).setParameter(1, id)
				.getResultList();
	}

	@Override
	public List<Procuration> gelAllProcurationByClient(int id) {
		return em.createQuery("select c from Procuration c where idClient=?", Procuration.class).setParameter(1, id)
				.getResultList();
	}

	@Override
	public List<Client> getAllClientsByCourtier(int id) {
		return em.createQuery("select c from Client c where idCourtier=?", Client.class).setParameter(1, id)
				.getResultList();
	}

	@Override
	public List<Procuration> getAllProcs() {
		List<Procuration> emp = em.createQuery("Select e from Procuration e ORDER BY e.score DESC ", Procuration.class)
				.getResultList();
		return emp;
	}

	public List<Courtier> getOrderCourtier() {
		List<Courtier> ordercourtier = em
				.createQuery("SELECT c FROM Courtier c WHERE c.disponible=true ORDER BY c.score DESC", Courtier.class)
				.getResultList();
		return ordercourtier;
	}

	// FIN CRUD
	// ------------------------------------------------------------------------------------------------------------------

	// affecte courtier à un client
	@Override
	public Courtier affecteClientCourtier(Procuration proc) {
		TypedQuery<Courtier> query = em
				.createQuery("SELECT c FROM Courtier c WHERE c.score <= ? AND c.disponible= ? ORDER BY c.score DESC",
						Courtier.class)
				.setParameter(1, proc.getScore()).setParameter(2, true);

		Courtier c = query.getSingleResult();

		proc.setCourtier(c);

		return proc.getCourtier();

	}

	// rating d'un courtier

	@Override
	public float rateCourtier(int id, float scr) {
		Courtier c = em.createQuery("select * from courtier c where c.id=?", Courtier.class).setParameter(1, id)
				.getSingleResult();

		c.setScore((c.getScore() + scr) / 2);

		return c.getScore();
	}

	// web srapping : affichage actualuté financiére

	@Override
	public Map<String, String> ShowStocks() throws IOException {
		Document doc = Jsoup.connect("https://www.boursorama.com/bourse/actualites/finances/").get();
		Elements name = doc.getElementsByClass("c-list-news__date");
		Elements value = doc.getElementsByClass("c-list-news__content");
		// Elements time=doc.getElementsByClass("data-table-row-cell hide-on-mobile");

		List<String> names = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		// List<String>times=new ArrayList<String>();

		// List<List<List<String>>> list = new ArrayList<>();
		// List<String> line;

		Map<String, String> x = new HashMap<>();

		for (Element elem : name) {
			names.add(elem.text());
		}
		for (Element elem : value) {
			values.add(elem.text());

		}
		/*
		 * for (Element elem :time) { times.add(elem.text()); }
		 */

		for (int i = 1; i < values.size(); i++) {
			x.put(names.get(i).toString(), values.get(i).toString());

		}
		/*
		 * for(int i=1;i<times.size();i++) { line.add(times.get(i).toString()); }
		 * list.add(line); return list;
		 */

		return x;
	}

	// send mail confirmation

	@Override
	public void send_Email(String msg, String adress, String subject) throws MessagingException {

		final String username = "abir.benismail@esprit.tn";
		final String password = "183JFT0494";
		String host = "smtp.gmail.com";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.from", username);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.setProperty("mail.debug", "true");

		Session session = Session.getInstance(props, null);

		MimeMessage message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, adress);
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setText(msg);

		Transport transport = session.getTransport("smtp");

		transport.connect(username, password);
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

	@Override
	public void sendMail(String email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("abir.benismail@esprit.tn", "183JFT0494");
			}
		});

		try {
			MimeMessage msg = new MimeMessage(session);
			Agence agence = em.createQuery("select u from Agence u where u.email=:email", Agence.class)
					.setParameter("email", email).getSingleResult();
			msg.setFrom(new InternetAddress("abir.benismail@esprit.tn"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			msg.setSubject("Autorisation Compte");
			msg.setSentDate(new Date());
			msg.setText(
					"Un client vient de créer un compte sur notre plateforme . Veuillez comfirmer ce compte et nous appelez sur 71 000 001");
			Transport.send(msg);
		} catch (MessagingException mex) {
			System.out.println("send failed, exception: " + mex);
		}

	}

	public void AutoriserCompte(long id) {
		Compte c = em.createQuery("select c from compte c where c.numero= ? ", Compte.class).setParameter(1, id)
				.getSingleResult();
		c.setIsAutorise(true);
		em.merge(c);
	}

	public void RejeterCompte(long id) {
		Compte c = em.createQuery("select c from compte c where c.numero= ? ", Compte.class).setParameter(1, id)
				.getSingleResult();
		c.setIsAutorise(false);
		em.merge(c);
	}

	public void affecterCompte(int idcourtier, int num) {
		System.out.println(num + "numeroprocuration");
		System.out.println(idcourtier + "idcourtier");

		Procuration c = em.createQuery("select c from Procuration c where c.numero= ? ", Procuration.class)
				.setParameter(1, num).getSingleResult();
		System.out.println(c.getClient().getUsername());
		System.out.println("------");
		Courtier C = em.find(Courtier.class, idcourtier);

		System.out.println(C);

		c.setCourtier(C);
		em.merge(c);
	}

	// Autorisation compte

	@Override
	public void validation_Compte(Long num) {

		Compte c = em.createQuery("select c from compte c where c.numero= ? ", Compte.class).setParameter(1, num)
				.getSingleResult();

		Agence a = c.getAgence();

		String email = a.getEmail();

		try {

			if (c.getIsAutorise() == false) {
				send_Email("le compte ayant ce numero" + c.getNumero() + "vient de créer un compte sur notre plateforme"
						+ " Merci de nous comfirmer l'existance de ce compte", email, "Confirmation du compte");

			}
			c.setIsAutorise(true);
		} catch (MessagingException e) {
			System.out.println("erreur d'envoie du mail");
			e.printStackTrace();
		}
	}

	// Verifier adresse mail
	@Override
	public boolean isValid(String email) {
		Agence a = em.createQuery("select a from Agence a where a.email = ?", Agence.class).setParameter(1, email)
				.getSingleResult();
		if (email != null && email.trim().length() > 0) {
			return email.matches("^[a-zA-Z0-9\\.\\-\\_]+@([a-zA-Z0-9\\-\\_\\.]+\\.)+([a-zA-Z]{2,4})$");

		} else
			System.out.println("Adresse mail incorrecte");

		return false;
	}

	// Login
	@Override
	public Utilisateur verifierLogin(String email, String password) {
		Query query = em.createQuery("select u from Utilisateur u where u.email=:email AND u.password=:password")
				.setParameter("email", email).setParameter("password", password);
		if (!(query.getResultList().isEmpty())) {
			Utilisateur user = (Utilisateur) query.getResultList().get(0);
			System.out.println("authenticating user with id :" + user.getId());
			return user;
		}
		System.out.println("Utilisateur incorrecte");
		return null;
	}

	@Override
	public Agence verifierLoginB(String email, String password) {
		TypedQuery<Agence> query=em.createQuery("Select c from Agence c where agenceName=? AND password=? ", Agence.class);
		query.setParameter(1, email);
		query.setParameter(2, password);
		Agence agence= null;
		try {
			agence = query.getSingleResult();
			System.out.println("Connectééééé");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return agence;
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
	
}
