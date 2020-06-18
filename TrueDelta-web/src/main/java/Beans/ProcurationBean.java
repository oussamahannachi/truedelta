package Beans;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.chartistjsf.model.chart.AspectRatio;
import org.chartistjsf.model.chart.Axis;
import org.chartistjsf.model.chart.AxisType;
import org.chartistjsf.model.chart.BarChartModel;
import org.chartistjsf.model.chart.BarChartSeries;
import org.chartistjsf.model.chart.PieChartModel;
import org.primefaces.event.ItemSelectEvent;

import com.itextpdf.text.DocumentException;

import javax.ejb.Schedule;
import entities.Client;
import entities.Compte;
import entities.Courtier;
import entities.Procuration;
import entities.ProcurationPK;
import entities.Type;
import services.ProcurationServiceImpl;

@ManagedBean(name = "procurationBean")
@SessionScoped
public class ProcurationBean {
	private static final long serialVersionUID = 1L;

	@EJB
	ProcurationServiceImpl procurationService;
	
	HashMap<Integer, String> topcourtier = new HashMap<>();
	private int id;
	private int idc;
	private int numero;
	private Type type;
	private Date dateCreation;
	private String dateTraitement;
	private String etat;
	private int score;
	private String avisContrat;
	private float gain;
	private String description;
	private Client client;
	private Courtier courtier;
	private int choix = 0;
	private String typep;
	private Procuration procuration_modif;
	private Procuration procuration_supp;
	public List<Procuration> procuration_client = new ArrayList<Procuration>();
	public List<Procuration> procurations = new ArrayList<Procuration>();
	public List<Procuration> procuration_courtier = new ArrayList<Procuration>();
	int size;
	int sizec;
	private String descriptionmodif;
	private String ErreurAjoutProcuration = "";
	private String ErreurIndex = "";
	private String Courtier = "";
	private PieChartModel pieChartModel = new PieChartModel();
	private BarChartModel barChartModel = new BarChartModel();
	private int nbnt;
	private int nbt;
	private int nbec;
	
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean lb;
	

	public ProcurationServiceImpl getProcurationService() {
		return procurationService;
	}

	public void setProcurationService(ProcurationServiceImpl procurationService) {
		this.procurationService = procurationService;
	}

	public LoginBean getLb() {
		return lb;
	}

