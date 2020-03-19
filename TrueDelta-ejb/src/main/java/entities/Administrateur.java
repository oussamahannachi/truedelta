package entities;

import entities.Utilisateur;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Administrateur
 *
 */
@Entity
@DiscriminatorValue(value="admin")
public class Administrateur extends Utilisateur implements Serializable {

	private static final long serialVersionUID = 1L;
	private int pin;

	public Administrateur() {
		super();
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Administrateur [pin=" + pin + "]";
	}
   
}
