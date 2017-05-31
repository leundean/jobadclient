

package annonsklient;

import java.time.LocalDateTime;

public class Ad {

    private String annonsid;
    private String annonsrubrik;
    private String yrkesbenamning;
    private String yrkesbenamningId;
    private String arbetsplatsnamn;
    private String kommunnamn;
    private int kommunkod;
    private LocalDateTime publiceraddatum;
    private LocalDateTime sista_ansokningsdag;
    private String annonsurl;
    private int relevans;
    private int antalplatser;
    private int antalPlatserVisa;
    private String varaktighetId;
    private int lanid;
    private String lan;
    private String anstallningstyp;

    public Ad(){}

    public String getAnnonsid() {
        return annonsid;
    }

    public void setAnnonsid(String annonsid) {
        this.annonsid = annonsid;
    }

    public String getAnnonsrubrik() {
        return annonsrubrik;
    }

    public void setAnnonsrubrik(String annonsrubrik) {
        this.annonsrubrik = annonsrubrik;
    }

    public String getYrkesbenamning() {
        return yrkesbenamning;
    }

    public void setYrkesbenamning(String yrkesbenamning) {
        this.yrkesbenamning = yrkesbenamning;
    }

    public String getYrkesbenamningId() {
        return yrkesbenamningId;
    }

    public void setYrkesbenamningId(String yrkesbenamningId) {
        this.yrkesbenamningId = yrkesbenamningId;
    }

    public String getArbetsplatsnamn() {
        return arbetsplatsnamn;
    }

    public void setArbetsplatsnamn(String arbetsplatsnamn) {
        this.arbetsplatsnamn = arbetsplatsnamn;
    }

    public String getKommunnamn() {
        return kommunnamn;
    }

    public void setKommunnamn(String kommunnamn) {
        this.kommunnamn = kommunnamn;
    }

    public int getKommunkod() {
        return kommunkod;
    }

    public void setKommunkod(int kommunkod) {
        this.kommunkod = kommunkod;
    }

    public LocalDateTime getPubliceraddatum() {
        return publiceraddatum;
    }

    public void setPubliceraddatum(LocalDateTime publiceraddatum) {
        this.publiceraddatum = publiceraddatum;
    }

    public LocalDateTime getSista_ansokningsdag() {
        return sista_ansokningsdag;
    }

    public void setSista_ansokningsdag(LocalDateTime sista_ansokningsdag) {
        this.sista_ansokningsdag = sista_ansokningsdag;
    }

    public String getAnnonsurl() {
        return annonsurl;
    }

    public void setAnnonsurl(String annonsurl) {
        this.annonsurl = annonsurl;
    }

    public int getRelevans() {
        return relevans;
    }

    public void setRelevans(int relevans) {
        this.relevans = relevans;
    }

    public int getAntalplatser() {
        return antalplatser;
    }

    public void setAntalplatser(int antalplatser) {
        this.antalplatser = antalplatser;
    }

    public int getAntalPlatserVisa() {
        return antalPlatserVisa;
    }

    public void setAntalPlatserVisa(int antalPlatserVisa) {
        this.antalPlatserVisa = antalPlatserVisa;
    }

    public String getVaraktighetId() {
        return varaktighetId;
    }

    public void setVaraktighetId(String varaktighetId) {
        this.varaktighetId = varaktighetId;
    }

    public int getLanid() {
        return lanid;
    }

    public void setLanid(int lanid) {
        this.lanid = lanid;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getAnstallningstyp() {
        return anstallningstyp;
    }

    public void setAnstallningstyp(String anstallningstyp) {
        this.anstallningstyp = anstallningstyp;
    }

    
            
}
