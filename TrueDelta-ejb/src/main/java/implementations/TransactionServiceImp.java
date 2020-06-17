
package implementations;



import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.*;

import services.TransactionServiceRemote;






@Stateless
@LocalBean

public class TransactionServiceImp implements TransactionServiceRemote {
	@PersistenceContext(unitName ="truedelta-ejb")
	private EntityManager em;


	public TransactionServiceImp() {
	}
	//***************************CRUD TRANSACTION***************************************************
	@Override
	public int ajouterTransaction(Transaction transaction) {
		System.out.println("AddTransaction : ");
		/*transaction.setNumcompte(0);
		transaction.setIsValide(false);
		
		transaction.setRendementTransaction(0);
		transaction.setScoreTransaction(0);
		SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
		Date date = new Date();
		DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
		DateFormat.MEDIUM,
		DateFormat.MEDIUM);
		transaction.setDate(date);
		transaction.setType("achat");*/
		
		
		em.persist(transaction);
		System.out.println("TransactionAdded : ");
		return transaction.getId();
		}
	@Override
	public void removeTransaction(int id) {
	System.out.println("RemoveTransaction : ");
	try {
	em.remove(em.find(Transaction.class, id));
	System.out.println("TransactionRemoved : ");
	} catch (Exception e) {
		System.out.println("Erreur : " + e);
	}
	} 
	@Override
	public void updateTransaction(Transaction transactionupdated) {
	System.out.println("UpdatedTransaction : ");
	Transaction transaction = em.find(Transaction.class, transactionupdated.getId());
	transaction.setType(transactionupdated.getType());
	System.out.println("Out of updateUser : "+ transaction.getId());
	
	}
	@Override
	public Transaction getTransactionById(int transactionId) {
		
		Transaction trans = em.find(Transaction.class, transactionId);
		if(trans==null) throw new RuntimeException("transaction introuvable !");
		return trans;
	}
	@Override
	public ActifFinancier getActifById(int ida) {
		
		ActifFinancier a = em.find(ActifFinancier.class, ida);
		if(a==null) throw new RuntimeException("actif introuvable !");
		return a;
	}
	@Override
	public Compte getCompteById(ComptePK pck) {
		
		Compte c = em.find(Compte.class, pck);
		if(c==null) throw new RuntimeException("compte introuvable !");
		return c;
	}

	@Override
	public Compte getCompteByNumero(long numero) {
		try {
		return em.createQuery("select c from Compte c where c.numero= ? ", Compte.class)
				 .setParameter(1, numero)
				 .getSingleResult();
			}catch (NoResultException e) {
				System.err.println("Verifier le numero du compte");
			}
		return null;
	}
	//***************************Validation du Transaction**************************************************************************

	@Override
	public double affecterActifTransaction(int actifId, int transactionId) throws IOException {
		Transaction trans = em.find(Transaction.class, transactionId);
		ActifFinancier actif = em.find(ActifFinancier.class, actifId);
		if(actif.getPrix()<=0) {
			return 0;}
		
		org.jsoup.nodes.Document convertissuer = Jsoup.connect("https://www.boursorama.com/bourse/devises/convertisseur-devises/"+actif.getCurrency()+"-"+ "dollar").timeout(20000).get();
		String s=convertissuer.getElementsByClass("c-table__cell c-table__cell--dotted").get(1).text();
		String[] val1=s.split(" ");
		double coef = Double.parseDouble( val1[0]);
		
		double prix ;
		prix = coef*actif.getPrix();
		System.out.println("l'actif dont l'Id = " +actif.getId() + "  Starting with  "+ actif.getPrix()+" en "+ actif.getCurrency());
		System.out.println("Prix actif apres chagement de devise "+ prix +" en "+ "dollar");
		
		trans.setActif(actif);
	    trans.setRendementTransaction((float) ((prix * 1)/ 100));
		em.merge(actif);
		return prix;
	}



