package logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Car;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

public class OneCarCardController {
    @FXML
    private Button view;

    @FXML
    private Button edit;

    @FXML
    private Label placaLabel;

    @FXML
    private Label kmLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Circle circle;

    @FXML
    void editCar(ActionEvent event) {

    }

    @FXML
    void viewCar(ActionEvent event) {
        System.out.println(placaLabel);
    }

    private Car car;

    public void setData(Car car,String image){
        System.out.println(car.getPlaca());
        this.car=car;
        placaLabel.setText(car.getPlaca());
        kmLabel.setText(String.valueOf(car.getKm()));
        tipoLabel.setText(car.getTipo());
        Image img;
        if(image=="no"){
            img=new Image("imgs/NOT.jpg");
        }else{
            img=getImage(image);
        }
        circle.setFill(new ImagePattern(img));
    }
    public void setNew(){

    }
    private Image getImage(String image){
        try{
            String imageDataBytes = image.substring(image.indexOf(",")+1);
            InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
            Image img= new Image(stream);
            return img;
        }
        catch (Exception e) {
            return null;
        }
    }
}
