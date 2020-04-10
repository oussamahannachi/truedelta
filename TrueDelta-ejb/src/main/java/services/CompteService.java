package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.*;

import entities.Compte;
import interfaces.CompteServiceLocal;
import interfaces.CompteServiceRemote;

@Stateful
public class CompteService implements CompteServiceLocal, CompteServiceRemote {

	@PersistenceContext(unitName="truedelta-ejb")
	EntityManager em;
	
	MongoClient con = new MongoClient("localhost",27017);
	MongoDatabase db = con.getDatabase("truedelta");
	Document doc = new Document();
	
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
		return em.createQuery("select c from compte c where c.numero= ? ", Compte.class)
				 .setParameter(1, num)
				 .getSingleResult();
	}

	@Override
	public List<Compte> getAllCompte() {
		return em.createQuery("select c from compte c", Compte.class)
				 .getResultList();
	}
	
	@Override
	public List<Compte> gelAllCompteByAgence(int id) {
		return  em.createQuery("select c from compte c where idAgence=? and isVerifie = ?", Compte.class)
							  .setParameter(1, id)
							  .setParameter(2, true)
							  .getResultList();
	}

	@Override
	public void autoriseCompte(long num) {
		Compte c = getCompteByNumero(num);
		c.setIsAutorise(true);
	}

	@Override
	public void verifierCompte(long num) {
		Compte c = getCompteByNumero(num);
		c.setIsVerifie(true);
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
	public boolean verifierCollection(String nom) {
		MongoIterable<String> names = db.listCollectionNames();
		MongoCursor<String> cursor = names.cursor(); 
		while(cursor.hasNext()) {
			if (cursor.next().equals(nom))
				return true;
		}
		return false;
	}

	//Creation d'un doc à partir d'un compte
	@Override
	public Document createDocByCompte(Compte c) {
		return doc.append("numero",c.getNumero())
				  .append("devise", c.getDevise().toString())
				  .append("solde", c.getSolde())
				  .append("actions", c.getNbAction())
				  .append("obligations", c.getNbObligation())
				  .append("source", "truedelta");
	}

	//Insertion des doc à partir l'id d'un banque
 	@Override
	public void docByTrueDelta(int id) {
		List<Compte> comptes = gelAllCompteByAgence(id);
		if(!(comptes.isEmpty()))
		{
			for (Compte compte : comptes) {
				String nom = (compte.getAgence().getBanqueName()+compte.getAgence().getAgenceName()).toLowerCase();
				if(!(verifierCollection(nom))) { 
						db.createCollection(nom);
						db.getCollection(nom).insertOne(createDocByCompte(compte));
				}
				else
					db.getCollection(nom).insertOne(createDocByCompte(compte));
			}
		}
	}

 	//Excel service
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
	
}
