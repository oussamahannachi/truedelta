package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.*;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.br.CPF;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import entities.Agence;
import entities.Client;
import entities.Compte;
import entities.Devise;
import interfaces.CompteServiceRemote;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@Stateless
@LocalBean
public class CompteService implements CompteServiceRemote {

	@PersistenceContext(unitName="truedelta-ejb")
	EntityManager em;
	
	MongoClient con = new MongoClient("localhost",27017);
	MongoDatabase db = con.getDatabase("truedelta");
	Document doc = new Document();
	private static final DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");	 
	
		
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public long ajouterCompte(Compte c) throws ParseException {
		DateFormat format= new SimpleDateFormat("dd/MM/YYYY");
		c.setIsAutorise(false);
		c.setIsVerifie(false);
		c.setIsActif(true);
		c.setScore(0);
		c.setGab(0);
		c.setLastVerif(format.parse("01/01/2020"));
		c.setRemarque("Le compte n'est pas encore vérifié");
		em.persist(c);
		return c.getNumero();
	}

	@Override
	public Compte getCompteByNumero(long num) {
		try {
		return em.createQuery("select c from compte c where c.numero= ? ", Compte.class)
				 .setParameter(1, num)
				 .getSingleResult();
			}catch (NoResultException e) {
				System.err.println("Verifier le numero du compte");
			}
		return null;
	}

	@Override
	public List<Compte> getAllCompte() {
		return em.createQuery("select c from compte c", Compte.class)
				 .getResultList();
	}
	
	@Override
	public List<Compte> getAllCompteByAgence(int id) {
		return  em.createQuery("select c from compte c where idAgence=? and isAutorise = ? and isActif = ? ", Compte.class)
							  .setParameter(1, id)
							  .setParameter(2, true)
							  .setParameter(3, true)
							  .getResultList();
	}
	
	@Override
	public List<Compte> getAllCompteByBanque(int id) {
		return  em.createQuery("select c from compte c where idAgence=? ORDER BY c.lastVerif DESC", Compte.class)
							  .setParameter(1, id)
							  .getResultList();
	}
	
	
	@Override
	public List<Compte> getAllCompteByClient(int id) {
		return  em.createQuery("select c from compte c where idClient = ?", Compte.class)
							  .setParameter(1, id)
							  .getResultList();
	}

	@Override
	public List<Agence> getAllAgence(String banquename,int id){
		List <Agence> l = new ArrayList<Agence>();
		List<Agence> list = em.createQuery("select a from Agence a where a.banqueName = ? ", Agence.class)
				 .setParameter(1, banquename)
				 .getResultList();
		List<Compte> comptes = getAllCompteByClient(id);
		if(comptes.isEmpty()) {
			return list;
		}
		else { 
			for (Agence agence : list) {
				Compte c = null;
				try {
					c=em.createQuery("select c from compte c where idClient=? and idAgence=?", Compte.class)
							.setParameter(1, id)
							.setParameter(2, agence.getId())
							.getSingleResult();
					}catch (NoResultException e) {
							System.err.println("Aucun compte dans cette agence");
				}
				if(c==null) {
					l.add(em.find(Agence.class, agence.getId()));
				}
		}
		return l;
		}
	}
	
