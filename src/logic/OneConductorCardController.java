package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Car;
import models.Conductor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

public class OneConductorCardController {

    @FXML
    private Circle circle;

    @FXML
    private Label nitLabel;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label apellidoLabel;

    @FXML
    private Button view;

    @FXML
    private Button edit;

    private Conductor conductor;

    @FXML
    void editConductor(ActionEvent event) {

    }

    @FXML
    void viewConductor(ActionEvent event) {

    }

    public void setData(Conductor conductor){
        this.conductor=conductor;
        nitLabel.setText(String.valueOf(conductor.getNit()));
        nombreLabel.setText(conductor.getNombre());
        apellidoLabel.setText(conductor.getApellido());
        Image img;
        if(conductor.getImagen()==null){
            img=new Image("imgs/NOTC.jpg");
        }else{
            img=getImage(conductor.getImagen());
            if(img==null)
                img=new Image("imgs/NOTC.jpg");
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
            System.out.println("La imagen no se puede cargar");
            return null;
        }
    }
}
