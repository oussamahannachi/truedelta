package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProcurationPK
 *
 */
@Embeddable
public class ProcurationPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private int idClient;
	private int idCourtier;
	
	public ProcurationPK() {}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public int getIdCourtier() {
		return idCourtier;
	}

	public void setIdCourtier(int idCourtier) {
		this.idCourtier = idCourtier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idClient;
		result = prime * result + idCourtier;
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
		ProcurationPK other = (ProcurationPK) obj;
		if (idClient != other.idClient)
			return false;
		if (idCourtier != other.idCourtier)
			return false;
		return true;
	}  
	
}
