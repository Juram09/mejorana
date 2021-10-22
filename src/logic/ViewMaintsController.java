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
import logic.*;
import models.Document;
import models.Maintenance;
import models.carDAO;
import sun.applet.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewMaintsController {

    @FXML
    private GridPane grid;

    @FXML
    private Button exitMaintButton;

    @FXML
    private Button viewAllBtn;

    @FXML
    private Button addMaintBtn;

    private String placa;
    private List<Maintenance> maints;
    private List<Maintenance> pMaints;
    private Long actualKm;
    private int row;

    @FXML
    void exitMaint(ActionEvent event) {
        if(this.maints.equals(this.pMaints)){
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Actualización de datos");
            alert.setHeaderText(null);
            alert.setContentText("¿Desea confirmar los cambios efectuados en los documentos?");
            ButtonType buttonTypeYes = new ButtonType("Sí");
            ButtonType buttonTypeNo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
            Optional<ButtonType> result=alert.showAndWait();
            if(result.get()==buttonTypeYes){
                updateMaints();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }else if(result.get()==buttonTypeNo){
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        }
    }

    @FXML
    void goAddMaint(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/addMaint.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 750, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        AddMaintController controller=fxmlLoader.getController();
        controller.setCar(this.placa, this);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    void goViewAllMaints(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/viewAllMaints.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 535, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        ViewAllMaintsController controller=fxmlLoader.getController();
        controller.setMaints(this.placa, this.maints,this,this.actualKm);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void setMaints(String placa, Long km){
        this.placa=placa;
        carDAO dao=new carDAO();
        this.maints=dao.getMaints(placa);
        this.pMaints= new ArrayList<>(this.maints);
        this.actualKm=km;
        try{
            for(int i=0;i<maints.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneMaintCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                if(maints.get(i).isActive()){
                    OneMaintCardController controller=fxmlLoader.getController();
                    controller.setData(maints.get(i),placa,this,this.actualKm);
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMaint(Maintenance maintenance) throws IOException {
        for(int i=0;i<maints.size();i++){
            if(maints.get(i).getTipo().equals(maintenance.getTipo()) && maints.get(i).isActive()){
                Maintenance maint=new Maintenance(maints.get(i));
                this.maints.add(maint);
                maint.setActive(false);
                this.maints.remove(i);
                break;
            }
        }
        this.maints.add(maintenance);
    }

    public void deleteMaint(Maintenance maintenance) throws IOException {
        this.maints.remove(maintenance);
    }

    public void updateMaints(String placa){
        grid.getChildren().clear();
        try{
            for(int i=0;i<maints.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneMaintCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                if(maints.get(i).isActive()){
                    OneMaintCardController controller = fxmlLoader.getController();
                    controller.setData(maints.get(i), placa, this,this.actualKm);

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
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateMaints(){
        carDAO dao=new carDAO();
        for(int i=0;i<this.pMaints.size();i++){
            boolean updated=false;
            for(int j=0;j<this.maints.size();j++){
                System.out.println(pMaints.get(i).getId()+", "+maints.get(j).getId());
                if(pMaints.get(i).equals(maints.get(j))){
                    updated=true;
                    break;
                }else if(pMaints.get(i).getId().equals(maints.get(j).getId()) && !(pMaints.get(i).isActive()==(maints.get(j).isActive()))){
                    updated=true;
                    dao.updateMaint(maints.get(j));
                    break;
                }
            }
            if(!updated){
                dao.deleteMaint(this.pMaints.get(i).getId());
            }
        }
        for(int i=0;i<this.maints.size();i++){
            System.out.println(i);
            boolean updated=false;
            for(int j=0;j<this.pMaints.size();j++){
                if(maints.get(i).equals(pMaints.get(j))){
                    updated=true;
                    break;
                }else if(pMaints.get(j).getId().equals(maints.get(i).getId()) && (pMaints.get(j).isActive()!=(maints.get(i).isActive()))){
                    updated=true;
                    break;
                }
            }
            if(!updated){
                System.out.println(maints.get(i).getKm());
                System.out.println(maints.get(i).getTipo());
                System.out.println(maints.get(i).getFecha());
                System.out.println(maints.get(i).getDescripcion());
                System.out.println(maints.get(i).getKm());
                System.out.println(maints.get(i).getKm());
                dao.insertMaint(this.maints.get(i),this.placa);
            }
        }
    }
}
