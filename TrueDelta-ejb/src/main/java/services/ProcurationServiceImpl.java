package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Client;
import entities.Compte;
import entities.Courtier;
import entities.Devise;
import entities.Procuration;
import entities.ProcurationPK;
import entities.Type;
import interfaces.ProcurationServiceLocal;
import interfaces.ProcurationServiceRemote;

@Stateful

public class ProcurationServiceImpl implements ProcurationServiceLocal , ProcurationServiceRemote{

	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;
	  
	//////////////////////////   CRUD    //////////////////////////////////
	
	@Override
	public ProcurationPK ajouterProcuration(Procuration proc) {
		proc.setAvisContrat(null);
		proc.setDateTraitement(null);
		proc.setEtat("bloque");
		proc.setScore(0);
		proc.setGain(0);
		em.persist(proc);
		return proc.getId();
	}
	
	@Override
	public void deleteProc(ProcurationPK id) {
		em.remove(em.find(Procuration.class,id));
		
	}
	@Override
	public void modifdescrip(ProcurationPK id, String descr) {
		Procuration proc=em.find(Procuration.class, id);
		proc.setDescription(descr);
		
	}

	@Override
	public void modifetat(ProcurationPK id, String etat) {
		Procuration proc=em.find(Procuration.class, id);
		proc.setEtat(etat);
		
	}

	@Override
	public void modifScore(ProcurationPK id, int score) {
		Procuration proc=em.find(Procuration.class, id);
		proc.setScore(score);
	}

	@Override
	public Procuration findprocById(ProcurationPK pk) {
		Procuration proc=em.find(Procuration.class, pk);
		return proc;
	}

