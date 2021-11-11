package logic;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Maintenance;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddMaintController implements Initializable{

    @FXML
    private ComboBox<String> tipoCombo;

    @FXML
    private TextField kmTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField kmTxt1;

    @FXML
    private TextArea descripcionTxt;

    @FXML
    private CheckBox checkNext;

    @FXML
    private ImageView img;

    @FXML
    private Button changeImg;

    @FXML
    private Button exitBtn;

    @FXML
    private Button addBtn;

    private Image image;
    private File selectedFile;
    private ViewMaintsController controller;
    private String placa;
    private boolean newImg;
    private Long actualKm;


    @FXML
    void addMaint(ActionEvent event) throws IOException {
        if(valid()){
            Maintenance maint=new Maintenance();
            maint.setTipo(tipoCombo.getValue());
            maint.setKm(Long.parseLong(kmTxt.getText()));
            maint.setFecha(java.sql.Date.valueOf(datePicker.getValue()));
            maint.setDescripcion(descripcionTxt.getText());
            if (checkNext.isSelected()){
                maint.setProx(Long.parseLong(kmTxt1.getText()));
            }
            if(this.newImg){
                String base = getImage().replace("\n", "").replace("\r", "");
                maint.setImagen("data:image/jpeg;base64,"+base);
            }
            maint.setActive(true);
            this.controller.addMaint(maint);
            this.controller.updateMaints(this.placa);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación para salir");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea salir? \n No se guardaran los cambios efectuados");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    @FXML
    void checkChange(ActionEvent event) {
        this.kmTxt1.setDisable(!this.checkNext.isSelected());
        this.kmTxt1.setText("");
    }

    @FXML
    void searchImg(ActionEvent event) {
        FileChooser fc= new FileChooser();
        fc.setTitle("Seleccionar imagen");
        Stage stage=(Stage) changeImg.getScene().getWindow();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images extensions (*.jpg & *.png)","*.jpg","*.png"));
        this.selectedFile=fc.showOpenDialog(stage);
        if(this.selectedFile!=null){
            this.newImg=true;
            this.image=new Image(this.selectedFile.toURI().toString());
            this.img.setImage(this.image);
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
        tipoCombo.getItems().add("Cambio de aceite");
        tipoCombo.setBackground(Background.EMPTY);
        tipoCombo.setStyle("-fx-font: 14px \"Century Gothic\";");
        datePicker.setStyle("-fx-font: 14px \"Century Gothic\";");
    }

    public void setCar(String placa, Long actualKm, ViewMaintsController controller){
        this.placa=placa;
        this.actualKm=actualKm;
        this.controller=controller;
    }

    private boolean valid(){
        if(this.kmTxt.getText().isEmpty() || this.tipoCombo.getValue().isEmpty() || this.datePicker.getValue()==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Los campos con * son obligatorios");
            alert.showAndWait();
            return false;
        }else if(this.checkNext.isSelected() && this.kmTxt1.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Se debe introducir el kilometraje para el proximo mantenimiento de este tipo\n Si no tiene proximo mantenimiento de este tipo programado en km desmarque la casilla");
            alert.showAndWait();
            return false;
        }else if(!isLong()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El kilometraje no es valido\n Si no tiene proximo mantenimiento de este tipo programado en km desmarque la casilla");
            alert.showAndWait();
            return false;
        }else if(Long.parseLong(this.kmTxt1.getText())<this.actualKm){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El kilometraje del mantenimiento siguiente es menor al kilometraje actual\n Si no tiene proximo mantenimiento de este tipo programado en km desmarque la casilla");
            alert.showAndWait();
            return false;
        }else{
            return true;
        }
    }

    private boolean isLong() {
        boolean xd=true;
        try{
            xd=Long.parseLong(this.kmTxt1.getText())>0 && Long.parseLong(this.kmTxt1.getText())>0;
        }catch (Exception e){
            xd=false;
        }
        return xd;
    }

    private String getImage(){
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            BufferedImage img = ImageIO.read(new File(this.selectedFile.getPath()));
            ImageIO.write(img, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error cargando la imagen seleccionada");
            alert.showAndWait();
        }
        return imageString;
    }
}
