package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="ReclamStatistics")

public class ReclamStatistics implements Serializable{
	
	


		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "StatisticsId")
		private int id;
		
		@Column(name = "NbinprogressReclam")
		private float NbinprogressReclam;
		@Column(name = "NbOpenedReclam")
		private float NbOpenedReclam;
		@Column(name = "NbTreatedReclam")
		private float NbTreatedReclam;	
		@Column(name = "NbReclams")
		private int NbReclams;
		@Column(name = "DateStat")
		private Date DateStat;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public float getNbinprogressReclam() {
			return NbinprogressReclam;
		}
		public void setNbinprogressReclam(int nbinprogressReclam) {
			NbinprogressReclam = nbinprogressReclam;
		}
		public float getNbOpenedReclam() {
			return NbOpenedReclam;
		}
		public void setNbOpenedReclam(int nbOpenedReclam) {
			NbOpenedReclam = nbOpenedReclam;
		}
		public float getNbTreatedReclam() {
			return NbTreatedReclam;
		}
		public void setNbTreatedReclam(int nbTreatedReclam) {
			NbTreatedReclam = nbTreatedReclam;
		}
		public int getNbReclams() {
			return NbReclams;
		}
		public void setNbReclams(int nbReclams) {
			NbReclams = nbReclams;
		}
		public Date getDateStat() {
			return DateStat;
		}
		public void setDateStat(Date dateStat) {
			DateStat = dateStat;
		}
		public ReclamStatistics(int id, int nbinprogressReclam, int nbOpenedReclam, int nbTreatedReclam, int nbReclams,
				Date dateStat) {
			super();
			this.id = id;
			NbinprogressReclam = nbinprogressReclam;
			NbOpenedReclam = nbOpenedReclam;
			NbTreatedReclam = nbTreatedReclam;
			NbReclams = nbReclams;
			DateStat = dateStat;
		}
		public ReclamStatistics() {
			super();
		}
		
	
		
		
		
		
		

}