	@Override
	public List findAllProc() {
		List <Procuration> procs=em.createQuery("select p from Procuration p",Procuration.class).getResultList();
		
		return procs;
	}
	
	
	@Override
	public List<Procuration> getProcByEtat(String etat) {
		TypedQuery<Procuration> procs = em.createQuery("select p from Procuration p where p.etat = :etat",Procuration.class);
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
	
	/////////////////////////  nbre de procurations pour chaque client 

	@Override
	public int procnbre(int idclient) {
		Client user = em.find(Client.class, idclient);
		javax.persistence.Query q = em.createQuery("SELECT Count(p) FROM Procuration p WHERE p.client = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}
	
	
	//////////////////////////////  nbre de procurations pour chaque courtier
	
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
		int score=0;
		int idClient;
		idClient=pk.getIdClient();
		Client client = em.find(Client.class, idClient);
		if (client.getSalaire()<1000) 
		{     
			score += 10;
		}
		else if (client.getSalaire()>=1000 && client.getSalaire()<=3000)
		{
			score += 30;
		}
		else if (client.getSalaire()>3000 && client.getSalaire()<10000)
		{
			score += 50;
		}
		else
		{
			score +=100;
		}
		
		if (client.getAge()<=30)
		{
			score +=10;
		}
		else if (client.getAge()>30 && client.getAge()<55)
		{
			score +=30;
		}
		else
		{
			score +=50;
		}
		
		if (client.getEtatCivil().equals("marie"))
		{
			score +=10;
		}
		else if (client.getEtatCivil().equals("celibataire") || client.getEtatCivil().equals("divorce"))
		{
			score +=20;
		}
		else 
		{
			score +=0;
		}
		
		if (client.getMetier().equals("financier") || client.getMetier().equals("homme d'affaires"))
		{
			score +=100;
		}
		else 
		{
			score += 30;
		}
		
		nbre =procnbre( idClient );
		
		if ( nbre <10 )
		{
			score += 30;
		}
		else 
		{
			score +=60;
		}
		
		nbrecompte=nbreComptesClient( idClient);
		if(nbrecompte <=2)
		{
			score +=20;
		}
		else 
		{
			score +=40;
		}
		
		List<Compte> l=new ArrayList<>();
		l=client.getComptes();
		for (Compte c : l)
		{
			if (c.getDevise()==Devise.dollar || c.getDevise()==Devise.euro) {
			score +=30;
			}
			else 
			{
				score+=10;
			}
			
			
		}
		int a = pk.getIdClient();
		Boolean b=true;
		Boolean c=true;
		javax.persistence.Query q = em.createQuery("SELECT Count(c.numeroCompte) FROM Compte c join c.proprietaire cl where cl.id=:a and c.isAutorise=:b and c.isVerifie=:d");
		q.setParameter("a",a );
		q.setParameter("b",b );
		q.setParameter("d",c );
		int e=((Number) q.getSingleResult()).intValue();
		int e1= l.size();
		if (e1==e) {
			score+=20;
		}
		
		
		return score;
	}
	
	/////////////////// score Procuration

	@Override
	public int scoreProcuration(ProcurationPK p) {
		int score =0;
		score=scoreClient(p);
		Procuration pk = em.find(Procuration.class, p);
		if (pk.getType()==Type.specifie)
		{
			score +=30;
		}
		else {
			score +=10;
		}
		if (pk.getGain()<=500) {
			
			score +=10;
		}
		else if (pk.getGain()<1000 && pk.getGain()>5000 ) {
			
			score +=30;
		}
		else 
		{
			score +=100;
		}
		
		return score ;
		
	}
	/////////////////// avis contrat bonne ou mauvaise procuration 

	@Override
	public void etatProc(ProcurationPK pk) {
		
		int score = scoreProcuration(pk);
		Procuration proc=em.find(Procuration.class, pk);
		
		if (score > 300)
		{
			proc.setAvisContrat("bonne");
		}
		else 
		{
			proc.setAvisContrat("mauvaise");
		}
		
	}
	
	///////////////////////////// calcul du gain pendant une periode 
	@Override
	public int GainPeriode(Date d ,Date date) throws ParseException  {
		int gain = 0;
	TypedQuery<Procuration> query = em.createQuery("select p from Procuration p WHERE p.dateCreation > :d and p.dateCreation < :date",Procuration.class);
		query.setParameter("d",d);
		query.setParameter("date",date);
		List<Procuration> l=query.getResultList();
		for(Procuration p : l) {
			if (p.getType()==Type.mondat)
			{
				gain +=100;
			}
			else if (p.getType()==Type.specifie)
			{
				gain +=110;
			}
			else 
			{
				gain +=130;
			}
		}
		
		return gain;
	}
	
	/////////// date traitement 
	
	@Override
	public Date addDays(ProcurationPK pk, Integer days){
		Procuration proc=em.find(Procuration.class, pk);
		Date sdf = proc.getDateCreation();
		Date result ;
		GregorianCalendar calendar = new java.util.GregorianCalendar();
		calendar.setTime(sdf);
		calendar.add (Calendar.DAY_OF_MONTH, days);
		System.out.println(calendar);
		result = calendar.getTime();
		return result;
	}

	@Override
	public void dateTraitement(ProcurationPK pk) {
		
		int a =0;
		int idClient=pk.getIdClient();
		Procuration proc=em.find(Procuration.class, pk);
		Client client = em.find(Client.class, idClient);
		List<Compte> l=new ArrayList<>();
		l=client.getComptes();
		int i = l.size();
		for (Compte c : l)
		{
			if ((c.getIsAutorise()) && (c.getIsVerifie()))
			{
				a +=1;
			}
			
		}
		
		Date d=addDays(pk, 10);
		Date date = new Date(); 
		
		if ( a==i && date.compareTo(d) > 0) 
		{
			proc.setEtat("en cours");
			proc.setDateTraitement(date);
		}
		
		
		
	}
	
	
	///// calcul du pourcentage de bonne procuration par an
	
	@Override
	public void calculPourcentage(int year) {
		int i=0;
		int z=0;
		List<Procuration> l=new ArrayList<>();
		l = findAllProc();
		for (Procuration proc : l) {
			if ((proc.getDateCreation().getYear() + 1900 )== year) {
				i += 1;
				if(proc.getAvisContrat().equals("bonne")) {
					z += 1;
				}
			}
				
		}
		
		if (i == 0) {
			System.out.println("Pas de contrats pour cette année");
		}
		else {
		float prc=(z*100)/i;
		
		System.out.println( "Le pourcentage des bons contrats pour l'année "+year+" est "+prc+" %");
		}
		
	}

	

	///// nbre de comptes vérifié 
	@Override
	public int calculCompteverif(ProcurationPK id) {
		int a = id.getIdClient();
		Boolean b=true;
		Boolean c=true;
		javax.persistence.Query q = em.createQuery("SELECT Count(c.numeroCompte) FROM Compte c join c.proprietaire cl where cl.id=:a and c.isAutorise=:b and c.isVerifie=:d");
		q.setParameter("a",a );
		q.setParameter("b",b );
		q.setParameter("d",c );
		return ((Number) q.getSingleResult()).intValue();
	}
	
	
	
	
	
	
	
	
	
	
}
