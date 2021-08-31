package logic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Car;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    private Car car;

    @FXML
    void editCar(ActionEvent event) {

    }

    @FXML
    void viewCar(ActionEvent event) throws IOException{
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/oneCar.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        OneCarController controller=fxmlLoader.getController();
        controller.setCar(placaLabel.getText());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
    }

    public void setData(Car car){
        this.car=car;
        placaLabel.setText(car.getPlaca());
        kmLabel.setText(String.valueOf(car.getKm()));
        tipoLabel.setText(car.getTipo());
        Image img;
        if(car.getImgs().getFrontal()==null){
            img=new Image("imgs/NOT.jpg");
        }else{
            img=getImage(car.getImgs().getFrontal());
        }
        circle.setFill(new ImagePattern(img));
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
