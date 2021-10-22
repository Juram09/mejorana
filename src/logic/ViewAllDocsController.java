package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Document;
import models.carDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewAllDocsController {

    @FXML
    private GridPane grid;

    @FXML
    private Button exitDocButton;

    private String placa;

    private List<Document> docs;
    private int row;
    private List<Document> pDocs;
    private ViewDocsController controller;

    @FXML
    void exitDoc(ActionEvent event) {
        if(!this.docs.equals(this.pDocs)){
            this.controller.updateDocs(this.placa);
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    void setDocs(String placa, List<Document> docs,ViewDocsController controller){
        this.placa=placa;
        this.controller=controller;
        this.docs=docs;
        organize(docs);
        this.pDocs=new ArrayList<>(this.docs);
        try{
            for(int i=0;i<docs.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneDocCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneDocCardController controller1=fxmlLoader.getController();
                controller1.setData(docs.get(i),placa,this);
                grid.add(anchorPane,0,this.row++);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDoc(Document document) throws IOException {
        this.docs.remove(document);
    }

    private void organize(List<Document> docs){
        Document aux;
        for(int i=0;i<docs.size();i++){
            if (docs.get(i).getName().equalsIgnoreCase("tarjeta de propiedad") && docs.get(i).isActive()){
                aux=docs.get(0);
                docs.set(0,docs.get(i));
                docs.set(i,aux);
            }else if(docs.get(i).getName().equalsIgnoreCase("soat") && docs.get(i).isActive()){
                aux=docs.get(1);
                docs.set(1,docs.get(i));
                docs.set(i,aux);
            }else if(docs.get(i).getName().equalsIgnoreCase("tecnomecanica") && docs.get(i).isActive()){
                aux=docs.get(2);
                docs.set(2,docs.get(i));
                docs.set(i,aux);
            }
        }
    }

    public void updateDocs(String placa){
        grid.getChildren().clear();
        organize(this.docs);
        try{
            for(int i=0;i<docs.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneDocCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneDocCardController controller = fxmlLoader.getController();
                controller.setData(docs.get(i), placa, this);

                grid.add(anchorPane, 0, this.row++);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
