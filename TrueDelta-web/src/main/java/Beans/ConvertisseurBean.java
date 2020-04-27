package Beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="convertisseurBean")
@SessionScoped
public class ConvertisseurBean implements Serializable {
	private String v="hello";

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}
	
}
