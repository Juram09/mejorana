package logic;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
            exit(event);
        }else{
            //TODO: Ventana emergente error
        }
    }

    @FXML
    void exit(ActionEvent event) {
        this.controller.updateMaints(this.placa);
        ((Node)(event.getSource())).getScene().getWindow().hide();
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
            //TODO: Ventana emergente error
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
            return false;
        }else if(this.checkNext.isSelected() && this.kmTxt1.getText().isEmpty()){
            return false;
        }else if(Long.parseLong(this.kmTxt1.getText())<this.actualKm){
            return false;
        }else {
            return true;
        }
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
            e.printStackTrace();
        }
        return imageString;
    }
}
