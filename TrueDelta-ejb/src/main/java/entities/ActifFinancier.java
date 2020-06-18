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
	@Enumerated(EnumType.STRING)
	private TypeActif type; // action ou obligation 
	private float interet;
	@Temporal(TemporalType.DATE)
	@Column(name="date",nullable=true)
	private Date date;	
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
	 @ManyToOne(cascade = CascadeType.DETACH)
	//@JoinColumn(name = "Company_Symbol", referencedColumnName = "Symbol")
	private Company Company;	 
	@Temporal(TemporalType.DATE)
	@Column(name="date_echeane",nullable=true)
	private Date dateEcheance; // Pour obligation
	private BigDecimal BondPrice  ;
	private BigDecimal Parvalue;
	private BigDecimal Tauxcoupon ;
	private int Duree;
	private BigDecimal TauxActuariel;
	private int Fréquence;
	@Column(nullable=true)
	private String  Categorie;
	@Column(name="date_emission",nullable=true)
	private Date DateEmission; // Pour obligation
	@Column(name="date_dernierCoupon",nullable=true)
	private Date DatedernierCoupon; // Pour obligation
	@Column(name="date_prochaincoupon",nullable=true)
	private Date DateprochainCoupon; // Pour obligation
	 public ActifFinancier() {}
	
	public ActifFinancier(String entreprise, float prix, float rendement, float risque, TypeActif type,
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



	public TypeActif getType() {
		return type;
	}

	public void setType(TypeActif type) {
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


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//@Override
	public String GetStock() {
		return "ActifFinancier [id=" + id + ", entreprise=" + entreprise + ", prix=" + prix + ", rendement=" + rendement
				+ ", risque=" + risque + ", type=" + type + ", interet=" + interet + ", date=" + date + ", compte="
				+ compte + ", transaction=" + transaction + ", HighPrice=" + HighPrice + ", LowPrice=" + LowPrice
				+ ", ClosedPrice=" + ClosedPrice + ", OpenPrice=" + OpenPrice + ", AdjClose=" + AdjClose + ", Currency="
				+ Currency + ", Company=" + Company + ", dateEcheance=" + dateEcheance  +"]" + "\n";
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

	public BigDecimal getBondPrice() {
		return BondPrice;
	}

	public void setBondPrice(BigDecimal bondPrice) {
		BondPrice = bondPrice;
	}

	public BigDecimal getParvalue() {
		return Parvalue;
	}

	public void setParvalue(BigDecimal parvalue) {
		Parvalue = parvalue;
	}



	public int getDuree() {
		return Duree;
	}

	public void setDuree(int duree) {
		Duree = duree;
	}

	public BigDecimal getTauxActuariel() {
		return TauxActuariel;
	}

	public void setTauxActuariel(BigDecimal tauxActuariel) {
		TauxActuariel = tauxActuariel;
	}

	
	public BigDecimal getTauxcoupon() {
		return Tauxcoupon;
	}

	public void setTauxcoupon(BigDecimal tauxcoupon) {
		Tauxcoupon = tauxcoupon;
	}

	public int getFréquence() {
		return Fréquence;
	}

	public void setFréquence(int fréquence) {
		Fréquence = fréquence;
	}
	

	public String getCategorie() {
		return Categorie;
	}

	public void setCategorie(String categorie) {
		Categorie = categorie;
	}

	public Date getDateEmission() {
		return DateEmission;
	}

	public void setDateEmission(Date dateEmission) {
		DateEmission = dateEmission;
	}

	public Date getDatedernierCoupon() {
		return DatedernierCoupon;
	}

	public void setDatedernierCoupon(Date datedernierCoupon) {
		DatedernierCoupon = datedernierCoupon;
	}

	public Date getDateprochainCoupon() {
		return DateprochainCoupon;
	}

	public void setDateprochainCoupon(Date dateprochainCoupon) {
		DateprochainCoupon = dateprochainCoupon;
	}

	//@Override
	public String GetBonds() {
		return "ActifFinancier [id=" + id + ", type=" + type + ", date=" + date + ", compte=" + compte
				+ ", transaction=" + transaction + ", Currency=" + Currency + ", Company=" + Company + ", dateEcheance="
				+ dateEcheance + ", BondPrice=" + BondPrice + ", Parvalue=" + Parvalue + ", Tauxcoupon=" + Tauxcoupon
				+ ", Duree=" + Duree + ", TauxActuariel=" + TauxActuariel  + ", Fréquence=" + Fréquence + ", Categorie=" + Categorie + ", DateEmission="
						+ DateEmission + ", DatedernierCoupon=" + DatedernierCoupon + ", DateprochainCoupon="
						+ DateprochainCoupon + "]"+ "\n";
	}

	@Override
	public String toString() {
		return "ActifFinancier [id=" + id + ", entreprise=" + entreprise + ", prix=" + prix + ", rendement=" + rendement
				+ ", risque=" + risque + ", type=" + type + ", interet=" + interet + ", date=" + date + ", compte="
				+ compte + ", transaction=" + transaction + ", HighPrice=" + HighPrice + ", LowPrice=" + LowPrice
				+ ", ClosedPrice=" + ClosedPrice + ", OpenPrice=" + OpenPrice + ", AdjClose=" + AdjClose + ", Currency="
				+ Currency + ", Company=" + Company + ", dateEcheance=" + dateEcheance + ", BondPrice=" + BondPrice
				+ ", Parvalue=" + Parvalue + ", Tauxcoupon=" + Tauxcoupon + ", Duree=" + Duree + ", TauxActuariel="
				+ TauxActuariel + ", Fréquence=" + Fréquence + ", Categorie=" + Categorie + ", DateEmission="
				+ DateEmission + ", DatedernierCoupon=" + DatedernierCoupon + ", DateprochainCoupon="
				+ DateprochainCoupon + "]";
	}




	
	

	
	
}
