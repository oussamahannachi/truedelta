package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Procuration
 *
 */
@Entity
public class Procuration implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProcurationPK id;
	
	@Column(name="numero")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int numero;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@Column(name="date_creation")
	private Date dateCreation;
	
	@Column(nullable=true)
	private Date dateTraitement;
	
	@Column(nullable=true)
	private String etat;
	
	@Column(nullable=true)
	private int score;
	
	@Column(nullable=true)
	private String avisContrat;
	
	@Column(nullable=true)
	private float gain; 
	
	@Column(nullable=true)
	private String description;
	
	@Column(nullable=true)
	private String url_contrat;
	
	
	@ManyToOne
	@JoinColumn(name="idClient", referencedColumnName="utilisateurID", insertable=false , updatable=false, unique=false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="idCourtier", referencedColumnName="utilisateurID", insertable=false , updatable=false,unique=false)
	private Courtier courtier;
	
	public Procuration() {}
	
	public Procuration(ProcurationPK id, int numero, Type type, Date dateCreation, Date dateTraitement, String etat,
			int score, String avisContrat, float gain, String description, String url_contrat, Client client,
			Courtier courtier) {
		super();
		this.id = id;
		this.numero = numero;
		this.type = type;
		this.dateCreation = dateCreation;
		this.dateTraitement = dateTraitement;
		this.etat = etat;
		this.score = score;
		this.avisContrat = avisContrat;
		this.gain = gain;
		this.description = description;
		this.url_contrat = url_contrat;
		this.client = client;
		this.courtier = courtier;
	}

	public ProcurationPK getId() {
		return id;
	}

	public void setId(ProcurationPK id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public Date getDateTraitement() {
		return dateTraitement;
	}

	public void setDateTraitement(Date dateTraitement) {
		this.dateTraitement = dateTraitement;
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

	public String getUrl_contrat() {
		return url_contrat;
	}

	public void setUrl_contrat(String url_contrat) {
		this.url_contrat = url_contrat;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Procuration [ id :"+id+" type :"+type+"]";
	}
   
}
