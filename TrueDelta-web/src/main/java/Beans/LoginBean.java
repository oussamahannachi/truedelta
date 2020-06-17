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
import services.UserService;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String email ;
	private String password ;
	private Utilisateur user; 
	private boolean loggedin;
	private Agence agence;
	private String role;
	private List<String> roles = Arrays.asList("Client", "Courtier", "Administrateur", "Agence");


	@EJB
	UserService us;
	
	public LoginBean() {}


	public String Login() {
			
			String navigateTo = "null";
			FacesContext context = FacesContext.getCurrentInstance();
			user = us.verifierLogin(email, password);
			agence=us.verifierLoginB(email, password);
			if (user != null) {
				loggedin = true;
				if(user.getClass()==Administrateur.class) {
					role="Administrateur";
					navigateTo = "/template/templateadmin?faces-redirect=true";
				}
				else {
					if(user.getClass()==Client.class) {
						role="Client";
					}
					else {
						role="Courtier";
					}
				context.getExternalContext().getSessionMap().put("user", user);
				navigateTo = "/template/template?faces-redirect=true";
				}
			}
			else if(agence != null ){
				System.out.println("ahlaaaaaaaaa");
				role="Agence";
				loggedin = true;
				context.getExternalContext().getSessionMap().put("agence", agence);
				navigateTo = "/template/template?faces-redirect=true";
			}
			else {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Adresse email ou mot de passe incorrecte"));
			}
			return navigateTo; 
		}
	 
	    
	    public String doLogout() {
	    	FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	    	loggedin = false;
	    	return "/pages/login?faces-redirect=true"; 
	    	}

		

		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
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

		public boolean isLoggedin() {
			return loggedin;
		}

		public void setLoggedin(boolean loggedin) {
			this.loggedin = loggedin;
		}

		public Agence getAgence() {
			return agence;
		}

		public void setAgence(Agence agence) {
			this.agence = agence;
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