package services;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Administrateur;
import entities.Reclamation;
import entities.State;
import entities.Utilisateur;
import entities.Client;
import interfaces.ReclamationServiceLocal;
import interfaces.ReclamationServiceRemote;


@Stateless
@LocalBean
public class ReclamationImp implements  ReclamationServiceLocal,ReclamationServiceRemote {
	@PersistenceContext(unitName = "truedelta-ejb")
	EntityManager em;
	Mail_API mail;
	
	
	@Override
	public int AddReclam(Reclamation rec , int id_user) {
		
	

	           Utilisateur user = em.find(Client.class, id_user); 
	            if(user!=null)
	            {
	            	
	              rec.setUser(user);

	            em.persist(rec);
	            return 1;
	            }
	            else
	            	return 0;}

	    

	        
		

	@Override
	public void deleteReclamById(int RecId) {
		em.remove(em.find(Reclamation.class, RecId));

	}

	@Override
	public void UpdateReclam(Reclamation rec) {
		  {
			  Reclamation c=em.find(Reclamation.class, rec.getId());
			    c.setDescription(rec.getDescription());
			    c.setDateCreation(rec.getDateCreation());
			    c.setSubject(rec.getSubject());
			    c.setState(rec.getState());

			    }

	}

	@Override
	public List<Reclamation> GetAllReclams() {
	    TypedQuery<Reclamation> q = em.createQuery("SELECT c FROM Reclamation c", Reclamation.class);
        return (List<Reclamation>) q.getResultList();
	}

	@Override
	public Reclamation GetReclamById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reclamation> GetReclamByState(String state) {
		State cm = State.valueOf(state);
		TypedQuery<Reclamation> q = em.createQuery("SELECT R FROM Reclamation R WHERE R.state = :state",
				Reclamation.class);
		q.setParameter("state", cm);
		return (List<Reclamation>) q.getResultList();
	}

	@Override
    public int NbReclamByState(String state) {
        State st = State.valueOf(state);
        Query q = em.createQuery("SELECT Count(c) FROM Reclamation c WHERE c.state = :state");
        q.setParameter("state", st);
        return ((Number) q.getSingleResult()).intValue();
    }
	
	
	@Override
	public List<Reclamation> GetReclamsOrderByDateASC() {

		TypedQuery<Reclamation> q = em.createQuery("SELECT c FROM Reclamation c ORDER BY c.dateCreation ASC",
				Reclamation.class);

		return (List<Reclamation>) q.getResultList();
	}

	@Override
	public List<Reclamation> GetReclamsOrderByDateDESC() {

		TypedQuery<Reclamation> q = em.createQuery("SELECT c FROM Reclamation c ORDER BY c.dateCreation DESC",
				Reclamation.class);

		return (List<Reclamation>) q.getResultList();
	}
	
	
	

	
	
	@Override
	public int NbReclamsByUser(int idUser) {

		Client user = em.find(Client.class, idUser);
		Query q = em.createQuery("SELECT Count(c) FROM Reclamation c WHERE c.user = :User");
		q.setParameter("User", user);
		return ((Number) q.getSingleResult()).intValue();
	}
	
	
	@Override
	public boolean TreatComplaint(int idcomplaint, String state) {


		Calendar currenttime = Calendar.getInstance();
		Date now = new Date((currenttime.getTime()).getTime());
		State cm = State.valueOf(state);
		Reclamation complaintBD = em.find(Reclamation.class, idcomplaint);

		
		Client c = (Client)complaintBD.getUser();
    	
    	
    	
    	
    	String email = c.getEmail();
    	System.out.print(email);
    	
		if (cm.equals(State.in_progress)) {
			complaintBD.setAssignmentDate(now);
			complaintBD.setState(cm);
			em.merge(complaintBD);
			
		   
			
		    	 try {
                mail.sendMail(email, "Your complaint is being processed",
                		complaintBD.getSubject()+ "  is being processed at" + complaintBD.getAssignmentDate());

            } catch (MessagingException e) {
                System.out.println("error");
                e.printStackTrace();
            }
			
		} else if (cm.equals(State.treated)) {

			complaintBD.setClosingDate(now);
			complaintBD.setState(cm);
			em.merge(complaintBD);
			
			try {

                mail.sendMail(email, "Your complaint is treated",
                		complaintBD.getSubject() + " is treated at " + complaintBD.getClosingDate()
                                + " with state : " + complaintBD.getState());

            } catch (MessagingException e) {
                System.out.println("error");
                e.printStackTrace();
            }
			
		}
		
		return true;
	//}
		 
		// return false;
		}
	
	
	@Override
	public int NbReclamationByperiod(Date d1, Date d2) {
		Query q = em
				.createQuery("SELECT COUNT(c) FROM Reclamation c WHERE c.dateCreation > :d1 AND c.dateCreation < :d2");
		q.setParameter("d1", d1);
		q.setParameter("d2", d2);
		return ((Number) q.getSingleResult()).intValue();
	}

	
	
		

	    
	    
	    
	    

		
	    @Override
		public List<Reclamation> SearchComplaint(String motclé) {
			TypedQuery<Reclamation> query = em.createQuery(
					"select c from Reclamation c WHERE c.description LIKE :code or c.subject LIKE :code or c.state LIKE :code ORDER BY c.dateCreation DESC",
					Reclamation.class);
			query.setParameter("code", "%" + motclé + "%");
			return query.getResultList();
		}
		
		
		
	    
	    @Override
	    public String verifBadWord(int idRec ) throws InterruptedException
	    { 
	    	
	    Reclamation Rec = em.find(Reclamation.class, idRec);
		       String input1= Rec.getDescription();
		       String input2= Rec.getSubject();
		       String  output1 = BadWordFilter.getCensoredText(input1);
		       String  output2 = BadWordFilter.getCensoredText(input2);

	if (( input1!=output1)||(input2!=output2))
	    	       
	{
		deleteReclamById(idRec);
		
          Client c = (Client)Rec.getUser();
    	
    	
    	
    	
    	String email = c.getEmail();
    	System.out.print(email);
    	
		
		   
			
		    	 try {
                mail.sendMail(email,"Alert" ,"Your complaint  was blocked because a bad word was found. If you believe this word should not be blocked, please message support"
                		);

            } catch (MessagingException e) {
                System.out.println("error");
                e.printStackTrace();
            }
			
		
	}
	    	return ("Success");
	    
}}
	
	
	
	
	
	


