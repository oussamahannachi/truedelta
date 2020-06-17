package entities;

import entities.Utilisateur;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Courtier
 *
 */
@Entity
@DiscriminatorValue(value="courtier")
public class Courtier extends Utilisateur implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String grade;
	private int experience;
	private float score;
	private int nbPF ; // Nombre portefeuilles
	
	
	@OneToMany(mappedBy="courtier", cascade = CascadeType.ALL)
	private List<Procuration> procurations= new ArrayList<Procuration>();
	
	public Courtier() {
		super();
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	

	
	public int getNbPF() {
		return nbPF;
	}

	public void setNbPF(int nbPF) {
		this.nbPF = nbPF;
	}

	public List<Procuration> getProcurations() {
		return procurations;
	}

	public void setProcurations(List<Procuration> procurations) {
		this.procurations = procurations;
	}

	@Override
	public String toString() {
		return "Courtier [grade=" + grade + ", score=" + score + "]";
	}	
}
