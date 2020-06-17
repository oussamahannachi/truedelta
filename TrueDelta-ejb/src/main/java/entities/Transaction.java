package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Transaction
 *
 */
@Entity
@Table(name="transaction")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transactionID")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String type; // Achat ou vente
    private float rendementTransaction ;
    private Boolean isValide;
    private long numcompte ;
    private int scoreTransaction;


	
	


	@OneToOne
	private ActifFinancier actif;
	
	public Transaction() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    public long getNumcompte() {
		return numcompte;
	}

	public void setNumcompte(long numcompte) {
		this.numcompte = numcompte;
	}



	public int getScoreTransaction() {
		return scoreTransaction;
	}

	public void setScoreTransaction(int scoreTransaction) {
		this.scoreTransaction = scoreTransaction;
	}
	public Date getDate() {
		return date;
	}
	

	public void setDate(Date date) {
		this.date = date;
	}
	


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ActifFinancier getActif() {
		return actif;
	}

	public void setActif(ActifFinancier actif) {
		this.actif = actif;
	}

	public float getRendementTransaction() {
		return rendementTransaction;
	}

	public void setRendementTransaction(float rendementTransaction) {
		this.rendementTransaction = rendementTransaction;
	}
	public Boolean getIsValide() {
		return isValide;
	}

	public void setIsValide(Boolean isValide) {
		this.isValide = isValide;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", type=" + type + ", rendementTransaction="
				+ rendementTransaction + ", isValide=" + isValide + ", numcompte=" + numcompte
				+ ", scoreTransaction=" + scoreTransaction + ", actif=" + actif + "]";
	}

	

	

	
	

	
	}
