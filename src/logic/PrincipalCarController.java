package logic;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Car;
import models.carDAO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PrincipalCarController implements Initializable {
    @FXML
    private AnchorPane anchor;

    @FXML
    private BorderPane border;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private TextField searchtxt;

    @FXML
    private Button search;

    @FXML
    private Button conductors;

    @FXML
    private Button exitButton;

    @FXML
    private Button addCarButton;

    @FXML
    void addCar(ActionEvent event) {

    }

    @FXML
    void goConductors(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalConductor.fxml"));
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
    void searchCar(ActionEvent event) {
        grid.getChildren().clear();
        dao=new carDAO();
        List<Car> cars = dao.getAll();
        int column=0;
        int row=0;
        try{
            for(int i=0;i<cars.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneCarCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                OneCarCardController onecarcardcontroller=fxmlLoader.getController();
                cars.get(i).setImgs(dao.getImages(cars.get(i).getPlaca()));
                if (cars.get(i).getPlaca().contains(searchtxt.getText().toUpperCase())) {
                    System.out.println(cars.get(i).getPlaca());
                    onecarcardcontroller.setData(cars.get(i));
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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
                if (row<1){
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);
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
