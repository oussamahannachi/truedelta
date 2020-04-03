package entities;

import java.io.Serializable;
import java.lang.Thread.State;
import java.util.Date;

import javax.persistence.*;

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
	@Enumerated(EnumType.STRING)
	private ReclamationState state;
	private String subject;
	private String description;
	@Column(name = "assignmentDate")
	private Date assignmentDate;
	@Column(name = "closingDate")
	private Date closingDate;
	
	
	@ManyToOne
	private Utilisateur user;
	
	public Reclamation() {}

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

	
	public void setDescription(String description) {
		this.description = description;
	}

	

	

	

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

	

	public ReclamationState getState() {
		return state;
	}

	public void setState(ReclamationState state) {
		this.state = state;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
	
	
	

	public Reclamation(int id, Date dateCreation, ReclamationState state, String subject, String description,
			Date assignmentDate, Date closingDate, Utilisateur user) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.state = state;
		this.subject = subject;
		this.description = description;
		this.assignmentDate = assignmentDate;
		this.closingDate = closingDate;
		this.user = user;
	}
	public Reclamation(int id, Date dateCreation, ReclamationState state, String subject, String description) {
		super();
		this.id = id;
		this.dateCreation = dateCreation;
		this.state = state;
		this.subject = subject;
		this.description = description;
		
	}
	public Reclamation( Date dateCreation, ReclamationState state, String subject, String description) {
		super();
		this.dateCreation = dateCreation;
		this.state = state;
		this.subject = subject;
		this.description = description;
		
	}

	@Override
	public String toString() {
		return "Reclamation [id=" + id + ", dateCreation=" + dateCreation + ", state=" + state + ", subject=" + subject
				+ ", description=" + description + ", assignmentDate=" + assignmentDate + ", closingDate=" + closingDate
				+ ", user=" + user + "]";
	}

	public String getDescription() {
		return description;
	}



	
	
}
