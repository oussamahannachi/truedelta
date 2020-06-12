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
import implementation.UserService;;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String login; 
	private String password; 
	private Utilisateur user;
	
	private Boolean loggedIn = false;
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
		
		if (user != null ) {
			System.out.println(user.getClass());
			if(user.getClass() != Administrateur.class ) {
				if(role.equals(user.getClass().getSimpleName())) {
					navigateTo = "/template/template?faces-redirect=true";
					loggedIn = true;
				} 
				else 
					FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Verifier votre role"));
			}
			else {
				if(role.equals(user.getClass().getSimpleName())) {
					navigateTo = "/template/templateadmin?faces-redirect=true";
					loggedIn = true;
				} 
				else 
					FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Verifier votre role"));
				
			}
		}
		
		else {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Bad Credentials"));
		}
		return navigateTo;
	}
		
	public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "../login?faces-redirect=true";
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
	
	public String connect() {
		
		String navigateTo = "null";
		user = us.loginUser(login, password);
		
		
				if(role.equals("Client")) {
					navigateTo = "/template/template?faces-redirect=true";
					loggedIn = true;
				} 
				
			else {
				if(role.equals("Administrateur")) {
					navigateTo = "/template/templateadmin?faces-redirect=true";
					loggedIn = true;
				} 
				else 
					navigateTo = "/pages/courtier?faces-redirect=true";
		}
		return navigateTo;
	}
}
