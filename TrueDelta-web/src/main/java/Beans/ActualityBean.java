package Beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import services.UserService;

@ManagedBean(name = "actualityBean")
@SessionScoped
public class ActualityBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<String> names;
	private List<String> values;
	private Map<String, String> hm = new HashMap<>();
	private int size = hm.size();

	private List<String> dates = Arrays.asList("09:11", "10:31", "10:30", "10:23", "09:20", "09:14", "09:06", "09:01",
			"09:56", "09:45", "09:37", "09:31", "09:16");
	private List<String> actu;

	@EJB
	UserService us;

	public ActualityBean() {
	}

	public List<String> getActu() throws IOException {
		Document webPage = Jsoup.connect("https://www.boursorama.com/bourse/actualites/finances").timeout(60000).get();
		Elements val = webPage.getElementsByClass("c-list-news__content");
		List<String> list = new ArrayList<String>();
		for (int i = 2; i < val.size(); i++) {
			if (!(val.get(i).text().contains(",")))
				list.add(val.get(i).text());
		}
		System.out.println(list);
		return list;
	}

	public Map<String, String> getActuality() throws IOException {
		Document doc = Jsoup.connect("https://www.boursorama.com/bourse/actualites/finances/").timeout(60000).get();
		Elements name = doc.getElementsByClass("c-list-news__date");
		Elements value = doc.getElementsByClass("c-list-news__content");

		for (Element elem : name) {
			names.add(elem.text());
		}
		for (Element elem : value) {
			values.add(elem.text());

		}
		for (int i = 0; i < values.size(); i++) {

			hm.put(names.get(i).toString(), values.get(i).toString());

		}
		return hm;
	}

	public Map<String, String> lastNews(String recherche) throws IOException {
		// System.out.println(us.ShowStocks().stream().collect(Collectors.toList()));

		return (Map<String, String>) ((Collection<String>) us.ShowStocks()).stream().collect(Collectors.toList());

	}

	public Map<String, String> getHm() {
		return hm;
	}

	public void setHm(Map<String, String> hm) {
		this.hm = hm;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public UserService getUs() {
		return us;
	}

	public void setUs(UserService us) {
		this.us = us;
	}

	public List<String> getDates() {
		return dates;
	}

	public void setDates(List<String> dates) {
		this.dates = dates;
	}

	public void setActu(List<String> actu) {
		this.actu = actu;
	}

}
