package digital.leandro.dto;

public class UFCityCount {
	private String uf;
	private int cityCount;
	private String ufCountStatus;
	
	public UFCityCount(String uf, int cityCount, String ufCountStatus) {
		super();
		this.uf = uf;
		this.cityCount = cityCount;
		this.ufCountStatus = ufCountStatus;
	}
	
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public int getCityCount() {
		return cityCount;
	}
	public void setCityCount(int cityCount) {
		this.cityCount = cityCount;
	}
	public String getUfCountStatus() {
		return ufCountStatus;
	}
	public void setUfCountStatus(String ufCountStatus) {
		this.ufCountStatus = ufCountStatus;
	}
}