	@Override
	public double affecterTransactionCompte(ComptePK pck , int actifId,int transactionId,String devisecompt) throws IOException {

		Transaction trans = em.find(Transaction.class, transactionId);
		Compte compt = em.find(Compte.class,  pck);
		
		ActifFinancier actif = em.find(ActifFinancier.class, actifId);
		 
	   
		if(actif.getPrix()<=0) {
			return 0;}
		
		org.jsoup.nodes.Document convertissuer = Jsoup.connect("https://www.boursorama.com/bourse/devises/convertisseur-devises/"+actif.getCurrency()+"-"+ devisecompt).timeout(20000).get();
		String s=convertissuer.getElementsByClass("c-table__cell c-table__cell--dotted").get(1).text();
		String[] val1=s.split(" ");
		double coef = Double.parseDouble( val1[0]);
		
		double prix ;
		prix = coef*actif.getPrix();
		System.out.println("l'actif dont l'Id = " +actif.getId() + "  Starting with  "+ actif.getPrix()+" en "+ actif.getCurrency());
		System.out.println("Prix actif apres chagement de devise "+ prix +" en "+ compt.getDevise());
		
		if(compt.getSolde() >= prix && trans.getType().equalsIgnoreCase("achat") && actif.getType().equalsIgnoreCase("obligation") ) 
		{compt.setNbObligation(compt.getNbObligation() + 1);
		      trans.setIsValide(true);
		      actif.setCompte(compt);
				em.merge(actif);
				System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()- prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
				return prix;	
		       }
		else if(compt.getSolde() >= prix && trans.getType().equalsIgnoreCase("achat") && actif.getType().equalsIgnoreCase("action")){
			compt.setNbAction(compt.getNbAction()+1);
		       trans.setIsValide(true);
               actif.setCompte(compt);
				em.merge(actif);
				System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()- prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
				return prix;}
		  
		else if (trans.getType().equalsIgnoreCase("vente")&& actif.getType().equalsIgnoreCase("obligation")) {
			   compt.getActifs().remove(actif);
		       compt.setNbObligation(compt.getNbObligation()-1);
		       trans.setIsValide(true);
		       System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()+ prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
		       return prix;}
		else if(trans.getType().equalsIgnoreCase("vente") && actif.getType().equalsIgnoreCase("action")){
			   compt.getActifs().remove(actif);
		       compt.setSolde(compt.getSolde()+actif.getPrix());
		       compt.setNbAction(compt.getNbAction()-1);
		       trans.setIsValide(true);
		       System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()+ prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
		       return prix;}
		else {trans.setIsValide(false);}
		return prix ;}
	


