package Beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import services.CompteService;

@ManagedBean(name="fichierBean",eager=true)
@ViewScoped
public class FichierBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UploadedFile file;
	private int etat=5;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean lb;
	
	@EJB
	CompteService cs;
	
	public FichierBean() {}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	public String dummyAction() throws IOException {

			if(file.getSize()==0) {
				setEtat(4);
				return "";
			}
			else if(!(file.getFileName().contains("xls"))) {
				setEtat(0);
				return "";
			}
			else {
			LocalDateTime now = LocalDateTime.now();
			int id=lb.getAgence().getId();
			String path = "C:\\Users\\OUSSAMA HANNACHI\\Desktop\\Uploads\\"+lb.getAgence().getBanqueName().toUpperCase()+"\\";
		    String name = lb.getAgence().getAgenceName()+now.getDayOfMonth()+now.getMonthValue()+".";
			
	        
			InputStream inputStream = file.getInputStream();
	        OutputStream outputStream = null;
	        if(file.getFileName().endsWith(".xlsx")){
	        	name = name.concat("xlsx");
	        }
	        
	        else if(file.getFileName().endsWith(".xls")){
	        	name = name.concat("xls");
	        }
	        
	        File f = new File(path + name);
	        outputStream = new FileOutputStream(f);

	        int read = 0;
	        byte[] bytes = new byte[1024];

	        while ((read = inputStream.read(bytes)) != -1) {
	            outputStream.write(bytes, 0, read);
	        }
	    	setEtat(cs.isValide(f));
	    	if(etat==3) {
	    		cs.exceltoMongoDB(f, id); //id banque
	    		outputStream.close();
	    		return "accueil?faces-redirect=true";
	    	}
	    	outputStream.close();
	    	Path fileToDeletePath = Paths.get(f.getAbsolutePath());
	        System.out.println(fileToDeletePath);
	        Files.delete(fileToDeletePath);
	    	return "";
		}
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public LoginBean getLb() {
		return lb;
	}

	public void setLb(LoginBean lb) {
		this.lb = lb;
	}
	
}
