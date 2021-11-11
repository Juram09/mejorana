package logic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.List;
import java.util.Optional;
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
   void add(ActionEvent event) throws IOException {
      if(nitTxt.getText()==null || tipoChoice.getValue()==null || nameTxt.getText()==null || lastnameTxt.getText()==null || phoneTxt.getText()==null || licenseTxt.getText()==null){
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Todos los campos son obligatorios");
         alert.showAndWait();
      }else if(check(phoneTxt.getText()) || check(licenseTxt.getText())) {
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Solo se aceptan números en los campos de teléfono y licencia");
         alert.showAndWait();
      }else if(checkDocument()){
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Ya existe un conductor registrado con este documento");
         alert.showAndWait();
      }else{
         try {
            conductorDAO dao = new conductorDAO();
            Conductor conductor = new Conductor();
            conductor.setNit(this.nitTxt.getText());
            conductor.setDocumento(this.tipoChoice.getValue());
            conductor.setNombre(this.nameTxt.getText().substring(0,1).toUpperCase()+this.nameTxt.getText().substring(1).toLowerCase());
            conductor.setApellido(this.lastnameTxt.getText().substring(0,1).toUpperCase()+this.lastnameTxt.getText().substring(1).toLowerCase());
            conductor.setTelefono(Long.parseLong(this.phoneTxt.getText().replace(" ","").replace(".","").replace(",","").replace("\n","")));
            conductor.setLicencia(Long.parseLong(this.licenseTxt.getText().replace(" ","").replace(".","").replace(",","").replace("\n","")));
            if(this.image!=null){
               String base = toBase().replace("\n", "").replace("\r", "");
               conductor.setImagen("data:image/jpeg;base64,"+base);
            }
            dao.insert(conductor);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Conductor agregado");
            alert.setHeaderText(null);
            alert.setContentText("El conductor se ha agregado exitosamente");
            alert.showAndWait();
         } catch (Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error añadiendo el conductor");
            alert.showAndWait();
         }finally {
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
      }
   }

   private boolean checkDocument() {
      conductorDAO dao=new conductorDAO();
      this.nitTxt.setText(this.nitTxt.getText().replace(" ","").replace("\n","").replace(".","").replace(",",""));
      List<Conductor> conductors= dao.getAll();
      for(int i=0;i<conductors.size();i++){
         if(conductors.get(i).getNit().equals(this.nitTxt.getText())){
            return true;
         }
      }
      return false;
   }

   private boolean check(String text) {
      boolean bool= false;
      Long x= Long.valueOf(0);
      text=text.replace(" ","").replace(".","").replace(",","").replace("\n","");
      try{
         x=Long.parseLong(text);
      }catch (Exception e){
         bool=true;
      }
      if(x<1){
         bool=false;
      }
      return bool;
   }

   @FXML
   void exit(ActionEvent event) {
      Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmación para salir");
      alert.setHeaderText(null);
      alert.setContentText("¿Está seguro que desea salir? \n No se guardaran los cambios efectuados");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK){
         Platform.exit();
         System.exit(0);
      }
   }

   @FXML
   void goBack(ActionEvent event) throws IOException {
      Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Confirmación para salir");
      alert.setHeaderText(null);
      alert.setContentText("¿Está seguro que desea volver? \n No se guardaran los cambios efectuados");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK){
         ((Node) (event.getSource())).getScene().getWindow().hide();
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalConductor.fxml"));
         Parent root = (Parent) fxmlLoader.load();
         Stage stage = new Stage();
         stage.initStyle(StageStyle.UNDECORATED);
         Scene scene = new Scene(root, 1280, 720);
         stage.setScene(scene);
         stage.setTitle("La Mejorana");
         stage.setResizable(false);
         stage.show();
      }
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
         Alert alert=new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle("Selección de imagen");
         alert.setHeaderText(null);
         alert.setContentText("No se ha seleccionado ninguna imagen");
         alert.showAndWait();
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
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Error cargando la imagen seleccionada");
         alert.showAndWait();
      }
      return imageString;
   }
}
