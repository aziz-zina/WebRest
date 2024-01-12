import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;

@XmlRootElement
public class categorie {
	
	@JsonProperty("code")
	private int code;
	
	@JsonProperty("lib")
    private String lib;


    public categorie() {
    }

    public categorie(int code, String lib) {
        this.code = code;
        this.lib = lib;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }
}