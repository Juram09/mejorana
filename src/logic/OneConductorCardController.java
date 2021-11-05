package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Car;
import models.Conductor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    void editConductor(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/editConductor.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        EditConductorController controller=fxmlLoader.getController();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
        controller.setData(this.conductor);
    }

    @FXML
    void viewConductor(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/oneConductor.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        OneConductorController controller=fxmlLoader.getController();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
        controller.setConductor(this.conductor);
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
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error cargando la imagen del conductor: "+this.conductor.getNit());
            alert.showAndWait();
            return null;
        }
    }
}
