

package annonsklient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class XMLConsume {
    
    public XMLConsume(){}

    public ArrayList<Lan> getLanList(){
        ArrayList<Lan> lanList = parseLanList(connect("http://api.arbetsformedlingen.se/af/v0/platsannonser/soklista/lan"));
        return lanList;
    }
    public ArrayList<Ad> getAdList(String lanId, String keyword){
        try {
            ArrayList<Ad> adList = parseAdList(connect("http://api.arbetsformedlingen.se/af/v0/platsannonser/matchning?antalrader=1000&lanid=" + lanId + "&nyckelord=" + URLEncoder.encode(keyword, "UTF-8")));
            return adList;
        }
        catch (Exception e) {
        }
        return null;
    }
    public Advertisement getAdvertisement(String annonsId){
        Advertisement adv = parseAdvertisement(connect("http://api.arbetsformedlingen.se/af/v0/platsannonser/" + annonsId));
        return adv;
    }
    public Document connect(String urlStr){
        
        HttpURLConnection conn;

        Document doc = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/xml");
            conn.setRequestProperty("Accept-Language", "sv");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }
            InputStream inStream = conn.getInputStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inStream);
            doc.getDocumentElement().normalize();
            
            conn.disconnect();                
                
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
           pce.printStackTrace();
        }
        return doc;
    }

    public ArrayList<Ad> parseAdList(Document doc){
	
        ArrayList<Ad> adList = new ArrayList<Ad>();
        NodeList matchList = doc.getElementsByTagName("matchningdata");

        for (int i=0; i < matchList.getLength(); i++){                                
            NodeList match = matchList.item(i).getChildNodes();
            Ad ad = new Ad();                                
            for (int j=0; j < match.getLength(); j++){            
                Node node = match.item(j);
                if(!node.getNodeName().equals(null) && !node.getNodeName().equals("#text")){
                    switch(node.getNodeName()){                            
                        case "annonsid":
                            ad.setAnnonsid(node.getTextContent());
                            break;
                        case "annonsrubrik":
                            ad.setAnnonsrubrik(node.getTextContent());
                            break;
                        case "yrkesbenamning":
                            ad.setYrkesbenamning(node.getTextContent());
                            break;
                        case "yrkesbenamningId":
                            ad.setYrkesbenamningId(node.getTextContent());
                            break;
                        case "arbetsplatsnamn":
                            ad.setArbetsplatsnamn(node.getTextContent());
                            break;
                        case "kommunnamn":
                            ad.setKommunnamn(node.getTextContent());
                            break;
                        case "kommunkod":
                            if(!node.getTextContent().equals("")){
                                ad.setKommunkod(Integer.parseInt(node.getTextContent()));
                            }
                            else {
                                ad.setKommunkod(0);                            
                            }
                            break;
                        case "publiceraddatum":                                
                            if(!node.getTextContent().equals("")){
                                ad.setPubliceraddatum(LocalDateTime.parse(node.getTextContent(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                            }
                            break;
                        case "sista_ansokningsdag":
                            if(!node.getTextContent().equals("")){
                                ad.setSista_ansokningsdag(LocalDateTime.parse(node.getTextContent(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
                            }
                            break;
                        case "annonsurl":
                            ad.setAnnonsurl(node.getTextContent());
                            break;
                        case "relevans":
                            if(!node.getTextContent().equals("")){
                                ad.setRelevans(Integer.parseInt(node.getTextContent()));
                            }
                            else {
                                ad.setRelevans(0);                            
                            }
                            break;
                        case "antalplatser":
                            if(!node.getTextContent().equals("")){
                                ad.setAntalplatser(Integer.parseInt(node.getTextContent()));
                            }
                            else {
                                ad.setAntalplatser(0);
                            }
                            break;
                        case "antalPlatserVisa":
                            if(!node.getTextContent().equals("")){
                                ad.setAntalPlatserVisa(Integer.parseInt(node.getTextContent()));
                            }
                            else {
                                ad.setAntalPlatserVisa(0);
                            }
                            break;
                        case "varaktighetId":
                            ad.setVaraktighetId(node.getTextContent());
                            break;
                        case "lanid":
                            if(!node.getTextContent().equals("")){
                                ad.setLanid(Integer.parseInt(node.getTextContent()));
                            }
                            else {
                                ad.setLanid(0);
                            }
                            break;
                        case "lan":
                            ad.setLan(node.getTextContent());
                            break;
                        case "anstallningstyp":
                            ad.setAnstallningstyp(node.getTextContent());
                            break;
                    }
                }
            }
            adList.add(ad);
        }
        System.out.println("Number of ads: " + adList.size());
        return adList;
    }      
    
    public ArrayList<Lan> parseLanList(Document doc){

        ArrayList<Lan> lanList = new ArrayList<Lan>();
        NodeList matchList = doc.getElementsByTagName("sokdata");

        for (int i=0; i < matchList.getLength(); i++){                                
            NodeList match = matchList.item(i).getChildNodes();
            Lan lan = new Lan();                                
            for (int j=0; j < match.getLength(); j++){            
                Node node = match.item(j);
                if(!node.getNodeName().equals(null) && !node.getNodeName().equals("#text")){
                    switch(node.getNodeName()){                            
                        case "id":
                            lan.id = Integer.parseInt(node.getTextContent());
                            break;
                        case "namn":
                            lan.name = node.getTextContent();
                            break;
                    }
                }
            }
            lanList.add(lan);
        }
        System.out.println("Antal län: " + lanList.size());
        return lanList;
    }          
    
     public Advertisement parseAdvertisement(Document doc){
        Advertisement adv = new Advertisement();        

        Node rootNode = doc.getDocumentElement();
        NodeList advCats = rootNode.getChildNodes();
        for (int a=0; a < advCats.getLength(); a++){
            if (advCats.item(a).getNodeName().equals("annons")){
                NodeList annons = advCats.item(a).getChildNodes();
                for (int i=0; i < annons.getLength(); i++){
                    switch(annons.item(i).getNodeName()){                            
                        case "annonsid":
                            adv.setAnnonsid(annons.item(i).getTextContent());
                            break;
                        case "platsannonsUrl":
                            adv.setPlatsannonsUrl(annons.item(i).getTextContent());
                            break;
                        case "annonsrubrik":
                            adv.setAnnonsrubrik(annons.item(i).getTextContent());
                            break;
                        case "annonstext":
                            adv.setAnnonstext(annons.item(i).getTextContent());
                            break;                            
                    }                                        
                }                                
            }
        }                                
        return adv;
    }             
    
    

}
