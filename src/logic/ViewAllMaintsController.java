package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import models.Document;
import models.Maintenance;
import models.carDAO;
import sun.applet.Main;
import logic.ViewMaintsController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewAllMaintsController {

    @FXML
    private GridPane grid;

    @FXML
    private Button exitMaintButton;

    private String placa;

    private List<Maintenance> maints;
    private int row;
    private List<Maintenance> pMaints;
    private ViewMaintsController controller;
    private Long actualKm;

    @FXML
    void exitMaint(ActionEvent event) {
        if(!this.maints.equals(this.pMaints)){
            this.controller.updateMaints(this.placa);
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void setMaints(String placa, List<Maintenance> maints, ViewMaintsController controller, Long km){
        this.placa=placa;
        this.controller=controller;
        this.maints=maints;
        this.pMaints=new ArrayList<>(this.maints);
        this.actualKm=km;
        try{
            for(int i=0;i<maints.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneMaintCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneMaintCardController controller1=fxmlLoader.getController();
                controller1.setData(maints.get(i),placa,this,this.actualKm);
                grid.add(anchorPane,0,this.row++);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                if (maints.size()==1)
                    GridPane.setMargin(anchorPane,new Insets(15,25,10,25));
                else
                    GridPane.setMargin(anchorPane,new Insets(15,0,10,20));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteMaint(Maintenance maint) throws IOException {
        this.maints.remove(maint);
    }

    public void updateMaints(String placa){
        grid.getChildren().clear();
        try{
            for(int i=0;i<maints.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneMaintCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneMaintCardController controller=fxmlLoader.getController();
                controller.setData(maints.get(i),placa,this,this.actualKm);

                grid.add(anchorPane, 0, this.row++);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                if (maints.size()==1)
                    GridPane.setMargin(anchorPane,new Insets(15,25,10,25));
                else
                    GridPane.setMargin(anchorPane,new Insets(15,0,10,20));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
