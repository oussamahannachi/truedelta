package entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Agence")
public class Agence implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="agenceID")
	private int id;

	private String banqueName; 
	
	@Column(name="codeBIC")
	private String code_BIC; // Code associé à chaque banque banque exp ATB : ATBKTNTT
	
	@Column(name="codeBCT")
	private int code_BCT; // Numero associé à chaque agence exp ATB hedi nouira 28
	
	private String agenceName;
	private String adresse; 
	private String siegeSocial; // Adresse
	@Column(nullable=true)
	private long telephone;
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dernierenvoi ;
	
	@OneToMany(mappedBy="agence", cascade = { CascadeType.PERSIST,CascadeType.REMOVE }, fetch=FetchType.EAGER)
	private List<Compte> comptes= new ArrayList<Compte>();
	
	@Transient
	private String confirmPassword;

	public Agence() {}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getBanqueName() { return banqueName; }

	public void setBanqueName(String banqueName) { this.banqueName = banqueName; }

	public String getCode_BIC() { return code_BIC; }

	public void setCode_BIC(String code_BIC) { this.code_BIC = code_BIC; }

	public int getCode_BCT() { return code_BCT; }

	public void setCode_BCT(int code_BCT) { this.code_BCT = code_BCT; }

	public String getAgenceName() { return agenceName; }

	public void setAgenceName(String agenceName) { this.agenceName = agenceName; }

	public String getAdresse() { return adresse; }

	public void setAdresse(String adresse) { this.adresse = adresse; }

	public String getSiegeSocial() { return siegeSocial; }

	public void setSiegeSocial(String siegeSocial) { this.siegeSocial = siegeSocial; }

	public long getTelephone() { return telephone; }

	public void setTelephone(long telephone) { this.telephone = telephone; }
	
	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }
	
	public String getConfirmPassword() { return confirmPassword; }

	public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

	public Date getDernierenvoi() {
		return dernierenvoi;
	}

	public void setDernierenvoi(Date dernierenvoi) {
		this.dernierenvoi = dernierenvoi;
	}

	public List<Compte> getComptes() { return comptes; }

	public void setComptes(List<Compte> comptes) { this.comptes = comptes; }
	
}