	@Override
	public double affecterTransactionCompte1(long numero , int actifId,int transactionId,String devisecompt) throws IOException {
Compte compt ;
		Transaction trans = em.find(Transaction.class, transactionId);
		compt = em.createQuery("select c from Compte c where c.numero= ? ", Compte.class)
		 .setParameter(1, numero)
		 .getSingleResult();
		
		ActifFinancier actif = em.find(ActifFinancier.class, actifId);
		 
	   
		if(actif.getPrix()<=0) {
			return 0;}
		
		org.jsoup.nodes.Document convertissuer = Jsoup.connect("https://www.boursorama.com/bourse/devises/convertisseur-devises/"+actif.getCurrency()+"-"+ devisecompt).timeout(20000).get();
		String s=convertissuer.getElementsByClass("c-table__cell c-table__cell--dotted").get(1).text();
		String[] val1=s.split(" ");
		double coef = Double.parseDouble( val1[0]);
		
		double prix ;
		prix = coef*actif.getPrix();
		System.out.println("l'actif dont l'Id = " +actif.getId() + "  Starting with  "+ actif.getPrix()+" en "+ actif.getCurrency());
		System.out.println("Prix actif apres chagement de devise "+ prix +" en "+ compt.getDevise());
		
		if(compt.getSolde() >= prix && trans.getType().equalsIgnoreCase("achat") && actif.getType().equalsIgnoreCase("obligation") ) 
		{compt.setNbObligation(compt.getNbObligation() + 1);
		      trans.setIsValide(true);
		      actif.setCompte(compt);
				em.merge(actif);
				System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()- prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
				return prix;	
		       }
		else if(compt.getSolde() >= prix && trans.getType().equalsIgnoreCase("achat") && actif.getType().equalsIgnoreCase("action")){
			compt.setNbAction(compt.getNbAction()+1);
		       trans.setIsValide(true);
               actif.setCompte(compt);
				em.merge(actif);
				System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()- prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
				return prix;}
		  
		else if (trans.getType().equalsIgnoreCase("vente")&& actif.getType().equalsIgnoreCase("obligation")) {
			   compt.getActifs().remove(actif);
		       compt.setNbObligation(compt.getNbObligation()-1);
		       trans.setIsValide(true);
		       System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()+ prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
		       return prix;}
		else if(trans.getType().equalsIgnoreCase("vente") && actif.getType().equalsIgnoreCase("action")){
			   compt.getActifs().remove(actif);
		       compt.setSolde(compt.getSolde()+actif.getPrix());
		       compt.setNbAction(compt.getNbAction()-1);
		       trans.setIsValide(true);
		       System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
				System.out.println("Prix transaction"+ prix);
				compt.setSolde((float) (compt.getSolde()+ prix));
				System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
		       return prix;}
		else {trans.setIsValide(false);}
		return prix ;}	
//////********************Consultation des transactions	*****************************	
	@Override
	public List<Transaction> consulterTransactions(){
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
			
			l.add(t);
		}
		return l;}	
	@Override
	public List<Transaction> consulterTransactionsv(){
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
			if(t.getIsValide()==true) {
			l.add(t);
		}}
		return l;}	
	@Override
	public List<Transaction> consulterTransactionsnv(){
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
			if(t.getIsValide()==false) {
			l.add(t);
		}}
		return l;}
	@Override
	public List<Transaction> consulterTransactionssnv(){
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
			if( t.getRendementTransaction() > 0 && t.getScoreTransaction()==0) {
			l.add(t);
		}}
		return l;}
	  @Override
			public List<ActifFinancier> consulterActifs() {
				List<ActifFinancier> actifs = em.createQuery("select a from ActifFinancier a", ActifFinancier.class).getResultList();
				List<ActifFinancier> la=new ArrayList<>();
				for(ActifFinancier a:actifs) {
						la.add(a);
				}
				return la;}
	   @Override
		public List<ActifFinancier> consulterActifsv() {
			List<ActifFinancier> actifs = em.createQuery("select a from ActifFinancier a", ActifFinancier.class).getResultList();
			List<ActifFinancier> la=new ArrayList<>();
			for(ActifFinancier a:actifs) {
				if(a.getCompte()!=null) {
					la.add(a);}
			}
			return la;}
	   @Override
	 		public List<ActifFinancier> consulterActifsnv() {
	 			List<ActifFinancier> actifs = em.createQuery("select a from ActifFinancier a", ActifFinancier.class).getResultList();
	 			List<ActifFinancier> la=new ArrayList<>();
	 			for(ActifFinancier a:actifs) {
	 				if(a.getCompte()==null) {
	 					la.add(a);}
	 			}
	 			return la;}
	   @Override
			public List<Compte> consulterCompte() {
				List<Compte> comptes = em.createQuery("select a from Compte a", Compte.class).getResultList();
				List<Compte> la=new ArrayList<>();
				for(Compte a:comptes) {
						la.add(a);
				}
				return la;}
	   @Override
		public List<Transaction> consulterTransactionParCompte1(long numc) {
			List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
			List<Transaction> la=new ArrayList<>();
			for(Transaction t:trans) {
				if(t.getNumcompte() == numc)
					la.add(t);
			}
			return la;}
	   @Override
			public List<Transaction> consulterTransactionScoreTop() {
				List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
				List<Transaction> la=new ArrayList<>();
				for(Transaction t:trans) {
					if(t.getScoreTransaction()>40)
						la.add(t);
				}
				return la;}
	   @Override
			public List<Transaction> consulterTransactionScoreMoyen() {
				List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
				List<Transaction> la=new ArrayList<>();
				for(Transaction t:trans) {
					if(t.getScoreTransaction()<=40 && t.getScoreTransaction()>20)
						la.add(t);
				}
				return la;}
	   @Override
		public List<Transaction> consulterTransactionScoreFaible() {
			List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
			List<Transaction> la=new ArrayList<>();
			for(Transaction t:trans) {
				if(t.getScoreTransaction()<=20 && t.getScoreTransaction()>5)
					la.add(t);
			}
			return la;}
    @Override
	public List<ActifFinancier> consulterTransactionParCompte(long numc) {
		List<ActifFinancier> actifs = em.createQuery("select a from ActifFinancier a", ActifFinancier.class).getResultList();
		List<ActifFinancier> la=new ArrayList<>();
		for(ActifFinancier a:actifs) {
			if(a.getCompte().getNumero() == numc)
				la.add(a);
		}
		return la;}
    @Override
	public Map<Integer, Float> classerTransactionValidéesById() {

		Map<Integer, Float> m = new HashMap<Integer, Float>(); 

		TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction  t", Transaction.class);  		
		List<Transaction> listTransaction = query.getResultList(); 
		
		for (Transaction transaction : listTransaction)
		{if (!m.containsKey(transaction.getId()))
					{ m.put(transaction.getId(),  transaction.getRendementTransaction());
			    	}
		}
		Map<Integer, Float> treeMap = new TreeMap<Integer, Float>(m).descendingMap();
   return treeMap ;}
	@Override
	public List<Transaction> classerTransactionByIdliste() {
		
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
		if(t.getIsValide()==true)	{
				l.add(t);
		}}
		return l;
	}
	@Override
	public List<Transaction> classerTransactionNonScoré() {
		
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
	
	     if(t.getIsValide()==true)	{
		    		if(t.getRendementTransaction()!=0) {
		    			if(t.getScoreTransaction()==0)
				l.add(t);
		}}}
		return l;
	}
	@Override
	public List<Transaction> classerTransactionScoré() {
		
		List<Transaction> trans = em.createQuery("select t from Transaction t", Transaction.class).getResultList();
		List<Transaction> l=new ArrayList<>();
		for(Transaction t:trans) {
	
	     if(t.getIsValide()==true)	{
	    	 if(t.getRendementTransaction()!=0) {
	    			if(t.getScoreTransaction()!=0)
		    		
		    			
				l.add(t);
		}}}
		return l;
	}
	@Override
	public Map<String, String>ProduitDeBourse() throws IOException {
	org.jsoup.nodes.Document webPage = Jsoup.connect("https://www.boursorama.com/bourse/matieres-premieres/produits-bourse").timeout(6000).get();
	Elements nom = webPage.getElementsByClass("c-table__cell c-table__cell--dotted u-text-left");
	Elements prix = webPage.getElementsByClass("c-table__cell c-table__cell--dotted");
	List<String> nomt = new ArrayList<String>();
	List<String> prixt = new ArrayList<String>();
	Map<String, String> hm = new HashMap<>();
	for (Element element : nom) {
	nomt.add(element.text());}
	for (Element element : prix) {
	if(!(element.text().contains(",")))
	prixt.add(element.text());}
	for (int i = 1; i < prixt.size(); i++) {
	hm.put(nomt.get(i).toString(),prixt.get(i).toString());}
	return hm;}
