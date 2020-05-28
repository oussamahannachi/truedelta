package Beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import entities.Administrateur;
import entities.Agence;
import entities.Client;
import entities.Utilisateur;
import services.UserService;;

@ManagedBean(name="loginBean",eager=true)
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String login; 
	private String password; 
	private Utilisateur user;
	private Agence agence;
	private Boolean loggedIn;
	private String role;
	private List<String> roles = Arrays.asList("Client", "Courtier", "Administrateur", "Agence");
	
	@EJB
	UserService us;
	
	public LoginBean() {}
	
	public String doLogin() {
		System.out.println(login);
		System.out.println(password);
		String navigateTo = "null";
		user = us.loginUser(login, password);
		agence = us.loginAgence(login, password);
		if (user != null ) {
			if(user.getClass() != Administrateur.class && role.equals(user.getClass().getSimpleName())) {
			navigateTo = "/template/template?faces-redirect=true";
			loggedIn = true;
			} 
			else 
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Verifier votre role"));
		}
		else if(agence != null && role.toLowerCase().equals("agence")){
			navigateTo = "/template/template?faces-redirect=true";
			loggedIn = true;
		}
		else {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Bad Credentials"));
		}
		return navigateTo;
	}
		
	public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	public Agence getAgence() {
		return agence;
	}

	public void setAgence(Agence agence) {
		this.agence = agence;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
