import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class GithubResponse {
	
	private String name;
	private String site;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
}
