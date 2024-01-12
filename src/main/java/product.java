import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonProperty;
@XmlRootElement
public class product {
	
	@JsonProperty("code")
	private int code;
	
	@JsonProperty("lib")
	private String lib;
	
	@JsonProperty("prix")
	private double prix;
	
	@JsonProperty("quantite")
	private int quantite;
	
	@JsonProperty("fkcategorie")
	private int fkcategorie;


    public product() {
    }

    public product(int code, String lib, double prix, int quantite, int fkcategorie) {
        this.code = code;
        this.lib = lib;
        this.prix = prix;
        this.quantite = quantite;
        this.fkcategorie = fkcategorie;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getFkcategorie() {
        return fkcategorie;
    }

    public void setFkcategorie(int fkcategorie) {
        this.fkcategorie = fkcategorie;
    }
}