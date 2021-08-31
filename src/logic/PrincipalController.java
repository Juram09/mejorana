package logic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import models.Car;
import models.carDAO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private Button exitButton;

    @FXML
    private AnchorPane anchor;

    @FXML
    private BorderPane border;

    @FXML
    private Button addConductorButton;

    @FXML
    private Button addCarButton;

    @FXML
    void addCar(ActionEvent event) {

    }

    @FXML
    void addConductor(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    private carDAO dao;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        dao=new carDAO();
        List<Car> cars =dao.getAll();
        int column=0;
        int row=0;
        try{
            for(int i=0;i<cars.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneCarCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneCarCardController onecarcardcontroller=fxmlLoader.getController();
                cars.get(i).setImgs(dao.getImages(cars.get(i).getPlaca()));
                onecarcardcontroller.setData(cars.get(i));
                if (column==3){
                    column=0;
                    row++;
                }
                grid.add(anchorPane,column++,row);
                //set grid width
                grid.setMinWidth(1250);
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
}
