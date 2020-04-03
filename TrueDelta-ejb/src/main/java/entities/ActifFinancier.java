package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;



/**
 * Entity implementation class for Entity: ActifFinancier
 *
 */
@Entity
@Table(name="actif_financier")
public class ActifFinancier implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actifID")
	private int id;
	private String entreprise;
	private float prix;
	private float rendement;
	private float risque;
	private String type; // action ou obligation 
	private float interet;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date_echeane",nullable=true)
	private Date dateEcheance; // Pour obligation
	
	@ManyToOne()
	private Compte compte;
	
	@OneToOne(mappedBy="actif")
	private Transaction transaction;
	
	 private BigDecimal HighPrice  ;
	 private BigDecimal LowPrice;
	 private BigDecimal	ClosedPrice ;
	 private BigDecimal	OpenPrice ;
	 private BigDecimal	AdjClose ;
	 private String Currency;
	
	 @ManyToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "Company_Symbol", referencedColumnName = "Symbol")
	 private Company Company;
	 
	public ActifFinancier() {}
	

	public ActifFinancier(String entreprise, float prix, float rendement, float risque, String type,
			float interet, Date dateEcheance, Compte compte, Transaction transaction, BigDecimal highPrice,
			BigDecimal lowPrice, BigDecimal closedPrice, String currency, entities.Company company) {
		super();
		this.entreprise = entreprise;
		this.prix = prix;
		this.rendement = rendement;
		this.risque = risque;
		this.type = type;
		this.interet = interet;
		this.dateEcheance = dateEcheance;
		this.compte = compte;
		this.transaction = transaction;
		HighPrice = highPrice;
		LowPrice = lowPrice;
		ClosedPrice = closedPrice;
		Currency = currency;
		Company = company;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public float getRendement() {
		return rendement;
	}

	public void setRendement(float rendement) {
		this.rendement = rendement;
	}

	public float getRisque() {
		return risque;
	}

	public void setRisque(float risque) {
		this.risque = risque;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getInteret() {
		return interet;
	}

	public void setInteret(float interet) {
		this.interet = interet;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	

	public BigDecimal getHighPrice() {
		return HighPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		HighPrice = highPrice;
	}

	public BigDecimal getLowPrice() {
		return LowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		LowPrice = lowPrice;
	}

	public BigDecimal getClosedPrice() {
		return ClosedPrice;
	}

	public void setClosedPrice(BigDecimal closedPrice) {
		ClosedPrice = closedPrice;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public Company getCompany() {
		return Company;
	}

	public void setCompany(Company company) {
		Company = company;
	}


	@Override
	public String toString() {
		return "ActifFinancier [id=" + id + ", entreprise=" + entreprise + ", prix=" + prix + ", rendement=" + rendement
				+ ", risque=" + risque + ", type=" + type + ", interet=" + interet + ", dateEcheance=" + dateEcheance
				+ ", compte=" + compte + ", transaction=" + transaction + ", HighPrice=" + HighPrice + ", LowPrice="
				+ LowPrice + ", ClosedPrice=" + ClosedPrice + ", Currency=" + Currency + ", Company=" + Company +"\n" +"]";
	}


	public BigDecimal getOpenPrice() {
		return OpenPrice;
	}


	public void setOpenPrice(BigDecimal openPrice) {
		OpenPrice = openPrice;
	}


	public BigDecimal getAdjClose() {
		return AdjClose;
	}


	public void setAdjClose(BigDecimal adjClose) {
		AdjClose = adjClose;
	}



	
	
}
