package logic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Conductor;
import models.conductorDAO;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class AddConductorController implements Initializable {

   @FXML
   private TextField nitTxt;

   @FXML
   private TextField nameTxt;

   @FXML
   private TextField lastnameTxt;

   @FXML
   private TextField phoneTxt;

   @FXML
   private TextField licenseTxt;

   @FXML
   private ChoiceBox<String> tipoChoice;

   @FXML
   private Button addButton;

   @FXML
   private Button menuButton;

   @FXML
   private Button backButton;

   @FXML
   private Button exitButton;

   @FXML
   private Circle img;

   @FXML
   private Button imgButton;

   private Image image;
   private File selectedFile;

   @FXML
   void add(ActionEvent event) {
      if(nitTxt.getText()==null || tipoChoice.getValue()==null || nameTxt.getText()==null || lastnameTxt.getText()==null || phoneTxt.getText()==null || licenseTxt.getText()==null){
         //TODO:Ventana emergente error
      }else if(check(phoneTxt.getText()) || check(licenseTxt.getText())) {
         //TODO
      }else{
         try {
            conductorDAO dao = new conductorDAO();
            Conductor conductor = new Conductor();
            conductor.setNit(this.nitTxt.getText());
            conductor.setDocumento(this.tipoChoice.getValue());
            conductor.setNombre(this.nameTxt.getText().substring(0,1).toUpperCase()+this.nameTxt.getText().substring(1).toLowerCase());
            conductor.setApellido(this.lastnameTxt.getText().substring(0,1).toUpperCase()+this.lastnameTxt.getText().substring(1).toLowerCase());
            conductor.setTelefono(Long.parseLong(this.phoneTxt.getText()));
            conductor.setLicencia(Long.parseLong(this.licenseTxt.getText()));
            String base = toBase().replace("\n", "").replace("\r", "");
            conductor.setImagen("data:image/jpeg;base64,"+base);
            dao.insert(conductor);
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
         catch (Exception e){
            //TODO: Ventanas emergentes
         }
      }
   }

   private boolean check(String text) {
      boolean bool= false;
      try{
         Long x=Long.parseLong(text);
      }catch (Exception e){
         bool=true;
      }
      return bool;
   }

   @FXML
   void exit(ActionEvent event) {
      Platform.exit();
      System.exit(0);
   }

   @FXML
   void goBack(ActionEvent event) throws IOException {
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
   void goMenu(ActionEvent event) throws IOException {
      goBack(event);
   }

   @FXML
   void choiceImg(ActionEvent event) throws IOException {
      FileChooser fc= new FileChooser();
      fc.setTitle("Seleccionar imagen");
      Stage stage=(Stage) imgButton.getScene().getWindow();
      fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images extensions (*.jpg & *.png)","*.jpg","*.png"));
      this.selectedFile=fc.showOpenDialog(stage);
      if(this.selectedFile!=null){
        this.image=new Image(this.selectedFile.toURI().toString());
         img.setOpacity(1);
         img.setFill(new ImagePattern(image));
      }else{
         //TODO: Ventana emergente error
      }
   }

   @Override
   public void initialize(URL location, ResourceBundle resources) {
      tipoChoice.getItems().add("Cedula de ciudadania");
      tipoChoice.getItems().add("Registro civil de nacimiento");
      tipoChoice.getItems().add("Tarjeta de identidad");
      tipoChoice.getItems().add("Cedula de extranjeria");
      tipoChoice.getItems().add("DNI (Pais de origen)");
      tipoChoice.getItems().add("Pasaporte");
      tipoChoice.getItems().add("Salvoconducto para refugiado");
      tipoChoice.getItems().add("Permiso especial de permanencia");
      tipoChoice.getItems().add("Numero de identificacion tributaria");
      tipoChoice.setStyle("-fx-font: 18px \"Century Gothic\";");
   }

   private String toBase(){
      String imageString = null;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      try{
         BufferedImage img = ImageIO.read(new File(this.selectedFile.getPath()));
         ImageIO.write(img, "jpg", bos);
         byte[] imageBytes = bos.toByteArray();
         BASE64Encoder encoder = new BASE64Encoder();
         imageString = encoder.encode(imageBytes);
         bos.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return imageString;
   }
}
