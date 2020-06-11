package entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;








/**
 * Entity implementation class for Entity: Reclamation
 *
 */
@Entity

@Table(name="reclamation") 
public class Reclamation implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reclamationID")
	private int id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreation;
	private String description;
	@Column(name="response")
	private String response ;
	private String subject;
	@Column(name="STATE")
    @Enumerated(EnumType.STRING)
    private State state;
	@Column(name = "assignmentDate")
    private Date assignmentDate;
    @Column(name = "closingDate")
    private Date closingDate;
	

	public Reclamation(int id, Date dateCreation, String etat, String titre, String description, String subject,
			State state, Date assignmentDate, Date closingDate, Utilisateur user) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		
		this.description = description;
		this.subject = subject;
		this.state = state;
		this.assignmentDate = assignmentDate;
		this.closingDate = closingDate;
		this.user = user;
	}
	
	public Reclamation( Date dateCreation, String description, String subject,
			State state, Date assignmentDate, Date closingDate, Utilisateur user) {
		super();
		
		this.dateCreation = dateCreation;
		this.description = description;
		this.subject = subject;
		this.state = state;
		this.assignmentDate = assignmentDate;
		this.closingDate = closingDate;
		this.user = user;
	}
	
	public Reclamation(int id, Date dateCreation,State state, String description, String subject
			) {
		super();
	
		this.id = id;
		this.dateCreation = dateCreation;
		this.state = state;
		this.description = description;
		this.subject = subject;
		
		
	}
	
	

	
	@ManyToOne
	@JsonBackReference
	private Utilisateur user;
	
	public Reclamation() {}

	

	public Reclamation(Date dateCreation,State state, String description, String subject) {
		super();
		this.dateCreation = dateCreation;
		
		this.description = description;
		this.subject = subject;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(Date assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Reclamation [dateCreation=" + dateCreation  + "]";
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Reclamation(int id, Date dateCreation, String description, String response, String subject, State state,
			Date assignmentDate, Date closingDate, Utilisateur user) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.description = description;
		this.response = response;
		this.subject = subject;
		this.state = state;
		this.assignmentDate = assignmentDate;
		this.closingDate = closingDate;
		this.user = user;
	}

	public Reclamation(String description, String subject) {
		super();
		this.description = description;
		this.subject = subject;
	}

	public Reclamation(Date dateCreation, String description, String subject) {
		super();
		this.dateCreation = dateCreation;
		this.description = description;
		this.subject = subject;
	}
	
	public Reclamation( Integer id ,String description, String subject,Date dateCreation,State state) {
		
		this.id=id;
		this.dateCreation = dateCreation;
		this.description = description;
		this.subject = subject;
		this.state=state;	}
	
	
	
	
}
