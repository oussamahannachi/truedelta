package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ComptePK
 *
 */
@Embeddable
public class ComptePK implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idAgence;
	private int idClient;
	
	public ComptePK() {}

	public int getIdBanque() {
		return idAgence;
	}

	public void setIdBanque(int idBanque) {
		this.idAgence = idBanque;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idAgence;
		result = prime * result + idClient;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComptePK other = (ComptePK) obj;
		if (idAgence != other.idAgence)
			return false;
		if (idClient != other.idClient)
			return false;
		return true;
	}

	
}
