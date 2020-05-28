package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import services.CompteService;

@ManagedBean(name = "convertisseurBean")
@SessionScoped
public class ConvertisseurBean implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String de;
	private String a;
	private double montant;
	
	private List<String> devises = Arrays.asList("euro", "dollar", "dinartunisien", "dollarcanadien", "yen",
			"francsuisse", "dirham", "livresterling", "rouble", "baht", "dollaraustralien", "rialduqatar", "riyal");

	private List<String> noms = Arrays.asList("EUR/USD", "USD/JPY", "GBP/USD","AUD/USD","NZD/USD","EUR/JPY","GBP/JPY",
			"EUR/GBP","EUR/CAD","EUR/SEK","EUR/CHF","EUR/HUF","EUR/JPY",
			"USD/CNY","USD/HKD", "USD/SGD","USD/INR","USD/MXN","USD/PHP","USD/THB","USD/MYR","USD/ZAR","USD/RUB");

	private List<String> taux;

	private double valeur;

	@EJB
	CompteService cs;

	public ConvertisseurBean(){}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public List<String> getDevises() {
		return devises;
	}

	public void setDevises(List<String> devises) {
		this.devises = devises;
	}

	public List<String> getNoms() {
		return noms;
	}

	public void setNoms(List<String> noms) {
		this.noms = noms;
	}

	public double getValeur() {
		return valeur;
	}

	public List<String> getTaux() throws IOException{
		Document webPage = Jsoup.connect("https://finance.yahoo.com/currencies").timeout(60000).get();
		Elements val = webPage.getElementsByClass("data-col2");
		List<String> list = new ArrayList<String>();
		for (int i=2;i<val.size();i++) {
			if(!(val.get(i).text().contains(",")))
			list.add(val.get(i).text());
		}
		return list;
	}

	public void setTaux(List<String> taux) {
		this.taux = taux;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public void doConvertisseur() throws IOException {
		valeur = cs.convertisseur(de, a, montant);
	}
	
}
