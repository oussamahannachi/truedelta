package Beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import javax.faces.context.ExternalContext;

import entities.Agence;
import entities.Client;
import entities.Compte;
import entities.ComptePK;
import entities.Courtier;
import entities.Procuration;
import entities.Utilisateur;
import services.UserService;

@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String confirmpassword;
	private String email;
	private String adresse;
	private String nom;
	private String prenom;
	private String etatcivil;
	private Utilisateur user;
	private boolean loggedin;
	private String role;
	private boolean disponible;
	private Date datenaissance;
	private Agence agence;
	private String nameb;
	private String namea;
	private int bct;
	private String bic;
	private String adress;
	private String mail;
	private String siege;
	private String tell;
	private String pass;
	private String confirmpass;
	private int experience;
	private File cv;
	private String verification;
	private String code;
	private List<Client> clients;
	private int id;
	private Client c;
	private Compte cmp;
	private long idcmp;
	private Procuration proc;
	private List<Procuration> procs;
	private List<Procuration> procsCourtier;
	private List<Procuration> procsClient;
	private List<Compte> comptes;
	private float score;
	private Courtier cr;
	private int idcourtier;
	private int idclient;
	private List<Courtier> courtiers;
	private List<Courtier> orderCourtier;

	private Map<String, Object> sessionMap;

	private String NIVEAU;
	private String PROFIL;
	private String DESCRIPTION;
	private String contrat;
	private String typePortefeuille;
	private String Reaction;
	private String objectif;
	private String partEpargne;
	private String HorizonPlacement;
	private String contracttypeenum;

	private UploadedFile file;
	private String message;

	private Part uploadedFile;

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean lb;

	private String t;
	private ExternalContext externalContext;

	@EJB
	UserService us;

	public UserBean() {}

	public String addclientt() {
		System.out.println(email);
		us.addClient(new Client("client", email, username, nom, prenom, datenaissance, adresse, password,
				confirmpassword, etatcivil));
		// us.send_Email("Votre compte True Delta à été crée avec succées", email,
		// "Création compte");

		return "/pages/login?faces-redirect=true";
	}

	public String addAgence() {
		us.addAgence(new Agence(nameb, bic, bct, namea, adress, mail, siege, tell, pass, confirmpass));
		// us.send_Email("Votre compte True Delta à été crée avec succées", mail,
		// "Création compte");
		return "/pages/loginBanque?faces-redirect=true";
	}

	public String addcourtier() {
		us.addUser(new Courtier("courtier", email, username, nom, prenom, datenaissance, adresse, password,
				confirmpassword, experience, cv));
		// us.send_Email("Votre compte True Delta à été crée avec succées", email,
		// "Création compte");

		return "/pages/login?faces-redirect=true";
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String dummyAction() throws IOException {

		if (!file.getFileName().endsWith(".pdf")) {

			message = "veuillez choisir un cv sous format PDF";
			return "";

		} else {
			String path = "/Users/macbookair⁩/Desktop/Uploads/";
			String name = username + ".pdf";

			InputStream inputStream = file.getInputStream();

			OutputStream outputStream = null;

			File f = new File(path + name);
			outputStream = new FileOutputStream(f);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.close();
			System.out.println(f);

			us.addUser(new Courtier("courtier", email, username, nom, prenom, datenaissance, adresse, password,
					confirmpassword, experience, f));

			return "";
		}
	}

	public void saveFile() {

		try (InputStream input = uploadedFile.getInputStream()) {
			String fileName = username + ".pdf";
			String folder = "/Users/macbookair/Desktop/Uploads";
			File f = new File(folder + fileName);

			Files.copy(input, new File(folder, fileName).toPath());
			us.addUser(new Courtier("courtier", email, username, nom, prenom, datenaissance, adresse, password,
					confirmpassword, experience, f));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String updateCourtier() {
		System.out.println(lb.getUser().getId());
		System.out.println("fff");
		Courtier u = (Courtier) lb.getUser();
		setNom(u.getNom());
		setPrenom(u.getPrenom());
		setEmail(u.getEmail());
		setUsername(u.getUsername());
		setPassword(u.getPassword());
		setConfirmpassword(u.getConfirmPassword());
		setAdresse(u.getAdresse());

		return "/pages/UpdateCourtier?faces-redirect=true";

	}

	public String editUpdateCourtier() {
		System.out.println(lb.getUser().getId());
		System.out.println("fffff");

		Courtier u = (Courtier) lb.getUser();
		Courtier uf = us.findUserById(u.getId());
		uf.setNom(nom);
		uf.setPrenom(prenom);
		uf.setAdresse(adresse);
		uf.setEmail(email);
		uf.setExperience(experience);
		uf.setUsername(username);
		uf.setPassword(password);
		uf.setConfirmPassword(confirmpassword);
		uf.setCv(cv);
		us.updateUser(uf);
		return "/pages/ProfilCourtier?faces-redirect=true";

	}

	public String doRegisterClient() {

		return "/pages/RegisterClient?faces-redirect=true";

	}

	public String doRegisterBanque() {

		return "/pages/RegisterBanque?faces-redirect=true";

	}

	public String doRegisterCourtier() {

		return "/pages/RegisterCourtier?faces-redirect=true";

	}

	public String doRegisterCBC() {

		return "/pages/RegisterCB?faces-redirect=true";

	}

	public void DeleteClient(int sym) {
		us.deleteClient(sym);
	}

	public void UpdateClient(Client C) {
		us.updateClient(C);
	}

	public String DisplayClient(int sym) {

		c = us.findClientById(sym);

		return ("ProfilClient?faces-redirect=true");

	}

	public void DeleteCourtier(int id) {
		us.deleteUser(id);
	}

	public String DisplayCourtier(int id) {

		cr = us.findUserById(id);

		return ("ProfilCourtier?faces-redirect=true");

	}

	public String DisplayProcParCourtier() {
		if (lb.getUser() == null)
			return null;
		procs = us.gelAllProcurationByCourtier(lb.getUser().getId());

		return ("ListProcsCourtier?faces-redirect=true");

	}

	public void VerifUser() throws IOException {
		if (verification.equals(code)) {
			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			externalContext.redirect("RegisterClient.jsf");

		}

		else {
			externalContext = FacesContext.getCurrentInstance().getExternalContext();

			externalContext.redirect("VerificationCustomer.jsf");
			setT("Please verify code !");

		}
	}

	@PostConstruct
	public void init() {

		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();

		setCode(generatedString);

	}

	public void editProfile() throws IOException {

		/*
		 * externalContext = FacesContext.getCurrentInstance().getExternalContext();
		 * 
		 * User u = (User) sessionMap.get("user"); setFirstname(u.getNom());
		 * setLastname(u.getPrenom()); setMail(u.getAdresseMail());
		 * setBirthdate(u.getDate()); setLogin(u.getLogin()); setPass(u.getPassword());
		 * externalContext.redirect("EditProfile.xhtml");
		 */
	}

	public void RateCourtier() throws IOException {
		us.rateCourtier(id, score);
		externalContext.redirect("ListCourtier.jsf");

	}

	public void ProfilInvestisseur() throws IOException {

		if ((NIVEAU.equals("PasdeRisque")) && (typePortefeuille.equals("PortfolioA"))) {
			System.out.println("Investisseur Sécuritaire  ");
			PROFIL = "Investisseur Sécuritaire";
			DESCRIPTION = "Vous êtes donc prêt à valoriser un capital à moyen terme, tout en bénéficiant d’un risque modéré ";
			contrat = "Au regard de votre profil investisseur sécuritaire, nous vous conseillons la Gestion libre";
		}

		else if ((NIVEAU.equals("RisqueLimite")) && (typePortefeuille.equals("PortfolioB"))) {
			System.out.println("Investisseur Prudent  ");
			PROFIL = "Investisseur Prudent";
			DESCRIPTION = "Vous êtes prêt à accepter un rendement modéré de votre investissement en contrepartie d'un faible risque de perte en capital. ";
			contrat = "Au regard de votre profil investisseur prudent, nous vous conseillons la Gestion sous Mandat";
		} else if ((NIVEAU.equals("RisqueMoyen")) && (typePortefeuille.equals("PortfolioC"))) {
			System.out.println("Équilibré  ");
			PROFIL = "Investisseur Équilibré";
			DESCRIPTION = "Vous êtes donc prêt à valoriser un capital à moyen terme, grâce à une diversification par classe d’actifs, tout en bénéficiant d’un risque équilibré.";
			contrat = "Au regard de votre profil investisseur équilibré, nous vous conseillons la Gestion libre";
		} else if ((NIVEAU.equals("RisqueEleve")) && (typePortefeuille.equals("PortfolioD"))) {
			System.out.println("Dynamique ");
			PROFIL = "Investisseur Dynamique";
			DESCRIPTION = "Vous êtes donc prêt à accepter un risque de perte en capital. Vous maîtrisez les produits et instruments financiers,vous permettant d'investir essentiellement sur des supports en unités de comptes.";
			contrat = "Au regard de votre profil investisseur prudent, nous vous conseillons la Gestion sous Mandat";
		}

		externalContext = FacesContext.getCurrentInstance().getExternalContext();

		externalContext.redirect("ResultatProfil.jsf");
	}

	/*--------------------------------------------------LISTS------------------------------------------------*/

	public List<Procuration> getProcsClient() {
		return us.gelAllProcurationByClient(lb.getUser().getId());
	}

	public void setProcsClient(List<Procuration> procsClient) {
		this.procsClient = procsClient;
	}

	public List<Procuration> getProcsCourtier() {

		return us.gelAllProcurationByCourtier(lb.getUser().getId());
	}

	public void setProcsCourtier(List<Procuration> procsCourtier) {
		this.procsCourtier = procsCourtier;
	}

	public List<Client> getClients() {
		clients = us.findAllClients();
		return clients;
	}

	public List<Courtier> getCourtiers() {
		courtiers = us.findAllUsers();

		return courtiers;
	}

	/*
	 * __________________________________________GETTERS AND
	 * SETTERS---------------------------------------
	 */
	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public boolean isLoggedin() {
		return loggedin;
	}

	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEtatcivil() {
		return etatcivil;
	}

	public void setEtatcivil(String etatcivil) {
		this.etatcivil = etatcivil;
	}

	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

	public Date getDatenaissance() {
		return datenaissance;
	}

	public void setDatenaissance(Date datenaissance) {
		this.datenaissance = datenaissance;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public String getNameb() {
		return nameb;
	}

	public void setNameb(String nameb) {
		this.nameb = nameb;
	}

	public String getNamea() {
		return namea;
	}

	public void setNamea(String namea) {
		this.namea = namea;
	}

	public int getBct() {
		return bct;
	}

	public void setBct(int bct) {
		this.bct = bct;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSiege() {
		return siege;
	}

	public void setSiege(String siege) {
		this.siege = siege;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getConfirmpass() {
		return confirmpass;
	}

	public void setConfirmpass(String confirmpass) {
		this.confirmpass = confirmpass;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public File getCv() {
		return cv;
	}

	public void setCv(File cv) {
		this.cv = cv;
	}

	public Agence getAgence() {
		return agence;
	}

	public void setAgence(Agence agence) {
		this.agence = agence;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Client getC() {
		return c;
	}

	public void setC(Client c) {
		this.c = c;
	}

	public Compte getCmp() {

		return us.GetComptebyNum(idcmp);
	}

	public void setCmp(Compte cmp) {
		this.cmp = cmp;
	}

	public Procuration getProc() {
		return proc;
	}

	public void setProc(Procuration proc) {
		this.proc = proc;
	}

	public Courtier getCr() {
		return cr;
	}

	public void setCr(Courtier cr) {
		this.cr = cr;
	}

	public void setCourtiers(List<Courtier> courtiers) {
		this.courtiers = courtiers;
	}

	public LoginBean getLb() {
		return lb;
	}

	public void setLb(LoginBean lb) {
		this.lb = lb;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public String getNIVEAU() {
		return NIVEAU;
	}

	public void setNIVEAU(String nIVEAU) {
		NIVEAU = nIVEAU;
	}

	public String getPROFIL() {
		return PROFIL;
	}

	public void setPROFIL(String pROFIL) {
		PROFIL = pROFIL;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getContrat() {
		return contrat;
	}

	public void setContrat(String contrat) {
		this.contrat = contrat;
	}

	public String getTypePortefeuille() {
		return typePortefeuille;
	}

	public void setTypePortefeuille(String typePortefeuille) {
		this.typePortefeuille = typePortefeuille;
	}

	public String getReaction() {
		return Reaction;
	}

	public void setReaction(String reaction) {
		Reaction = reaction;
	}

	public String getObjectif() {
		return objectif;
	}

	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}

	public String getPartEpargne() {
		return partEpargne;
	}

	public void setPartEpargne(String partEpargne) {
		this.partEpargne = partEpargne;
	}

	public String getHorizonPlacement() {
		return HorizonPlacement;
	}

	public void setHorizonPlacement(String horizonPlacement) {
		HorizonPlacement = horizonPlacement;
	}

	public String getContracttypeenum() {
		return contracttypeenum;
	}

	public void setContracttypeenum(String contracttypeenum) {
		this.contracttypeenum = contracttypeenum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public int getIdcourtier() {
		return idcourtier;
	}

	public void setIdcourtier(int idcourtier) {
		this.idcourtier = idcourtier;
	}

	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

	public long getIdcmp() {
		return idcmp;
	}

	public void setIdcmp(long idcmp) {
		this.idcmp = idcmp;
	}

	/*---------------------------------------------------Navigations---------------------------------------------*/

	public String doVerificationClient() {

		return "/pages/VerificationCustomer?faces-redirect=true";

	}

	public String doAnalyseProfil() {

		return "/pages/QuestionsProfilClient?faces-redirect=true";

	}

	public String doProfil() {

		return "/pages/ProfilClient?faces-redirect=true";

	}

	public String doProfilCourtier() {

		return "/pages/ProfilCourtier?faces-redirect=true";

	}

}
