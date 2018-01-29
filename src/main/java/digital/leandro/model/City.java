package digital.leandro.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="City")
public class City {
	
	@Id
	private long ibgeid;
	
	private String uf;
	private String name;
	private Boolean capital;
	private double lon;
	private double lat;
	private String no_accents;
	private String alternative_names;
	private String microregion;
	private String mesoregion;
	
	
	public City() {
	}

	public City(long ibgeid, String uf, String name, Boolean capital, double lon, double lat, String no_accents,
			String alternative_names, String microregion, String mesoregion) {
		super();
		this.ibgeid = ibgeid;
		this.uf = uf;
		this.name = name;
		this.capital = capital;
		this.lon = lon;
		this.lat = lat;
		this.no_accents = no_accents;
		this.alternative_names = alternative_names;
		this.microregion = microregion;
		this.mesoregion = mesoregion;
	}
	
	public long getIbgeid() {
		return ibgeid;
	}
	public void setIbgeid(long ibgeId) {
		this.ibgeid = ibgeId;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getCapital() {
		return capital;
	}
	public void setCapital(Boolean capital) {
		this.capital = capital;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getNo_accents() {
		return no_accents;
	}
	public void setNo_accents(String no_accents) {
		this.no_accents = no_accents;
	}
	public String getAlternative_names() {
		return alternative_names;
	}
	public void setAlternative_names(String alternative_names) {
		this.alternative_names = alternative_names;
	}
	public String getMicroregion() {
		return microregion;
	}
	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}
	public String getMesoregion() {
		return mesoregion;
	}
	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}
}
