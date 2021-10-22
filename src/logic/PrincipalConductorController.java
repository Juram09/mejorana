package logic;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Car;
import models.Conductor;
import models.carDAO;
import models.conductorDAO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalConductorController implements Initializable{

    @FXML
    private GridPane grid;

    @FXML
    private Button search;

    @FXML
    private Button exitButton;

    @FXML
    private Button carsButton;

    @FXML
    private Button addConductorButton;

    @FXML
    private TextField searchTxt;

    @FXML
    private Button bckupBtn;

    private List<Conductor> conductors;

    @FXML
    void backup(ActionEvent event) {

    }

    @FXML
    void addConductor(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/addConductor.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goCars(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalCar.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void searchConductor(ActionEvent event) {
        grid.getChildren().clear();
        int column=0;
        int row=0;
        try{
            for(int i=0;i<conductors.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneConductorCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneConductorCardController oneConductorCardController=fxmlLoader.getController();
                oneConductorCardController.setData(conductors.get(i));
                if (conductors.get(i).getNit().contains(searchTxt.getText().toUpperCase()) || conductors.get(i).getNombre().toLowerCase().contains(searchTxt.getText().toLowerCase()) || conductors.get(i).getApellido().toLowerCase().contains(searchTxt.getText().toLowerCase())) {
                    oneConductorCardController.setData(conductors.get(i));
                    if (column==3){
                        column=0;
                        row++;
                    }
                    grid.add(anchorPane,column++,row);
                    //set grid width
                    if (row<1){
                        if (column==2){
                            grid.setMinWidth(800);
                            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            grid.setMaxWidth(Region.USE_PREF_SIZE);
                        }else{
                            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            grid.setMaxWidth(Region.USE_PREF_SIZE);}
                    }
                    else {
                        grid.setMinWidth(1200);
                        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        grid.setMaxWidth(Region.USE_PREF_SIZE);
                    }
                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane,new Insets(10));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private conductorDAO dao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao=new conductorDAO();
        this.conductors =dao.getAll();
        int column=0;
        int row=0;
        try{
            for(int i=0;i<conductors.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneConductorCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneConductorCardController oneConductorCardController=fxmlLoader.getController();
                oneConductorCardController.setData(conductors.get(i));
                if (column==3){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);
                //set grid width
                if (row<1 && column!=3){
                    if (column==2){
                        grid.setMinWidth(800);
                        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        grid.setMaxWidth(Region.USE_PREF_SIZE);
                    }else{
                        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                        grid.setMaxWidth(Region.USE_PREF_SIZE);}
                }
                else {
                    grid.setMinWidth(1200);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);
                }

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
}