///////////********************Statistique************************
	
@Override
public int Scooringtransaction(int id ) {
	Transaction trans = em.find(Transaction.class , id);
	if(trans.getRendementTransaction()< 5 && trans.getScoreTransaction()>0) {
		trans.setScoreTransaction(0);}
	else if (trans.getRendementTransaction()>=5 && trans.getRendementTransaction()<=10) {
		trans.setScoreTransaction(10);}
	else if (trans.getRendementTransaction()>10 && trans.getRendementTransaction()<=20) {
		trans.setScoreTransaction(15);}
	else if (trans.getRendementTransaction()>20 && trans.getRendementTransaction()<=40) {
		trans.setScoreTransaction(20);}
	else if (trans.getRendementTransaction()>40 && trans.getRendementTransaction()<=100) {
		trans.setScoreTransaction(25);}
	else if (trans.getRendementTransaction()>100 && trans.getRendementTransaction()<=500) {
		trans.setScoreTransaction(30);}
	else {trans.setScoreTransaction(40);}
	return trans.getScoreTransaction();
}
@Override
public int getNbtransvalide() {


	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtvalide=0;
	for (Transaction t : listTransaction) {
		
			

		if (t.getIsValide()==true) { 
		nbtvalide=nbtvalide+1;
	
	
}}return nbtvalide;}

