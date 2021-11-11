package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Document;
import sun.misc.BASE64Encoder;
import sun.util.resources.LocaleData;

import javax.imageio.ImageIO;
import javax.print.Doc;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddDocumentController implements Initializable {

    @FXML
    private ComboBox<String> comboType;

    @FXML
    private Label placaLbl;

    @FXML
    private TextField numberTxt;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView Img;

    @FXML
    private Button ImgBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button addBtn;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private CheckBox checkDate;

    ViewDocsController controller;
    private Image image;
    private File selectedFile;
    private boolean newImg;
    private AddCarController controller1;

    @FXML
    void add(ActionEvent event) throws IOException {
        if(valid()){
            Document document=new Document();
            document.setId(numberTxt.getText());
            document.setName(comboType.getValue());
            document.setDate(java.sql.Date.valueOf(datePicker.getValue()));
            document.setDescription(descriptionTxt.getText());
            if (checkDate.isSelected() || document.getName().toLowerCase().equals("soat") || document.getName().toLowerCase().equals("tecnomecanica")){
                document.setDateOut(java.sql.Date.valueOf(datePicker1.getValue()));
            }
            if(this.newImg){
                String base = getImage().replace("\n", "").replace("\r", "");
                document.setImg("data:image/jpeg;base64,"+base);
            }
            document.setActive(true);
            if(controller==null){
                this.controller1.addDoc(document);
            }
            else{
                this.controller.addDoc(document);
            }
            if(controller!=null)
                this.controller.updateDocs(placaLbl.getText());
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    @FXML
    void changeDate(ActionEvent event) {
        this.datePicker1.setDisable(!this.datePicker1.isDisabled());
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
    void comboChange(ActionEvent event) {
        if(comboType.getValue().toLowerCase().contains("soat") || comboType.getValue().toLowerCase().contains("tecnomecanica")){
            this.checkDate.setSelected(true);
            this.datePicker1.setDisable(false);
            if(this.datePicker.getValue()!=null){
                LocalDate date2=datePicker.getValue().plusYears(1);
                datePicker1.setValue(date2);
            }
        }else{
            this.checkDate.setSelected(false);
            this.datePicker1.setDisable(true);
            this.datePicker1.setValue(null);
        }
    }

    @FXML
    void newDate(ActionEvent event) {
        if(comboType.getValue().toLowerCase().contains("soat") || comboType.getValue().toLowerCase().contains("tecnomecanica")){
            this.checkDate.setSelected(true);
            this.datePicker1.setDisable(false);
            LocalDate date2=datePicker.getValue().plusYears(1);
            datePicker1.setValue(date2);
        }
    }

    @FXML
    void searchImg(ActionEvent event) {
        FileChooser fc= new FileChooser();
        fc.setTitle("Seleccionar imagen");
        Stage stage=(Stage) ImgBtn.getScene().getWindow();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images extensions (*.jpg & *.png)","*.jpg","*.png"));
        this.selectedFile=fc.showOpenDialog(stage);
        if(this.selectedFile!=null){
            this.image=new Image(this.selectedFile.toURI().toString());
            this.Img.setImage(this.image);
            this.newImg=true;
        }else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selección de imagen");
            alert.setHeaderText(null);
            alert.setContentText("No se ha seleccionado ninguna imagen");
            alert.showAndWait();
        }
    }

    public void setCar(String placa, ViewDocsController controller){
        placaLbl.setText(placa);
        this.controller=controller;
    }
    
    public void setCar(String placa, AddCarController controller){
        placaLbl.setText(placa);
        this.controller1=controller;
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

    private boolean valid(){
        if(this.numberTxt.getText().isEmpty() || this.comboType.getValue().isEmpty() || this.datePicker.getValue()==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Los campos con * son obligatorios");
            alert.showAndWait();
            return false;
        }else if(this.checkDate.isSelected() && this.datePicker1.getValue()==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Se debe introducir fecha de vencimiento del documento \n Si este documento no tiene fecha de vencimiento desmarque la casilla");
            alert.showAndWait();
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboType.getItems().add("Tarjeta de propiedad");
        comboType.getItems().add("SOAT");
        comboType.getItems().add("Tecnomecanica");
        comboType.setBackground(Background.EMPTY);
        comboType.setStyle("-fx-font: 14px \"Century Gothic\";");
        datePicker.setStyle("-fx-font: 14px \"Century Gothic\";");
        datePicker1.setStyle("-fx-font: 14px \"Century Gothic\";");
    }
}