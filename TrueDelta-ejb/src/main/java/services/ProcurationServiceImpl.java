package services;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
//import javax.swing.text.Document;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.ZapfDingbatsList;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import entities.Client;
import entities.Compte;
import entities.Courtier;
import entities.Devise;
import entities.Procuration;
import entities.ProcurationPK;
import entities.Type;
import entities.Utilisateur;
import interfaces.ProcurationServiceRemote;

@Stateless
@LocalBean
public class ProcurationServiceImpl implements ProcurationServiceRemote {

	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;
	
	
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	//////////////////////////CRUD //////////////////////////////////

	
	@Override
	public ProcurationPK ajouterProcuration(Procuration proc) {
		proc.setAvisContrat(null);
		proc.setEtat("non traité");
		proc.setScore(0);
		proc.setGain(0);
		em.persist(proc);
		/*
		Procuration p2=findprocById(proc.getId());
		try {
			int num=p2.getNumero();
			System.out.println("num");
			System.out.println("hhhhhhhhhhh"+p2.getClient());
			AjouterContrat(num);
		} catch (Exception e) {
      System.out.println(e.getMessage()+"errruuuuuuuurrrrrrrrrrrr");
		}
		*/
		return proc.getId();
	}

	@Override
	public void deleteProc(ProcurationPK id) {
		em.remove(em.find(Procuration.class, id));

	}

	@Override
	public void modifdescrip(ProcurationPK id, String descr) {
		Procuration proc = em.find(Procuration.class, id);
		proc.setDescription(descr);

	}

	@Override
	public void modifetat(ProcurationPK id, String etat) {
		Procuration proc = em.find(Procuration.class, id);
		proc.setEtat(etat);

	}

	@Override
	public void modifScore(ProcurationPK id, int score) {
		Procuration proc = em.find(Procuration.class, id);
		proc.setScore(score);
	}

	@Override
	public Procuration findprocById(ProcurationPK pk) {
		Procuration proc = em.find(Procuration.class, pk);
		return proc;
	}

	@Override
	public List<Procuration> findAllProc() {
		List<Procuration> procs = em.createQuery("select p from Procuration p", Procuration.class).getResultList();

		return procs;
	}

	@Override
	public List<Procuration> getProcByEtat(String etat) {
		TypedQuery<Procuration> procs = em.createQuery("select p from Procuration p where p.etat = :etat",
				Procuration.class);
		procs.setParameter("etat", etat);
		return (List<Procuration>) procs.getResultList();
	}

	@Override
	public List<Procuration> getProcOrderByDate() {

		TypedQuery<Procuration> procs = em.createQuery("SELECT p FROM Procuration p ORDER BY p.dateCreation ASC",
				Procuration.class);

		return (List<Procuration>) procs.getResultList();
	}

	@Override
	public List<Procuration> GetProcByType(String type) {
		Type tp = Type.valueOf(type);
		TypedQuery<Procuration> procs = em.createQuery("SELECT p FROM Procuration p WHERE p.type = :type",
				Procuration.class);
		procs.setParameter("type", tp);
		return (List<Procuration>) procs.getResultList();
	}

	////////////////////// nbre de ttes les procurations

	@Override
	public Long nbrProc() {
		TypedQuery<Long> query = em.createQuery("select COUNT (p) from Procuration p", Long.class);

		return query.getSingleResult();

	}

	///////////////////////// nbre de procurations pour chaque type
	@Override
	public int procnbreType(String type) {
		Type tp = Type.valueOf(type);
		javax.persistence.Query q = em.createQuery("SELECT Count(p) FROM Procuration p WHERE p.type = :type");
		q.setParameter("type", tp);
		return ((Number) q.getSingleResult()).intValue();

	}

	///////////////////////// nbre de procurations pour chaque client

