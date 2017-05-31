
package annonsklient;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;



public class FXMLDocumentController implements Initializable {
    
    private ArrayList<TreeItem<AdInfo>> tltAds = new ArrayList<TreeItem<AdInfo>>();    
    private XMLConsume xmlConsume = new XMLConsume();
    private ArrayList<Lan> lanList = xmlConsume.getLanList();
    private ArrayList<Ad> ads = new ArrayList<Ad>();

    @FXML
    private ChoiceBox lanListBox;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private WebView mapView;

    @FXML
    private TextField searchBox;

    @FXML
    private Label annonsRubrik;
    
    @FXML
    private Label annonsText;

    @FXML
    private WebView webAdv;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String lanIdString = "90";
        for (Lan lan: lanList){
            if (lan.name.equals(lanListBox.getSelectionModel().getSelectedItem())){
                lanIdString = "" + lan.id;
                System.out.println("SEARCHING: " + lan.name);
            }
        }
        ads.clear();
        tltAds.clear();
        ads = xmlConsume.getAdList(lanIdString, searchBox.getText());        
        buildTreeTable();
    }
    
    @FXML
    private TreeTableView<AdInfo> tlt;
    
    private TreeItem<AdInfo> root = new TreeItem<>(new AdInfo("rootName","rootValue"));        

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#eeeeee"), CornerRadii.EMPTY, Insets.EMPTY)));
        ObservableList<String> choices = FXCollections.observableArrayList();
        choices.add("Välj län");
        for (Lan lan: lanList) {
          choices.add(lan.name);
        }
        
        WebEngine mapEng = mapView.getEngine();
        mapEng.getLoadWorker().stateProperty().addListener(
            (ObservableValue<? extends State> ov, State oldState, 
            State newState) -> {
                if (newState == State.SUCCEEDED) {
                    JSObject win = (JSObject) mapEng.executeScript("window");
                    win.setMember("app", new JSInterfaceApp());
                }
        });

        // Add local path/URL
        mapEng.load("sv_map.html");

            
        lanListBox.setItems(choices);
        lanListBox.getSelectionModel().selectFirst();

        tlt.setRoot(root);
        root.setExpanded(true);
        tlt.setShowRoot(false);

    }
    public class JSInterfaceApp {
        public void selectLan(String lanId) {
            for (Lan lan: lanList) {
                if (lan.id == Integer.parseInt(lanId)){
                   lanListBox.getSelectionModel().select(lan.name);                    
                }
            }
        }
    }  

    public void buildTreeTable(){
        tlt.getRoot().getChildren().clear();
        
        TreeTableColumn<AdInfo, String> tlt_left = new TreeTableColumn<>("Annons");
        tlt_left.setPrefWidth(225);
        tlt_left.setCellValueFactory((
                TreeTableColumn.CellDataFeatures<AdInfo, String> param) -> new ReadOnlyStringWrapper(
                param.getValue().getValue().getInfoName()));

        TreeTableColumn<AdInfo, String> tlt_right = new TreeTableColumn<>("Info");
        tlt_right.setPrefWidth(175);
        tlt_right.setCellValueFactory((
        TreeTableColumn.CellDataFeatures<AdInfo, String> param) -> new ReadOnlyStringWrapper(
        param.getValue().getValue().getInfoValue()));

        tlt.getColumns().setAll(tlt_left, tlt_right);

        if(ads.isEmpty()){
            root.getChildren().add(new TreeItem<>(new AdInfo("Inga matchningar", "")));
        }
        else {
            for (int i=0; i < ads.size(); i++){
                tltAds.add(new TreeItem<>(new AdInfo(ads.get(i).getAnnonsrubrik(), 
                                                    ads.get(i).getAnnonsid())));            
            }
            for (int i=0; i < tltAds.size(); i++){
                root.getChildren().add(tltAds.get(i));            
                tltAds.get(i).getChildren().add(new TreeItem<>(
                                        new AdInfo("Annonsör", ads.get(i).getArbetsplatsnamn())));
                try {
                    tltAds.get(i).getChildren().add(new TreeItem<>(
                                            new AdInfo("Kommun", ads.get(i).getKommunnamn())));
                
                } catch(NullPointerException npe){
                    
                }
                try {
                    tltAds.get(i).getChildren().add(new TreeItem<>(
                                            new AdInfo("Ansök senast", ads.get(i).getSista_ansokningsdag().format(DateTimeFormatter.ISO_DATE))));
                
                } catch(NullPointerException npe){
                    
                }
            }
            tlt.setOnMouseClicked(openAd);
        }
    }    
    private EventHandler<MouseEvent> openAd = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent){            
            TreeItem<AdInfo> item = (TreeItem) tlt.getSelectionModel().getSelectedItem();
            if(item != null){
                if(mouseEvent.getClickCount() == 1){
                    if(item.isExpanded()){
                        item.setExpanded(false);
                    }
                    else {
                        item.setExpanded(true);
                    }
                }
                if(mouseEvent.getClickCount() == 2)
                {
                    String adId = "";
                    if (item.getParent().equals(tlt.getRoot())){
                        adId = item.getValue().getInfoValue();
                    }
                    else {
                        adId = item.getParent().getValue().getInfoValue();                
                    }
                    Advertisement adv = xmlConsume.getAdvertisement(adId);

                    annonsRubrik.setText(adv.getAnnonsrubrik());
                    annonsText.setText(adv.getAnnonstext());
                    webAdv.getEngine().load(adv.getPlatsannonsUrl());
                }
            }
        }
    }; 
}