	@Override
	public List<Compte> filtrerComptes(String banquename, String devise,int actif){
		List<Compte> comptes ;
		List <Compte> l = new ArrayList<Compte>();
		int inf=0;
		int sup=0;
		switch(actif) {
			case 0:
				inf=0;
				sup=10000;
				break;
			case 1:
				inf=0;
				sup=4;
				break;
			case 2:
				inf=5;
				sup=9;
				break;
			case 3:
				inf=10;
				sup=19;
				break;
			case 4:
				inf=20;
				sup=100000;
				break;
		}
		if(banquename.equals("Tous") && devise.equals("Tous"))
			l = em.createQuery("select c from compte c where c.isActif=? AND c.nbAction + c.nbObligation BETWEEN ? and ?", Compte.class)
					 .setParameter(1, true)
					 .setParameter(2, inf)
					 .setParameter(3, sup)
					 .getResultList();
		else if (banquename.equals("Tous") && !(devise.equals("Tous"))) {
			comptes = em.createQuery("select c from compte c where c.isActif=? AND c.nbAction + c.nbObligation BETWEEN ? and ?", Compte.class)
					 .setParameter(1, true)
					 .setParameter(2, inf)
					 .setParameter(3, sup)
					 .getResultList();
			for (int i=0;i<comptes.size();i++) {
				if((comptes.get(i).getDevise().toString().equals(devise))) {
					l.add(getCompteByNumero(comptes.get(i).getNumero()));
				}
			}
		}
		else {
		comptes = em.createQuery("select c from compte c "
									  + "left join c.agence Agence "
									  + "where Agence.banqueName = ? AND c.isActif=? AND c.nbAction + c.nbObligation BETWEEN ? and ? ", Compte.class)
				  .setParameter(1, banquename)
				  .setParameter(2, true)
				  .setParameter(3, inf)
				  .setParameter(4, sup)
				  .getResultList();
		if (devise.equals("Tous")) {
			l=comptes;
		}
		for (int i=0;i<comptes.size();i++) {
			if((comptes.get(i).getDevise().toString().equals(devise))) {
				l.add(getCompteByNumero(comptes.get(i).getNumero()));
			}
		}
		}
		return l;
	}
	
	@Override
	public long modifierCompte(Compte c) {
		em.merge(c);
		return (c.getNumero());
	}

	@Override
	public void supprimerCompte(long num) {
		em.createQuery("delete from compte c where c.numero=?")
		.setParameter(1, num)
		.executeUpdate();
	}

	// -----------------------------------------------------mongoDB---------------------------------------------------------------------
	
	//Verifier l'exsitence d'une collection  
	@Override
	public boolean verifierCollection(String nomCollection) {
		MongoIterable<String> names = db.listCollectionNames();
		MongoCursor<String> cursor = names.cursor(); 
		while(cursor.hasNext()) {
			if (cursor.next().equals(nomCollection))
				return true;
		}
		return false;
	}

	//Creation d'un doc à partir d'un compte
	@Override
	public Document createDocByCompte(Compte c) {
		Calendar cal = Calendar.getInstance();
		return doc.append("_id", new ObjectId())
				  .append("numero",c.getNumero())
				  .append("devise", c.getDevise().toString())
				  .append("solde", c.getSolde())
				  .append("actions", c.getNbAction())
				  .append("obligations", c.getNbObligation())
				  .append("source", "truedelta")
				  .append("date", formatter.format(cal.getTime()));
	}
	
	//Creation d'un doc à partir d'un fichier excel
	@Override
	public Document createDocByExcel(Compte c) {
		Calendar cal = Calendar.getInstance();
		return doc.append("_id", new ObjectId())
				  .append("numero",c.getNumero())
				  .append("devise", c.getDevise().toString())
				  .append("solde", c.getSolde())
				  .append("actions", c.getNbAction())
				  .append("obligations", c.getNbObligation())
				  .append("source", "excel")
				  .append("date", formatter.format(cal.getTime()));
					
	}
	
 	@Override
	public void exceltoMongoDB(File f, int idAgence) throws IOException {
 		Calendar cal = Calendar.getInstance();
 		Agence agence = em.find(Agence.class, idAgence);
 		String nom = (agence.getBanqueName()+agence.getAgenceName()).toLowerCase();
 		
 		if((verifierCollection(nom))) 
 			db.getCollection(nom).deleteMany(Filters.in("source","excel","truedelta"));
 		else 
 			db.createCollection(nom);
 		MongoCollection<Document> collection = db.getCollection(nom);
		
 		List<Compte> comptes = getAllCompteByAgence(idAgence);
		FileInputStream excelFile = new FileInputStream(f);
		
		Workbook workbook ;
		if (f.getName().toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(excelFile);
		} else {
			workbook = new XSSFWorkbook(excelFile);
		}
		
		Sheet sheet = workbook.getSheet("comptes");
		agence.setDernierenvoi(new Date(formatter.format(cal.getTime())));
		if(!(comptes.isEmpty())) 
		{
			for(Compte compte : comptes) {
				Compte nvCompte = new Compte();
				for(Row row : sheet)
					for (Cell cell : row) {
						if(cell.getCellType()==0) {
							if(cell.getNumericCellValue()==compte.getNumero()) {
								Client nvClient = new Client();
								nvCompte.setNumero((long)cell.getNumericCellValue());
								nvClient.setNom(row.getCell(1).getStringCellValue());
								nvClient.setPrenom(row.getCell(2).getStringCellValue());
								nvCompte.setProprietaire(nvClient);
									if(row.getCell(3).getStringCellValue().toLowerCase().equals("euro"))
										nvCompte.setDevise(Devise.euro);
									else if(row.getCell(3).getStringCellValue().toLowerCase().equals("dollar"))
										nvCompte.setDevise(Devise.dollar);
									else nvCompte.setDevise(Devise.dinar);
								nvCompte.setSolde((float)row.getCell(4).getNumericCellValue());
								nvCompte.setNbAction(nbrActions(f, compte.getNumero()));
								nvCompte.setNbObligation(nbrObligations(f, compte.getNumero()));
								compte.setLastVerif(new Timestamp(System.currentTimeMillis()));
								collection.insertOne(createDocByExcel(nvCompte));
								collection.insertOne(createDocByCompte(compte));
						}
					}
				}
			}		
		}
 	  
 	}
 	
