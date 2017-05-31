/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annonsklient;

/**
 *
 * @author lceo
 */
public class Advertisement {
    private String annonsid;
    private String platsannonsUrl;
    private String annonsrubrik;
    private String annonstext;

    public Advertisement() {
    }

    public String getAnnonsid() {
        return annonsid;
    }

    public void setAnnonsid(String annonsid) {
        this.annonsid = annonsid;
    }

    public String getPlatsannonsUrl() {
        return platsannonsUrl;
    }

    public void setPlatsannonsUrl(String platsannonsUrl) {
        this.platsannonsUrl = platsannonsUrl;
    }

    public String getAnnonsrubrik() {
        return annonsrubrik;
    }

    public void setAnnonsrubrik(String annonsrubrik) {
        this.annonsrubrik = annonsrubrik;
    }

    public String getAnnonstext() {
        return annonstext;
    }

    public void setAnnonstext(String annonstext) {
        this.annonstext = annonstext;
    }
    
}
