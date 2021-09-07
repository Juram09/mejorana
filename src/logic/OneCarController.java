package logic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class OneCarController{

    @FXML
    private ImageView carImg;

    @FXML
    private Button editButton;

    @FXML
    private Button addControlButton;

    @FXML
    private Button editDocumentsButton;

    @FXML
    private Button addMantButton;

    @FXML
    private Button showFrontalButton;

    @FXML
    private Button showBackButton;

    @FXML
    private Button showRightButton;

    @FXML
    private Button showLeftButton;

    @FXML
    private Button showCabinButton;

    @FXML
    private Button seeCardButton;

    @FXML
    private Button seeSoatButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Button seeTecnoButton;

    @FXML
    private Button seeOtherButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button goMenuButton;

    @FXML
    private Label placasLabel;

    @FXML
    private Label kmLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label marcaLabel;

    @FXML
    private Label modeloLabel;

    @FXML
    private Label chasisLabel;

    @FXML
    private Label capacidadLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Label cardLabel;

    @FXML
    private Label soatLabel;

    @FXML
    private Label tecnoLabel;

    @FXML
    private Label nitLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label licenseLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Circle conductorImg;

    private Car car;
    private String placa;
    private Conductor conductor;

    @FXML
    void addControl(ActionEvent event) {

    }

    @FXML
    void addMant(ActionEvent event) {

    }

    @FXML
    void editDocuments(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalCar.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void goEdit(ActionEvent event) {

    }

    @FXML
    void goMenu(ActionEvent event) throws IOException {
        goBack(event);
    }

    @FXML
    void seeCard(ActionEvent event) {

    }

    @FXML
    void seeOther(ActionEvent event) {

    }

    @FXML
    void seeSoat(ActionEvent event) {

    }

    @FXML
    void seeTecno(ActionEvent event) {

    }
    @FXML
    void deleteCar(ActionEvent event) throws IOException {
        carDAO dao=new carDAO();
        dao.delete(placasLabel.getText());
        goBack(event);
    }


    @FXML
    void showBack(ActionEvent event) {
        show("car",this.car.getImgs().getTrasera());
    }

    @FXML
    void showCabin(ActionEvent event) {
        show("car",this.car.getImgs().getCabin());
    }

    @FXML
    void showFrontal(ActionEvent event) {
        show("car",this.car.getImgs().getFrontal());
    }

    @FXML
    void showLeft(ActionEvent event) {
        show("car",this.car.getImgs().getLatIzq());
    }

    @FXML
    void showRight(ActionEvent event) {
        show("car",this.car.getImgs().getLatDer());
    }

    public void setCar(String placa){
        this.placa=placa;
        carDAO dao=new carDAO();
        conductorDAO cdao=new conductorDAO();
        this.car=dao.getOne(this.placa);
        placasLabel.setText(this.placa);
        kmLabel.setText(String.valueOf(car.getKm()));
        colorLabel.setText(String.valueOf(car.getColor()));
        marcaLabel.setText(String.valueOf(car.getMarca()));
        modeloLabel.setText(String.valueOf(car.getModelo()));
        chasisLabel.setText(String.valueOf(car.getChasis()));
        capacidadLabel.setText(String.valueOf(car.getCapacidad()));
        tipoLabel.setText(car.getTipo());
        this.conductor=cdao.getOne(dao.getConductor(this.placa));
        nitLabel.setText(String.valueOf(conductor.getNit()));
        nameLabel.setText(String.valueOf(conductor.getNombre()));
        lastnameLabel.setText(String.valueOf(conductor.getApellido()));
        phoneLabel.setText(String.valueOf(conductor.getTelefono()));
        licenseLabel.setText(String.valueOf(conductor.getLicencia()));
        show("conductor",conductor.getImagen());
        show("car",car.getImgs().getFrontal());
    }
    private void show(String tipo,String image){
        Image img;
        try{
            if (image==null){
                if (tipo.equals("car")){
                    img=new Image("imgs/NOT.jpg");
                }
                else{
                    img=new Image("imgs/NOTC.jpg");
                }
            }else{
                String imageDataBytes = image.substring(image.indexOf(",")+1);
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
                img=new Image(stream);
            }
            if(tipo.equals("car")){
                carImg.setImage(img);
            }else{
                conductorImg.setFill(new ImagePattern(img));
            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