	public void setLb(LoginBean lb) {
		this.lb = lb;
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public void setBarChartModel(BarChartModel barChartModel) {
		this.barChartModel = barChartModel;
	}

	public int getNbnt() {
		return nbnt;
	}

	public void setNbnt(int nbnt) {
		this.nbnt = nbnt;
	}

	public int getNbt() {
		return nbt;
	}

	public HashMap<Integer, String> getTopcourtier() {
		return topcourtier;
	}

	public void setTopcourtier(HashMap<Integer, String> topcourtier) {
		this.topcourtier = topcourtier;
	}

	public void setNbt(int nbt) {
		this.nbt = nbt;
	}

	public int getNbec() {
		return nbec;
	}

	public void setNbec(int nbec) {
		this.nbec = nbec;
	}

	public PieChartModel getPieChartModel() {
		return pieChartModel;
	}

	public void setPieChartModel(PieChartModel pieChartModel) {
		this.pieChartModel = pieChartModel;
	}

	public List<Procuration> getProcurations() {
		return procurations;
	}

	public void setProcurations(List<Procuration> procurations) {
		this.procurations = procurations;
	}

	public List<Procuration> getProcuration_courtier() {
		return procuration_courtier;
	}

	public void setProcuration_courtier(List<Procuration> procuration_courtier) {
		this.procuration_courtier = procuration_courtier;
	}

	public void setCourtier(String courtier) {
		Courtier = courtier;
	}

	public int getSizec() {
		return sizec;
	}

	public void setSizec(int sizec) {
		this.sizec = sizec;
	}

	public String getErreurIndex() {
		return ErreurIndex;
	}

	public void setErreurIndex(String erreurIndex) {
		ErreurIndex = erreurIndex;
	}

	public String getErreurAjoutProcuration() {
		return ErreurAjoutProcuration;
	}

	public void setErreurAjoutProcuration(String erreurAjoutProcuration) {
		ErreurAjoutProcuration = erreurAjoutProcuration;
	}

	public Procuration getProcuration_supp() {
		return procuration_supp;
	}

	public void setProcuration_supp(Procuration procuration_supp) {
		this.procuration_supp = procuration_supp;
	}

	public String getDescriptionmodif() {
		return descriptionmodif;
	}

	public void setDescriptionmodif(String descriptionmodif) {
		this.descriptionmodif = descriptionmodif;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIdc() {
		return idc;
	}

	public void setIdc(int idc) {
		this.idc = idc;
	}

	public List<Procuration> getProcuration_client() {
		return procuration_client;
	}

	public void setProcuration_client(List<Procuration> procuration_client) {
		this.procuration_client = procuration_client;
	}

	public Procuration getProcuration_modif() {
		return procuration_modif;
	}

	public void setProcuration_modif(Procuration procuration_modif) {
		this.procuration_modif = procuration_modif;
	}

	public String getTypep() {
		return typep;
	}

	public void setTypep(String typep) {
		this.typep = typep;
	}

	public int getChoix() {
		return choix;
	}

	public void setChoix(int choix) {
		this.choix = choix;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(String dateTraitement) {
		this.dateTraitement = dateTraitement;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getAvisContrat() {
		return avisContrat;
	}

	public void setAvisContrat(String avisContrat) {
		this.avisContrat = avisContrat;
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		this.gain = gain;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Courtier getCourtier() {
		return courtier;
	}

	public void setCourtier(Courtier courtier) {
		this.courtier = courtier;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void CurrentUser() {
		procuration_client = procurationService.getProcByClient(lb.getUser().getId());
		procuration_courtier = procurationService.getProcByCourtier(4);
	}

	public String Mondat() {
		choix = 1;
		typep = "Mondat";
		return "addprocuration?faces-redirect=true";
	}

	public String Specifique() {
		choix = 2;
		typep = "Specifique";
		return "addprocuration?faces-redirect=true";
	}

	public String Proposition() {
		choix = 3;
		typep = "Proposition";
		return "addprocuration?faces-redirect=true";
	}

	public String AddProcuration() throws ParseException, MalformedURLException, IOException, DocumentException {
		int idclient = lb.getUser().getId();
		
		String retour="";
		Type type = null;
		if (choix == 1) {
			type = Type.mondat;

		}
		if (choix == 2) {
			type = Type.specifie;

		}
		if (choix == 3) {
			type = Type.proposition;

		}
		
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh" + idclient);
		int idcr = 4;
		ProcurationPK pk = new ProcurationPK(idclient, idcr);
		Client client=procurationService.getEm().find(Client.class, idclient);
		
		System.out.println(pk.getIdClient() + pk.getIdCourtier());
		Procuration p = new Procuration();
		p.setId(pk);
		p.setDescription(description);

		p.setType(type);

		Date d = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("" + dateTraitement.toString());
		if (dateTraitement.isEmpty()) {
			p.setDateTraitement(null);

		} else {
			Date d1 = dateFormat.parse(dateTraitement);
			p.setDateTraitement(d1);

		}

		p.setDateCreation(d);
		if (procurationService.VerifierExistanceProc(idclient, idcr) == 1) {
			retour = "indexprocuration?faces-redirect=true";

			ErreurIndex = "Vous avez déja une procuration avec ce courtier";

		} else {
          try {
        	 procurationService.ajouterProcuration(p);
  			System.out.println("succes");
  			System.out.println("username :"+client.getUsername());
  			procurationService.AjouterContrat(p.getNumero(), client);

  			procurationService.calculgain(p.getId());

  			procurationService.etatProc(p.getId());
  			choix = 0;
  			retour = "myprocuration?faces-redirect=true";
          }
          catch(Exception e)
          {
        	  retour = "indexprocuration?faces-redirect=true";

  			ErreurIndex = "Vous avez déja une procuration avec ce courtier";
 
          }
			

		}

		procuration_client = procurationService.getProcByClient(idclient);
		size = procuration_client.size();
		return retour;

	}

	public List<Procuration> ProcurationClient() {

		procuration_client = procurationService.getProcByClient(lb.getUser().getId());
		size = procuration_client.size();

		return procuration_client;
	}

	public String GoToModifier(Procuration e) {
		procuration_modif = e;
		descriptionmodif = procuration_modif.getDescription();
		return "modifierprocuration?faces-redirect=true";
	}

	public String SupprimerProcuration(Procuration e) {
		procuration_supp = e;
		procurationService.deleteProc(e.getId());
		procuration_client = procurationService.getProcByClient(idc);
		size = procuration_client.size();
		ErreurAjoutProcuration = "";
		return "myprocuration?faces-redirect=true";
	}

	public String ModifierProcuration() {

		procurationService.modifdescrip(procuration_modif.getId(), descriptionmodif);
		return "myprocuration?faces-redirect=true";
	}

	public String GoToProcurations() {
		return "myprocuration?faces-redirect=true";
	}

	public String GoToIndex() {
		return "indexprocuration?faces-redirect=true";
	}

	public String GoToAddProcuration() {
		String retour = "";
		int r = procurationService.NbCourtier();
		if (size == r) {
			retour = "myprocuration?faces-redirect=true";
			ErreurAjoutProcuration = "Vous avez atteint le nombre maximale de procurations ";

		} else {
			ErreurAjoutProcuration = "";
			retour = "indexprocuration?faces-redirect=true";
		}
		return retour;

	}

	public String Procurations() {

		String retour = "";

		procuration_client = procurationService.getProcByClient(lb.getUser().getId());
		size = procuration_client.size();
		if (size == 0) {

			retour = "indexprocuration?faces-redirect=true";
		} else {
			retour = "myprocuration?faces-redirect=true";
		}

		return retour;
	}

	public List<Procuration> ProcurationCourtier() {

		procuration_courtier = procurationService.getProcByCourtier(4);
		sizec = procuration_courtier.size();
		return procuration_courtier;

	}

	public String ValiderProcuration(Procuration p) {
		Date d = new Date();

		procurationService.modifetat(p.getId(), "Traité");
		procurationService.modifDateTraitement(p.getId(), d);
		return "procurationCourtier?faces-redirect=true";
	}

	public String EtatColor(String etat) {
		String retour;

		if (etat.equals("non traité")) {
			retour = "N";
		} else if (etat.equals("bloqué")) {
			retour = "N";
		} else {
			retour = "T";
		}

		return retour;
	}

	public String VerifType() {
		String retour;

		if (choix == 1) {
			retour = "M";
		} else {
			retour = "E";
		}

		return retour;
	}

	public String DateFinVerify(Date d) {
		String retour;

		if (d == null) {
			retour = "N";
		} else {
			retour = "V";
		}

		return retour;
	}

	public List<Procuration> GetAllProcurations() {

		procurations = procurationService.findAllProc();
		return procurations;
	}

	public PieChartModel createPieChart() {

		pieChartModel = new PieChartModel();
		pieChartModel.addLabel("Traité");
		pieChartModel.addLabel("Non traité");
          int n1=procurationService.CalculerNb("non traité");
          int n2=procurationService.CalculerNb("Traité");
		pieChartModel.set(n1);
		pieChartModel.set(n2);

		pieChartModel.setShowTooltip(true);
		pieChartModel.setDonut(true);
		return pieChartModel;

	}

	public TreeMap<Integer, String> Topcourtier() {

		for (Courtier a : procurationService.getCouriters()) {

			int e = procurationService.NombreProcurationTraiteParCourtier(a.getId());

			String name = a.getNom() + " " + a.getPrenom();
			topcourtier.put(e, name);
		}
		TreeMap<Integer, String> sorted = new TreeMap<>(Collections.reverseOrder());
		sorted.putAll(topcourtier);

		return sorted;
	}

	public double totalgain() {

		double b = procurationService.totalgain();
		double d = (double) Math.round(b * 100) / 100; 
		return d;
	}

	public String PlusRentable() {
		return procurationService.TypeDeContratRentable();
	}

	public BarChartModel createBarChart() {
		barChartModel = new BarChartModel();
		barChartModel.setAspectRatio(AspectRatio.GOLDEN_SECTION);

		for (Procuration p : procurationService.findAllProc()) {
			barChartModel.addLabel(p.getClient().getNom() + p.getClient().getPrenom() + "(" + p.getType() + ")");
		}

		BarChartSeries series1 = new BarChartSeries();
		series1.setName("Score");
		for (Procuration p : procurationService.findAllProc()) {
			series1.set(p.getScore());
		}

		Axis xAxis = barChartModel.getAxis(AxisType.X);
		xAxis.setShowGrid(false);
		barChartModel.addSeries(series1);

		barChartModel.setShowTooltip(true);
		barChartModel.setSeriesBarDistance(15);
		barChartModel.setAnimateAdvanced(true);
		return barChartModel;

	}

}