 	@Override
 	public Document getDoc(String nom,long num) {
 		Document excel= db.getCollection(nom).find(Filters.and(Filters.eq("numero", num),Filters.eq("source","excel"))).first();
 		return excel;
 	}
 	
 	
 	//----------------------------------------------------------------Excel service----------------------------------------------------------
	@Override
	public int isValide(File file) throws IOException, FileNotFoundException {
		if(!(file.getName().contains("xls"))||(!file.exists())) return 0; // test extension
		else
		{
			FileInputStream excelFile = new FileInputStream(file);
			Workbook workbook ;
			if (file.getName().toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(excelFile);
			} else {
				workbook = new XSSFWorkbook(excelFile);
			}
			if(workbook.getNumberOfSheets()!=3) {
				workbook.close();
				excelFile.close();
				return 1; // fichier excel mais nombre de sheet < 3
			}
			else
			{
				int test=0;
				for(int i=0;i<3;i++) {
					if((workbook.getSheetName(i).toLowerCase().equals("comptes"))||(workbook.getSheetName(i).toLowerCase().equals("actions"))||(workbook.getSheetName(i).toLowerCase().equals("obligations"))) {
						test++;
					}
				}
				workbook.close();
				excelFile.close();
				if(test == 3) return 3; // format valide
				else return 2; // les sheets non valides
			}
		}
	}

	@Override
	public int isEmpty(File file) throws IOException {
		FileInputStream excelFile = new FileInputStream(file);
		Workbook workbook ;
		if (file.getName().toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(excelFile);
		} else {
			workbook = new XSSFWorkbook(excelFile);
		}
		int c = 0;
		for (Sheet sheet : workbook) 
		{
			for (Row row : sheet)
			{
				for(Cell cell : row) 
				{
					if(cell.getCellType()==3) 
					{
						c++;
					}
				}
			}
		}
		workbook.close();
		excelFile.close();
		return c;
	}

	@Override
	public int nbrActions(File file, long num) throws IOException {
		FileInputStream excelFile = new FileInputStream(file);
		Workbook workbook ;
		if (file.getName().toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(excelFile);
		} else {
			workbook = new XSSFWorkbook(excelFile);
		}
		Sheet sheet = workbook.getSheet("actions");
		int action =0;
		for(Row row : sheet) 
		{
			if(row.getRowNum()>0) {
				Cell cell=row.getCell(0);
				if(cell.getCellType()==0) {
					if(cell.getNumericCellValue()==num) {
						action++;
					}
				}
			}
		}
		workbook.close();
		excelFile.close();
		return action;
	}

	@Override
	public int nbrObligations(File file, long num) throws IOException {
		FileInputStream excelFile = new FileInputStream(file);
		Workbook workbook ;
		if (file.getName().toLowerCase().endsWith("xls")) {
			workbook = new HSSFWorkbook(excelFile);
		} else {
			workbook = new XSSFWorkbook(excelFile);
		}
		Sheet sheet = workbook.getSheet("obligations");
		int oblgation =0;
		for(Row row : sheet) 
		{
			if(row.getRowNum()>0) {
				Cell cell=row.getCell(0);
				if(cell.getCellType()==0) {
					if(cell.getNumericCellValue()==num) {
						oblgation++;
					}
				}
			}
		}
		workbook.close();
		excelFile.close();
		return oblgation;
	}
	
