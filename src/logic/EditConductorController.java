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
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditConductorController implements Initializable {

    @FXML
    private Label nitLabel;

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
    private Button editButton;

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
    private Image previousImage;
    private String basesf;


    @FXML
    void choiceImg(ActionEvent event) {
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

    @FXML
    void edit(ActionEvent event) throws IOException {
        if(tipoChoice.getValue()==null || nameTxt.getText()==null || lastnameTxt.getText()==null || phoneTxt.getText()==null || licenseTxt.getText()==null){
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
        }else{
            try {
                conductorDAO dao = new conductorDAO();
                Conductor conductor = new Conductor();
                conductor.setNit(this.nitLabel.getText());
                conductor.setDocumento(this.tipoChoice.getValue());
                conductor.setNombre(this.nameTxt.getText().substring(0,1).toUpperCase()+this.nameTxt.getText().substring(1).toLowerCase());
                conductor.setApellido(this.lastnameTxt.getText().substring(0,1).toUpperCase()+this.lastnameTxt.getText().substring(1).toLowerCase());
                conductor.setTelefono(Long.parseLong(this.phoneTxt.getText()));
                conductor.setLicencia(Long.parseLong(this.licenseTxt.getText()));
                if (checkImg()){
                    String base = toBase().replace("\n", "").replace("\r", "");
                    conductor.setImagen("data:image/jpeg;base64,"+base);
                }else{
                    conductor.setImagen(this.basesf);
                }
                dao.update(conductor);
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Conductor editado");
                alert.setHeaderText(null);
                alert.setContentText("El conductor se ha editado exitosamente");
                alert.showAndWait();
            }
            catch (Exception e){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error añadiendo el conductor");
                alert.showAndWait();
            }finally{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ui/principalConductor.fxml"));
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
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selección de imagen");
            alert.setHeaderText(null);
            alert.setContentText("No se ha seleccionado ninguna imagen");
            alert.showAndWait();
        }
        return imageString;
    }

    public void setData(Conductor conductor){
        this.nitLabel.setText(conductor.getNit());
        this.tipoChoice.setValue(conductor.getDocumento());
        this.nameTxt.setText(conductor.getNombre());
        this.lastnameTxt.setText(conductor.getApellido());
        this.phoneTxt.setText(Long.toString(conductor.getTelefono()));
        this.licenseTxt.setText(Long.toString(conductor.getLicencia()));
        this.image=getImg(conductor.getImagen());
        this.basesf=conductor.getImagen();
        this.previousImage=image;
        if (image!=null){
            img.setOpacity(1);
            img.setFill(new ImagePattern(image));
        }
    }

    private Image getImg(String img){
        if (img==null){
            return null;
        }else{
            try{
                String imageDataBytes = img.substring(img.indexOf(",")+1);
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
                Image image= new Image(stream);
                return image;
            }
            catch (Exception e) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error cargando la imagen del conductor");
                alert.showAndWait();
                return null;
            }
        }
    }
    private boolean checkImg(){
        if(this.previousImage==null){
            return true;
        }else{
            if(this.previousImage==this.image) {
                return false;
            }
            else{
                return true;
            }
        }
    }
}
