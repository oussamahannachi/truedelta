package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="utilisateur")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="role")
public class Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="utilisateurID")
	private int id;
	
	@Column(unique=true,nullable=false)
	private String email;

	@Column(unique=true,nullable=false)
	private String username;
	
	private String nom;
	private String prenom;
	@Column(nullable=true)
	private String adresse;
	@Column(nullable=true)
	private String telephone;
	private String password;
	@Transient 
	private String confirmPassword;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Reclamation> reclamations;

	public Utilisateur() {}
	
	public Utilisateur(String username) {
		super();
		this.username = username;
	}
	
	public Utilisateur(String email, String username, String nom, String prenom, String adresse, String password,
			String confirmPassword) {
		super();
		this.email = email;
		this.username = username;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", email=" + email + ", username=" + username + "]";
	}

	public Set<Reclamation> getReclamations() {
		return reclamations;
	}

	public void setReclamations(Set<Reclamation> reclamations) {
		this.reclamations = reclamations;
	}
	
   
}
