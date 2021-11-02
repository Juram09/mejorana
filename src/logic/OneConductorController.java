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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Conductor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class OneConductorController {

    @FXML
    private Label nitLbl;

    @FXML
    private Label tipoLbl;

    @FXML
    private Label nombreLbl;

    @FXML
    private Label apellidoLbl;

    @FXML
    private Label telefonoLbl;

    @FXML
    private Label licenciaLbl;

    @FXML
    private Button exitBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button menuBtn;

    @FXML
    private Circle circleImg;
    private Conductor conductor;

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalConductor.fxml"));
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
    void goMenu(ActionEvent event) throws IOException {
        goBack(event);
    }

    public void setConductor(Conductor conductor){
        this.conductor=conductor;
        nitLbl.setText(String.valueOf(conductor.getNit()));
        tipoLbl.setText(conductor.getDocumento());
        nombreLbl.setText(conductor.getNombre());
        apellidoLbl.setText(conductor.getApellido());
        telefonoLbl.setText(String.valueOf(conductor.getTelefono()));
        licenciaLbl.setText(String.valueOf(conductor.getLicencia()));
        Image img;
        if(conductor.getImagen()==null){
            img=new Image("imgs/NOTC.jpg");
        }else{
            img=getImage(conductor.getImagen());
            if(img==null)
                img=new Image("imgs/NOTC.jpg");
        }
        circleImg.setFill(new ImagePattern(img));
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