	//----------------------------------------------------------Matching--------------------------------------------------------------------
	@Override
	public void matchData(int idAgence) {
		Agence agence = em.find(Agence.class, idAgence);
 		String nom = (agence.getBanqueName()+agence.getAgenceName()).toLowerCase();
 		float gab = 0;
 		if((verifierCollection(nom))) 
 		{ 
 			MongoCollection<Document> collection = db.getCollection(nom);
 			List<Compte> comptes = getAllCompteByAgence(idAgence);
 			if(!(comptes.isEmpty())) 
 			{
 			for (Compte compte : comptes)
 			  {
 				Document excel= db.getCollection("stbariana").find(Filters.and(Filters.eq("numero", compte.getNumero()),Filters.eq("source","excel"))).first();
 				Document truedelta = db.getCollection("stbariana").find(Filters.and(Filters.eq("numero", compte.getNumero()),Filters.eq("source","truedelta"))).first();
 				if((excel!=null)&&(truedelta!=null)) 
 				{
 					int val = Classification(excel, truedelta);
 					String s1 = excel.getString("devise")+""+excel.getDouble("solde")+excel.getInteger("actions")+""+excel.getInteger("obligations");
 					String s2 = truedelta.getString("devise")+""+truedelta.getDouble("solde")+truedelta.getInteger("actions")+""+truedelta.getInteger("obligations");
 					gab = FuzzySearch.ratio(s1,s2)/(float)100;
 					switch (val) {
					case 0:
						compte.setRemarque("Devise non conforme");
						compte.setGab(gab);
						compte.setIsVerifie(false);
						break;
					case 10:
						compte.setRemarque("Problème de solde");
						compte.setGab(gab);
						compte.setIsVerifie(false);
						break;
					case 20:
						compte.setRemarque("Problème des actions");
						compte.setGab(gab);
						compte.setIsVerifie(false);
						break;
					case 30:
						compte.setRemarque("Problème des obligations");
						compte.setGab(gab);
						compte.setIsVerifie(false);
						break;
					case 1:
						compte.setRemarque("Aucun problème");
						compte.setGab(1-gab);
						compte.setIsVerifie(true);
						break;
					default:
						break;
					}
 					Calendar cal = Calendar.getInstance();
 					compte.setLastVerif(new Date(formatter.format(cal.getTime())));
 				}
 			  }
 			}
 		}
	}
	
	@Override
	public int Classification(Document excel, Document truedelta) {
		
		if(!(excel.get("devise").equals(truedelta.get("devise"))))
			return 0; // Devise 
		else 
		{
			if(Math.abs(excel.getDouble("solde")-truedelta.getDouble("solde"))>1000) 
			{
				return 10;
			}
			if(Math.abs(excel.getInteger("actions")-truedelta.getInteger("actions"))>1) 
			{
				return 20;
			}
			if(Math.abs(excel.getInteger("obligations")-truedelta.getInteger("obligations"))>1)
			{
				return 30;	
			}
			else {
				return 1;
			}
		}
	}
	
	//------------------------------------------------------------Convertisseur--------------------------------------------------------------
	
	@Override
	public Map<String, Float> lastTaux() throws IOException {
		org.jsoup.nodes.Document webPage = Jsoup.connect("https://finance.yahoo.com/currencies").timeout(60000).get();
		Elements dev = webPage.getElementsByClass("data-col1");
		Elements val = webPage.getElementsByClass("data-col2");
		Map<String, Float> hm = new HashMap<>();
		for(int i=0;i<dev.size();i++) {
			if(!(val.get(i).text().contains(","))) {
				
				hm.put(dev.get(i).text(), Float.parseFloat(val.get(i).text()));
			}
		}
		return hm;
	}

	@Override
	public double convertisseur(String de, String a, double quantite) throws IOException {
		if(quantite<=0) {
			return 0;
		}
		org.jsoup.nodes.Document convertissuer = Jsoup.connect("https://www.boursorama.com/bourse/devises/convertisseur-devises/"+de+"-"+a).timeout(200000).get();
		Elements tr = convertissuer.select("tr.c-table__row");
        String s = tr.select("td").get(1).text();
        String[] val1=s.split(" ");
        double coef = Double.parseDouble(val1[0]);
		return coef*quantite;
	}
	
	
}
