package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.*;
import org.bson.types.ObjectId;
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
import interfaces.CompteServiceLocal;
import interfaces.CompteServiceRemote;
import me.xdrop.fuzzywuzzy.FuzzySearch;

@Stateful
public class CompteService implements CompteServiceLocal, CompteServiceRemote {

	@PersistenceContext(unitName="truedelta-ejb")
	EntityManager em;
	
	MongoClient con = new MongoClient("localhost",27017);
	MongoDatabase db = con.getDatabase("truedelta");
	Document doc = new Document();
	private static final DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");	 
	private Calendar cal = Calendar.getInstance();
		
	@Override
	public long ajouterCompte(Compte c) {
		c.setIsAutorise(false);
		c.setIsVerifie(false);
		c.setScore(0);
		c.setGab(0);
		c.setNbAction(0);
		c.setNbObligation(0);
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
		return  em.createQuery("select c from compte c where idAgence=? and isVerifie = ?", Compte.class)
							  .setParameter(1, id)
							  .setParameter(2, true)
							  .getResultList();
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
 		Agence agence = em.find(Agence.class, idAgence);
 		String nom = (agence.getBanqueName()+agence.getAgenceName()).toLowerCase();
 		if((verifierCollection(nom))) 
 			db.getCollection(nom).deleteMany(Filters.in("source","excel","truedelta"));
 		else 
 			db.createCollection(nom);
 		MongoCollection<Document> collection = db.getCollection(nom);
		List<Compte> comptes = getAllCompteByAgence(idAgence);
		FileInputStream excelFile = new FileInputStream(f);
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheet("comptes");
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
 	
 	
 	
 	
 	//----------------------------------------------------------------Excel service----------------------------------------------------------
	@Override
	public int isValide(File file) throws IOException, FileNotFoundException {
		if(!(file.getName().contains("xls"))||(!file.exists())) return 0; // test extension
		else
		{
			FileInputStream excelFile = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(excelFile);
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
		Workbook workbook = new XSSFWorkbook(excelFile);
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
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheet("actions");
		int action =0;
		for(Row row : sheet) 
		{
			for(Cell cell : row) 
			{
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
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet sheet = workbook.getSheet("obligations");
		int oblgation =0;
		for(Row row : sheet) 
		{
			for(Cell cell : row) 
			{
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
 					gab = FuzzySearch.ratio(s1,"euro2000a0o0")/(float)100;
 					switch (val) {
					case 0:
						compte.setRemarque("Devise non conforme");
						compte.setGab(gab);
						compte.setIsAutorise(false);
						break;
					case 10:
						compte.setRemarque("Problème du solde");
						compte.setGab(gab);
						compte.setIsAutorise(false);
						break;
					case 20:
						compte.setRemarque("Problème des actions");
						compte.setGab(gab);
						compte.setIsAutorise(false);
						break;
					case 30:
						compte.setRemarque("Problème des obligations");
						compte.setGab(gab);
						compte.setIsAutorise(false);
						break;
					case 1:
						compte.setRemarque("Aucun problème");
						compte.setGab(1-gab);
						compte.setIsAutorise(true);
						break;
					default:
						break;
					}
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
		org.jsoup.nodes.Document webPage = Jsoup.connect("https://finance.yahoo.com/currencies").timeout(6000).get();
		Elements dev = webPage.getElementsByClass("data-col1");
		Elements val = webPage.getElementsByClass("data-col2");
		List<String> devises = new ArrayList<String>();
		List<String> valeurs = new ArrayList<String>();
		Map<String, Float> hm = new HashMap<>();
		 for (Element element : dev) {
			devises.add(element.text());
		 }
		 for (Element element : val) {
			 	if(!(element.text().contains(",")))
			     valeurs.add(element.text());	
		 }
		 for (int i = 1; i < valeurs.size(); i++) {
			hm.put(devises.get(i).toString(),Float.parseFloat(valeurs.get(i).toString()));
		}
		return hm;
	}

	@Override
	public double convertisseur(String de, String a, double quantite) throws IOException {
		if(quantite<=0) {
			return 0;
		}
		org.jsoup.nodes.Document convertissuer = Jsoup.connect("https://www.boursorama.com/bourse/devises/convertisseur-devises/"+de+"-"+a).timeout(20000).get();
		String s=convertissuer.getElementsByClass("c-table__cell c-table__cell--dotted").get(1).text();
		String[] val1=s.split(" ");
		double coef = Double.parseDouble(val1[0]);
		return coef*quantite;
	}
	
	
}