	@Override
	public int procnbre(int idclient) {
		Client user = em.find(Client.class, idclient);
		javax.persistence.Query q = em.createQuery("SELECT Count(p) FROM Procuration p WHERE p.client = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}

	////////////////////////////// nbre de procurations pour chaque courtier

	@Override
	public int procnbrecourtier(int idcourtier) {
		Courtier user = em.find(Courtier.class, idcourtier);
		javax.persistence.Query q = em.createQuery("SELECT Count(p) FROM Procuration p WHERE p.courtier = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}

	/////////////////////// nbre de comptes pour chaque client

	@Override
	public int nbreComptesClient(int idclient) {
		Client user = em.find(Client.class, idclient);
		javax.persistence.Query q = em.createQuery("SELECT Count(c) FROM  Compte c WHERE c.proprietaire = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}

	/////////////////// score client
	@Override
	public int scoreClient(ProcurationPK pk) {
		int nbre;
		int nbrecompte;
		int score = 0;
		int idClient;
		idClient = pk.getIdClient();
		Client client = em.find(Client.class, idClient);
		if (client.getSalaire() < 1000) {
			score += 10;
		} else if (client.getSalaire() >= 1000 && client.getSalaire() <= 3000) {
			score += 30;
		} else if (client.getSalaire() > 3000 && client.getSalaire() < 10000) {
			score += 50;
		} else {
			score += 100;
		}

		if (client.getAge() <= 30) {
			score += 10;
		} else if (client.getAge() > 30 && client.getAge() < 55) {
			score += 30;
		} else {
			score += 50;
		}

		if (client.getEtatCivil().equals("marie")) {
			score += 10;
		} else if (client.getEtatCivil().equals("celibataire") || client.getEtatCivil().equals("divorce")) {
			score += 20;
		} else {
			score += 0;
		}

		if (client.getMetier().equals("financier") || client.getMetier().equals("homme d'affaires")) {
			score += 100;
		} else {
			score += 30;
		}

		nbre = procnbre(idClient);

		if (nbre < 10) {
			score += 30;
		} else {
			score += 60;
		}

		nbrecompte = nbreComptesClient(idClient);
		if (nbrecompte <= 2) {
			score += 20;
		} else {
			score += 40;
		}

		List<Compte> l = new ArrayList<>();
		l = client.getComptes();
		for (Compte c : l) {
			if (c.getDevise() == Devise.dollar || c.getDevise() == Devise.euro) {
				score += 30;
			} else {
				score += 10;
			}

		}
		int a = pk.getIdClient();
		Boolean b = true;
		Boolean c = true;
		javax.persistence.Query q = em.createQuery(
				"SELECT Count(c.numeroCompte) FROM Compte c join c.proprietaire cl where cl.id=:a and c.isAutorise=:b and c.isVerifie=:d");
		q.setParameter("a", a);
		q.setParameter("b", b);
		q.setParameter("d", c);
		int e = ((Number) q.getSingleResult()).intValue();
		int e1 = l.size();
		if (e1 == e) {
			score += 20;
		}

		return score;
	}

	/////////////////// score Procuration

	@Override
	public int scoreProcuration(ProcurationPK p) {
		int score = 0;
		score = scoreClient(p);
		Procuration pk = em.find(Procuration.class, p);
		if (pk.getType() == Type.specifie) {
			score += 30;
			
		} else {
			score += 10;
			
		}
		if (pk.getGain() <= 500) {

			score += 10;
			
		} else if (pk.getGain() < 1000 && pk.getGain() > 5000) {

			score += 30;
			
		} else {
			score += 100;
			
		}
		pk.setScore(score);
		return score;

	}
	/////////////////// avis contrat bonne ou mauvaise procuration

	@Override
	public void etatProc(ProcurationPK pk) {

		int score = scoreProcuration(pk);
		Procuration proc = em.find(Procuration.class, pk);

		if (score > 300) {
			proc.setAvisContrat("bonne");
		} else {
			proc.setAvisContrat("mauvaise");
		}

	}

	///////////////////////////// calcul du gain pendant une periode
	@Override
	public void GainPeriode(Date d, Date date) throws ParseException {
		float gain = 0;
		TypedQuery<Procuration> query = em.createQuery(
				"select p from Procuration p WHERE p.dateCreation > :d and p.dateCreation < :date", Procuration.class);
		query.setParameter("d", d);
		query.setParameter("date", date);
		List<Procuration> l = query.getResultList();
		for (Procuration p : l) {
			if (p.getType() == Type.mondat) {
				gain += p.getGain() + 100;
				Procuration proc = em.find(Procuration.class, p.getId());
				proc.setGain(gain);
				
			} else if (p.getType() == Type.specifie) {
				
				gain += p.getGain() + 110;
				Procuration proc = em.find(Procuration.class, p.getId());
				proc.setGain(gain);
			} else {
				gain += p.getGain() + 130;
				Procuration proc = em.find(Procuration.class, p.getId());
				proc.setGain(gain);
			}
		}

		
	}

	/////////// date traitement

	@Override
	public Date addDays(ProcurationPK pk, Integer days) {
		Procuration proc = em.find(Procuration.class, pk);
		Date sdf = proc.getDateCreation();
		Date result;
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(sdf);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		System.out.println(calendar);
		result = calendar.getTime();
		return result;
	}

	@Override
	public void dateTraitement(ProcurationPK pk) {

		int a = 0;
		int idClient = pk.getIdClient();
		Procuration proc = em.find(Procuration.class, pk);
		Client client = em.find(Client.class, idClient);
		List<Compte> l = new ArrayList<>();
		l = client.getComptes();
		int i = l.size();
		for (Compte c : l) {
			if ((c.getIsAutorise()) && (c.getIsVerifie())) {
				a += 1;
			}

		}

		Date date = new Date();

		if (a == i) {
			proc.setEtat("en cours");
			proc.setDateTraitement(date);
		}

	}

	///// calcul du pourcentage de bonne procuration par an

	@Override
	public void calculPourcentage(int year) {
		int i = 0;
		int z = 0;
		List<Procuration> l = new ArrayList<>();
		l = findAllProc();
		for (Procuration proc : l) {
			if ((proc.getDateCreation().getYear() + 1900) == year) {
				i += 1;
				if (proc.getAvisContrat().equals("bonne")) {
					z += 1;
				}
			}

		}

		if (i == 0) {
			System.out.println("Pas de contrats pour cette année");
		} else {
			float prc = (z * 100) / i;

			System.out.println("Le pourcentage des bons contrats pour l'année " + year + " est " + prc + " %");
		}

	}

	///////////////////// Le type de contrat le plus rentable

	@Override
	public String TypeDeContratRentable() {
	String response="";
	int e =0;
	int e1 =0;
	int e2 =0;
        try {
        	javax.persistence.Query q = em.createQuery("SELECT sum(p.gain) FROM Procuration p WHERE p.type = :type");
    		q.setParameter("type", Type.mondat);
    		e = ((Number) q.getSingleResult()).intValue();
        	
        }catch (Exception ex)
        {
        	e=0;
        }
        try {
        	javax.persistence.Query q1 = em.createQuery("SELECT sum(p.gain) FROM Procuration p WHERE p.type = :type");
    		q1.setParameter("type", Type.proposition);
    		 e1 = ((Number) q1.getSingleResult()).intValue();
        	
        }catch (Exception ex)
        {
        	e1=0;
        }
        try {
        	javax.persistence.Query q2 = em.createQuery("SELECT sum(p.gain) FROM Procuration p WHERE p.type = :type");
    		q2.setParameter("type", Type.specifie);
    		 e2 = ((Number) q2.getSingleResult()).intValue();
        	
        }catch (Exception ex)
        {
        	e2=0;
        }
		
		if (e >= e1 && e >= e2) {
			response="MONDAT";
		} else if (e1 >= e2 && e1 >= e) {
			response="PROPOSITION";
		} else {
			response="SPECIFIE";
		}
		return response;

	}

	/////////////////////// Les contrats de chaque courtier

	@Override
	public List<Procuration> ProcCourtiers(int id) {

		TypedQuery<Procuration> procs = em.createQuery(
				"SELECT p FROM Procuration p join p.courtier c where c.id=:id ORDER BY p.dateTraitement ASC",
				Procuration.class);
		procs.setParameter("id", id);
		return (List<Procuration>) procs.getResultList();
	}

	//////////// nbre de procurations crées par jour

	@Override
	public Long getNbProcurationByDate(String date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date datec = dateFormat.parse(date);
		TypedQuery<Long> query = em.createQuery(
				"select COUNT (p) from Procuration p where DATEDIFF (DATE_FORMAT(p.dateCreation,\'%d%m%y\'),DATE_FORMAT(:datec,\'%d%m%y\'))=0",
				Long.class);
		query.setParameter("datec", datec);
		return query.getSingleResult();

	}

	///////////// KNN algorithm

	@Override
	public void KNN(int k, int nbrAct, int nbrObg, float solde) {

		TypedQuery<Utilisateur> procs = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
		List<Utilisateur> l = procs.getResultList();
		List l1 = new ArrayList<>();
		for (Utilisateur u : l) {
			if (u instanceof Client) {
				l1.add(u.getId());
			}
		}
		int size = l1.size();
		List l2 = new ArrayList<>();
		List l3 = new ArrayList<>();
		List l4 = new ArrayList<>();
		List l5 = new ArrayList<>();
		for (int i = 0; i < size; i++) {

			javax.persistence.Query q = em
					.createQuery("SELECT sum(c.nbAction) FROM Compte c join c.proprietaire cl where cl.id=:x");
			q.setParameter("x", l1.get(i));
			int e = ((Number) q.getSingleResult()).intValue();
			l2.add(e);

			javax.persistence.Query q1 = em
					.createQuery("SELECT sum(c.nbObligation) FROM Compte c join c.proprietaire cl where cl.id=:y");
			q1.setParameter("y", l1.get(i));
			int e1 = ((Number) q1.getSingleResult()).intValue();
			l3.add(e1);

			javax.persistence.Query q2 = em
					.createQuery("SELECT sum(c.solde) FROM Compte c join c.proprietaire cl where cl.id=:z");
			q2.setParameter("z", l1.get(i));
			int e2 = ((Number) q2.getSingleResult()).intValue();
			l4.add(e2);

		}

		// calcul distance euclidienne
		List l6 = new ArrayList<>();
		List l7 = new ArrayList<>();

		double sum = 0;
		for (int j = 0; j < size; j++) {
			int a = (int) l2.get(j);
			int b = (int) l3.get(j);
			int c = (int) l4.get(j);
			sum = Math.pow(nbrAct - a, 2);
			sum += Math.pow(nbrObg - b, 2);
			sum += Math.pow(solde - c, 2);
			l5.add(Math.sqrt(sum));
			l6.add(Math.sqrt(sum));
			sum = 0;

		}

		if (k > 0 && k <= 2) {

			Collections.sort(l5);
			// l5.stream().limit(k);
			for (int s = 0; s < k; s++) {
				for (int h = 0; h < size; h++) {
					if (l5.get(s).equals(l6.get(h))) {
						l7.add(l1.get(h));

					}
				}
			}
			System.out.println(l7);

		} else {

			System.out.println("resaisir k");
		}

	}

	//////////////////////////////////////
	@Override
	public void calculgain(ProcurationPK pk) {
		float gain = 0;
		double gainPortefeuille = 10; // résultat na54ou men samar
		Procuration proc = em.find(Procuration.class, pk);
		if (proc.getType() == Type.mondat) {
			gain = (float) (gainPortefeuille * 0.1);

		} else if (proc.getType() == Type.specifie) {
			gain = (float) (gainPortefeuille * 0.12);
		} else {
			gain = (float) (gainPortefeuille * 0.15);
		}

		proc.setGain(gain);

	}

	@Override
	public List<Procuration> getProcByClient(int idc) {
		TypedQuery<Procuration> procs = em.createQuery("SELECT p FROM Procuration p join p.client c where c.id=:idc",
				Procuration.class);
		procs.setParameter("idc", idc);
		return (List<Procuration>) procs.getResultList();
	}

	@Override
	public int NbCourtier() {
		List<Courtier> l = em.createQuery("select p from Courtier p", Courtier.class).getResultList();

		return l.size();
	}

	@Override
	public int VerifierExistanceProc(int idc, int idcr) {
		List<Procuration> l = findAllProc();
		int retour = 0;
		for (Procuration a : l) {
			int idc1 = a.getId().getIdClient();
			int idcr1 = a.getId().getIdCourtier();
			if ((idc == idc1) && (idcr == idcr1)) {
				retour = 1;
			} else {
				retour = 0;
			}
		}
		return retour;
	}

	@Override
	public List<Procuration> getProcByCourtier(int idc) {
		TypedQuery<Procuration> procs = em.createQuery("SELECT p FROM Procuration p join p.courtier c where c.id=:idc",
				Procuration.class);
		procs.setParameter("idc", idc);
		return (List<Procuration>) procs.getResultList();
	}

	@Override
	public String nomCourtier(int idc) {
		List<Courtier> court = em.createQuery("select p from Courtier p", Courtier.class).getResultList();
		String nomp = "";
		for (Courtier a : court) {
			if (a.getId() == idc) {
				nomp = a.getNom() + " " + a.getPrenom();
			}

		}
		return nomp;
	}

	@Schedule(minute = "*/1", hour = "*", persistent = false)
	@Override
	public void test() {
		System.out.println("Modifier Etat Proc");
		List<Procuration> l = findAllProc();
		for (Procuration a : l) {
			if (a.getEtat().equals("non traité")) {
				for (Compte c : a.getClient().getComptes()) {
					if (c.getIsVerifie().booleanValue() == false) {
						modifetat(a.getId(), "bloqué");
						System.out.println("Result :" + a.getEtat());
					}
				}
			}
		}

	}

	@Override
	public void modifDateTraitement(ProcurationPK id, Date t) {
		Procuration proc = em.find(Procuration.class, id);
		proc.setDateTraitement(t);

	}

	@Override
	public void AjouterContrat(int num,Client c) throws MalformedURLException, IOException, DocumentException {
		Procuration p=em.createQuery("select p from Procuration p where numero=?", Procuration.class)
				.setParameter(1, num)
				.getSingleResult();
		DateTimeFormatter date = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter full = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		Document document = new Document();
		//String username = p.getClient().getNom() + " " + p.getClient().getPrenom();
		String username =c.getUsername();
		PdfWriter.getInstance(document, new FileOutputStream("C:\\Work\\workspace-eclipse\\PIDEV\\truedelta"
											+ "\\TrueDelta-ejb\\src\\main\\java\\Files\\"+ username + p.getNumero() + ".pdf"));
		//PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Admin/Desktop/hhh.pdf"));
		document.open();
		
		com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 44, BaseColor.WHITE);
		com.itextpdf.text.Font font1 = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
		com.itextpdf.text.Font font2 = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 22, BaseColor.BLACK);
		com.itextpdf.text.Font font3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.RED);

		Chunk chunk2 = new Chunk("TrueDelta");
		chunk2.setLineHeight(60f);
		Paragraph truedelta = new Paragraph(chunk2);
		truedelta.setAlignment(Element.ALIGN_RIGHT);
		truedelta.setSpacingAfter(8f);

		LineSeparator line = new LineSeparator();
		line.setOffset(-2f);
		line.setLineWidth(2f);

		Chunk chunk = new Chunk("CONTRAT", (com.itextpdf.text.Font) font);
		chunk.setBackground(BaseColor.PINK, 2, 1, 4, 6);
		chunk.setCharacterSpacing(1);
		chunk.setLineHeight(70f);
		Paragraph title = new Paragraph(chunk);
		title.setAlignment(Element.ALIGN_CENTER);

		Chunk chunk3 = new Chunk("1. Les détails du contrat :", font2);
		chunk3.setLineHeight(95f);
		chunk3.setWordSpacing(0.6f);
		Paragraph details = new Paragraph(chunk3);
		details.setAlignment(Element.ALIGN_LEFT);
		details.setSpacingAfter(10f);

		Chunk benifchunk = new Chunk("Bénéficiaire : " + username + "", font1);
		Paragraph benificiaire = new Paragraph(benifchunk);
		benificiaire.setSpacingAfter(4f);
		benificiaire.setIndentationLeft(40f);

		Chunk typechunk = new Chunk("Type de procuration : " + p.getType() + "", font1);
		Paragraph type = new Paragraph(typechunk);
		type.setSpacingAfter(4f);
		type.setIndentationLeft(40f);

		Chunk descchunk = new Chunk("Description : " + p.getDescription() + "", font1);
		Paragraph desc = new Paragraph(descchunk);
		desc.setSpacingAfter(4f);
		desc.setIndentationLeft(40f);

		Chunk datechunk = new Chunk("Date du contrat : " + full.format(now), font1);
		Paragraph datep = new Paragraph(datechunk);
		datep.setSpacingAfter(4f);
		datep.setIndentationLeft(40f);

		Chunk chunk4 = new Chunk("2. Les régles à respecter :", font2);
		chunk4.setLineHeight(65f);
		chunk4.setWordSpacing(0.6f);
		Paragraph regles = new Paragraph(chunk4);
		regles.setSpacingAfter(12f);
		regles.setAlignment(Element.ALIGN_LEFT);

		ZapfDingbatsList list = new ZapfDingbatsList(45, 25);
		list.setIndentationLeft(40);
		list.add("Notre système affectera un courtier pour traiter votre procuration.");
		list.add(
				"Vous ne pouvez ni changer le courtier ni modifier votre procuration après 24 heures de la date de la création.");
		list.add(
				"Toute non-conformité des données de portefeuille entraine automatiquement le blockage de la procuration.");
		list.add("Tout au long le traitement de la procuration vous ne pouvez pas créer d'autre procuration.");

		LineSeparator line2 = new LineSeparator();
		line.setOffset(-2f);
		line.setLineWidth(3f);

		Chunk chunk5 = new Chunk(
				"NB : Si vous voulez modifier votre porcuration , vous pouvez le faire dans 24 heurs avant l'affectation d'un courtier",
				font3);
		Paragraph nb = new Paragraph(chunk5);
		nb.setAlignment(Element.ALIGN_BOTTOM);
		nb.setSpacingBefore(70f);
		nb.setSpacingAfter(6f);

		Chunk chunk6 = new Chunk("Tunisie, le " + date.format(now) + " à " + time.format(now));
		Paragraph footer = new Paragraph(chunk6);
		footer.setAlignment(Element.ALIGN_RIGHT);
		footer.setIndentationRight(22f);
		footer.setSpacingBefore(10f);
		footer.setSpacingAfter(10f);

		String url = "C:\\Work\\workspace-eclipse\\PIDEV\\truedelta\\TrueDelta-ejb\\src\\main\\java\\Files\\Tempon.png";
		Image img = Image.getInstance(url);
		img.setAlignment(Element.ALIGN_RIGHT);

		document.add(truedelta);
		document.add(line);
		document.add(title);
		document.add(details);
		document.add(benificiaire);
		document.add(type);
		document.add(desc);
		document.add(datep);
		document.add(regles);
		document.add(list);
		document.add(nb);
		document.add(line2);
		document.add(footer);
		document.add(img);
		document.addTitle("Contrat");
		document.close();

	}

	@Override
	public int CalculerNb(String n) {
		int somme=0;
		List<Procuration> liste=findAllProc();
		for(Procuration a : liste)
		{
			if(a.getEtat().equals(n))
					{
				somme=somme+1;
					}
		}
		return somme;
	}

	@Override
	public List<Courtier> getCouriters() {
		List<Courtier> crt = em.createQuery("select p from Courtier p", Courtier.class).getResultList();

		return crt;
	}

	@Override
	public double totalgain() {
		double somme=0;
		List<Procuration> liste=findAllProc();
		for(Procuration a : liste)
		{
			
				somme=somme+a.getGain();
					
		}
		return somme;
	}

	@Override
	public int NombreProcurationTraiteParCourtier(int id) {
		int somme=0;
		List<Procuration> liste=getProcByCourtier(id);
		for(Procuration p:liste)
		{
			if(p.getEtat().equals("Traité")) {
				somme=somme+1;
			}
		}
		return somme;
	}

	
	
}