@Override
public int getNbtransNonvalide() {


	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
		if (t.getIsValide()==false) { 
		nbtNonvalide=nbtNonvalide+1;
	}}return nbtNonvalide;}
@Override
public int getNbtransvalideparJour() throws ParseException {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	
	int nbTVJ ;
	nbTVJ=0;
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
	        if(t.getDate().getYear()==y)	{
	             if(t.getDate().getMonth()==m) {		
	                        if( t.getDate().getDay() == j ) {
		nbTVJ=nbTVJ+1;
	}}}}}
	return nbTVJ;
}
@Override
public int getNbtransNvalideparJour() throws ParseException {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	int nbTVJ ;
	nbTVJ=0;
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
	        if(t.getDate().getYear()==y)	{
	             if(t.getDate().getMonth()==m) {		
	                        if( t.getDate().getDay() == j ) {
		nbTVJ=nbTVJ+1;
	}}}}}
	return nbTVJ;
}
@Override
public int getNbtransvalideparMois() throws ParseException {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	int nbTVJ ;
	nbTVJ=0;
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
	        if(t.getDate().getYear()==y)	{
	             if(t.getDate().getMonth()==m) {		
	                        
		nbTVJ=nbTVJ+1;
	}}}}
	return nbTVJ;
}
@Override
public int getNbtransNvalideparMois() throws ParseException {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	int nbTVJ ;
	nbTVJ=0;
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
	        if(t.getDate().getYear()==y)	{
	             if(t.getDate().getMonth()==m) {		
	                       
		nbTVJ=nbTVJ+1;
	}}}}
	return nbTVJ;
}
@Override
public int getNbtransvalideparAnnee() throws ParseException {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	int nbTVJ ;
	nbTVJ=0;
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
	if(t.getDate().getYear()== y && t.getIsValide()==true) {
		nbTVJ=nbTVJ+1;
	}}
	return nbTVJ;
}
@Override
public int getNbtransNvalideparAnnee() throws ParseException {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	
	int nbTVJ ;
	nbTVJ=0;
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t", Transaction.class);  		
	List<Transaction> listTransaction = query.getResultList(); 
	int nbtNonvalide=0;
	for (Transaction t : listTransaction) {
	if(t.getDate().getYear()== y && t.getIsValide()==false) {
		nbTVJ=nbTVJ+1;
	}}
	return nbTVJ;
}
@Override
public double profitparJ() {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();
	/*DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
	DateFormat.MEDIUM,
	DateFormat.MEDIUM);*/

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	int j=date.getDay();
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t WHERE t.isValide=:true ", Transaction.class); 
	query.setParameter("true", true);		
	List<Transaction> listTransaction = query.getResultList(); 
  double profity = 0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
	        if(t.getDate().getYear()==y)	{
	             if(t.getDate().getMonth()==m) {		
	                        if( t.getDate().getDay() == j ) {
		double r = t.getRendementTransaction();
		 profity = r +  profity ;
		 //profity = r +  profity ;
	}}}}}
	return profity;


}
@Override
public double profitparM() {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();
	/*DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
	DateFormat.MEDIUM,
	DateFormat.MEDIUM);*/

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	int m=date.getMonth();
	
	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t ", Transaction.class); 
	//query.setParameter("true", true);  		
	List<Transaction> listTransaction = query.getResultList(); 
  double profity = 0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
			if(t.getDate().getYear()==y)	{
				if(t.getDate().getMonth()==m) {		
	                  if(t.getDate().getMonth()==m ) {
		double r = t.getRendementTransaction();
	 profity = r +  profity ;
	}}}}}
	return profity;


}
@Override
 public double profitparannee() {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();

	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	/*DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(
	DateFormat.MEDIUM,
	DateFormat.MEDIUM);*/
	int y= date.getYear();
	

	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t ", Transaction.class);  
	//query.setParameter("true", true);
	List<Transaction> listTransaction = query.getResultList(); 
	
   double profity = 0;
	for (Transaction t : listTransaction) {
		if(t.getIsValide()== true) {
	         if(t.getDate().getYear()==y) {
		double r = t.getRendementTransaction();
		 profity = r +  profity ;}
	}}
	return profity;

 
}
@Override
public double profittotal() {
	SimpleDateFormat dateformat=new SimpleDateFormat("dd/mm/yyyy");
	Date date = new Date();
	
	 dateformat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.FRENCH);
	int y= date.getYear();
	

	TypedQuery<Transaction> query = em.createQuery("SELECT t FROM Transaction t  ", Transaction.class); 
	
	List<Transaction> listTransaction = query.getResultList(); 
	
  double profity = 0;
	for (Transaction t : listTransaction) {
	if(t.getIsValide()== true) {
		double r = t.getRendementTransaction();
		 profity = r +  profity ;}
	}
	return profity;


}



	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public void verserPrixTransaction(float solde, ComptePK pck) {
		Compte compt = em.find(Compte.class,  pck);
		System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
		System.out.println("Prix transaction"+ solde);
		compt.setSolde(compt.getSolde()+solde);
		System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
	}
	@Override
	public void retirerPrixTransaction(float solde, ComptePK pck) {
		Compte compt = em.find(Compte.class,  pck);
		System.out.println(compt.getNumeroCompte() + "Starting with"+ compt.getSolde());
		System.out.println("Prix transaction"+ solde);
		if(compt.getSolde() >= solde) {
			compt.setSolde(compt.getSolde()-solde);
			compt.setNbAction(compt.getNbAction()+1);
		}
		else {
			System.out.println("Not enough money to buy this actif");
		}
		
		System.out.println(compt.getNumeroCompte() + "End balance"+ compt.getSolde());
		
	}
	@Override
	public Utilisateur getUserByEmailAndPassword(String login, String password) {
		Query query = em.createQuery("SELECT e FROM Utilisateur e WHERE e.username=? AND e.password=? ");
		query.setParameter(1, login);
		query.setParameter(2, password);
		Utilisateur user = null;
		try {
			user = (Utilisateur) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
		return user;
	}
	@Override
	public Utilisateur loginUser(String login, String password) {
		Query query = em.createQuery("SELECT e FROM Utilisateur e WHERE e.username=? AND e.password=? ");
		query.setParameter(1, login);
		query.setParameter(2, password);
		Utilisateur user = null;
		try {
			user = (Utilisateur) query.getSingleResult();
		} catch (Exception e) {
			System.out.println("Erreur : " + e);
		}
		return user;
	}
	@Override
	public Agence loginAgence(String login,String password) {
		TypedQuery<Agence> query=em.createQuery("Select c from Agence c where c.agenceName=? AND c.password=? ", Agence.class);
		query.setParameter(1, login);
		query.setParameter(2, password);
		Agence agence= null;
		try {
			agence = query.getSingleResult();
		} catch (Exception e) {
		
		}
		return agence;
	}
	
	
}
