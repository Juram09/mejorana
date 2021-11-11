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
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import sun.misc.BASE64Encoder;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.*;

public class AddCarController implements Initializable {

    @FXML
    private ImageView Img;

    @FXML
    private ImageView btnImg;

    @FXML
    private Button addButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button backButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button carImgBtn;

    @FXML
    private Button frontalBtn;

    @FXML
    private Button traseraBtn;

    @FXML
    private Button izqBtn;

    @FXML
    private Button derBtn;

    @FXML
    private Button cabinaBtn;

    @FXML
    private Button addConductorBtn;

    @FXML
    private Button addDocumentBtn;

    @FXML
    private TextField placaTxt;

    @FXML
    private TextField kmTxt;

    @FXML
    private TextField colorTxt;

    @FXML
    private TextField marcaTxt;

    @FXML
    private TextField modeloTxt;

    @FXML
    private TextField chasisTxt;

    @FXML
    private TextField capacidadTxt;

    @FXML
    private TextField tipoTxt;

    @FXML
    private Label tarjetaLbl;

    @FXML
    private Label soatLbl;

    @FXML
    private Label tecnomecanicaLbl;

    @FXML
    private Label nombreLbl;

    @FXML
    private Label apellidoLbl;

    @FXML
    private Label telefonoLbl;

    @FXML
    private Label licenciaLbl;

    @FXML
    private ChoiceBox<String> choiceConductor;

    @FXML
    private Circle circleImg;

    @FXML
    private ImageView blackUnfocus;

    private Images imgs;
    private int active;
    private File selectedFile;
    private List<Conductor> conductors;
    private List<Document> documents=new ArrayList<>();
    private boolean conductorInDB;
    private Conductor conductor;
    private String placa;

    @FXML
    void add(ActionEvent event) throws IOException {
        if(this.validate()){
            try{
                this.placa=format();
                Car car=new Car();
                car.setPlaca(this.placa);
                car.setKm(Long.parseLong(this.kmTxt.getText()));
                car.setTipo(this.tipoTxt.getText());
                car.setCapacidad(Integer.parseInt(this.capacidadTxt.getText()));
                car.setChasis(this.chasisTxt.getText());
                car.setColor(this.colorTxt.getText());
                car.setMarca(this.marcaTxt.getText());
                car.setModelo(Integer.parseInt(this.modeloTxt.getText()));
                if(!this.conductorInDB){
                    insertConductor();
                }
                car.setConductor(this.choiceConductor.getValue());
                carDAO dao=new carDAO();
                dao.insert(car);
                insertImgs();
                insertDocuments();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Automovil agregado");
                alert.setHeaderText(null);
                alert.setContentText("El automovil se ha agregado correctamente");
                alert.showAndWait();
            }catch(Exception e){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error añadiendo el automovil");
                alert.showAndWait();
            }finally{
                ((Node) (event.getSource())).getScene().getWindow().hide();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalCar.fxml"));
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
    }

    private String format() {
        String placa=this.placaTxt.getText().replace("\n", "").replace(" ", "").replace("-","");
        placa=placa.toUpperCase();
        return placa;
    }

    @FXML
    void addConductor(ActionEvent event) throws IOException {
        this.blackUnfocus.setOpacity(1);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/addConductorEmergent.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 755, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        AddConductorEmergentController controller=fxmlLoader.getController();
        controller.setData(this);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        this.blackUnfocus.setOpacity(0);
    }

    @FXML
    void addDocument(ActionEvent event) throws IOException {
        this.blackUnfocus.setOpacity(1);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/addDocument.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 755, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        AddDocumentController controller=fxmlLoader.getController();
        controller.setCar(this.placa,this);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        this.blackUnfocus.setOpacity(0);
    }

    @FXML
    void searchImg(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar imagen");
        Stage stage = (Stage) carImgBtn.getScene().getWindow();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images extensions (*.jpg & *.png)", "*.jpg", "*.png"));
        this.selectedFile = fc.showOpenDialog(stage);
        if (this.selectedFile != null) {
            Image image = new Image(this.selectedFile.toURI().toString());
            this.Img.setImage(image);
            toBase();
        } else {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selección de imagen");
            alert.setHeaderText(null);
            alert.setContentText("No se ha seleccionado ninguna imagen");
            alert.showAndWait();
        }
    }

    @FXML
    void selectCabina(ActionEvent event) {
        this.active = 5;
        btnImg.setLayoutX(263);
        btnImg.setLayoutY(577);
        btnImg.setImage(new Image("imgs/cabinImg.png"));
        if (this.imgs.getCabin() == null) {
            this.Img.setImage(new Image("imgs/carImgs.png"));
        } else {
            this.Img.setImage(getImage(this.imgs.getCabin()));
            this.Img.setPreserveRatio(false);
        }
    }

    @FXML
    void selectDer(ActionEvent event) {
        this.active = 4;
        btnImg.setLayoutX(126);
        btnImg.setLayoutY(577);
        btnImg.setImage(new Image("imgs/latDerImg.png"));
        if (this.imgs.getLatDer() == null) {
            this.Img.setImage(new Image("imgs/carImgs.png"));
        } else {
            this.Img.setImage(getImage(this.imgs.getLatDer()));
            this.Img.setPreserveRatio(false);
        }
    }

    @FXML
    void selectFrontal(ActionEvent event) {
        this.active = 1;
        btnImg.setLayoutX(57);
        btnImg.setLayoutY(461);
        btnImg.setImage(new Image("imgs/frontalImg.png"));
        if (this.imgs.getFrontal() == null) {
            this.Img.setImage(new Image("imgs/carImgs.png"));
        } else {
            this.Img.setImage(getImage(this.imgs.getFrontal()));
            this.Img.setPreserveRatio(false);
        }
    }

    @FXML
    void selectIzq(ActionEvent event) {
        this.active = 3;
        btnImg.setLayoutX(332);
        btnImg.setLayoutY(461);
        btnImg.setImage(new Image("imgs/latIzqImg.png"));
        if (this.imgs.getLatIzq() == null) {
            this.Img.setImage(new Image("imgs/carImgs.png"));
        } else {
            this.Img.setImage(getImage(this.imgs.getLatIzq()));
            this.Img.setPreserveRatio(false);
        }
    }

    @FXML
    void selectTrasera(ActionEvent event) {
        this.active = 2;
        btnImg.setLayoutX(196);
        btnImg.setLayoutY(461);
        btnImg.setImage(new Image("imgs/traseraImg.png"));
        if (this.imgs.getTrasera() == null) {
            this.Img.setImage(new Image("imgs/carImgs.png"));
        } else {
            this.Img.setImage(getImage(this.imgs.getTrasera()));
            this.Img.setPreserveRatio(false);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalCar.fxml"));
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
        this.active = 1;
        this.imgs = new Images();
        conductorDAO dao = new conductorDAO();
        this.conductors = dao.getAll();
        for (int i = 0; i < this.conductors.size(); i++)
            this.choiceConductor.getItems().add(this.conductors.get(i).getNit());
        this.choiceConductor.setStyle("-fx-font: 16px \"Century Gothic\";");
        choiceConductor.setOnAction(event -> {
            Conductor conductor = dao.getOne(choiceConductor.getValue());
            if(conductor.getLicencia()==0){
                for(int i=0;i<this.conductors.size();i++){
                    if(conductors.get(i).getNit().equals(this.choiceConductor.getValue()))
                        conductor = this.conductors.get(i);
                        this.conductorInDB=false;
                        this.conductor=conductor;
                }
            }else{
                this.conductorInDB=true;
            }
            this.nombreLbl.setText(conductor.getNombre());
            this.apellidoLbl.setText(conductor.getApellido());
            this.telefonoLbl.setText(String.valueOf(conductor.getTelefono()));
            this.licenciaLbl.setText(String.valueOf(conductor.getLicencia()));
            circleImg.setOpacity(1);
            getConductorImg(conductor.getImagen());
        });
    }

    private void insertDocuments() {
        carDAO dao=new carDAO();
        for(int i=0;i<this.documents.size();i++){
            dao.insertDoc(this.documents.get(i),this.placa);
        }
    }

    private void insertImgs() {
        imagesDAO dao=new imagesDAO();
        dao.insert(this.imgs, this.placa);
    }

    private void insertConductor() {
        conductorDAO dao=new conductorDAO();
        dao.insert(this.conductor);
    }

    private boolean validate() {
        boolean valid=true;
        if(placaTxt.getText().isEmpty() || kmTxt.getText().isEmpty() || colorTxt.getText().isEmpty() || marcaTxt.getText().isEmpty() || modeloTxt.getText().isEmpty() || chasisTxt.getText().isEmpty() || capacidadTxt.getText().isEmpty() || tipoTxt.getText().isEmpty() || choiceConductor.getValue()==null){
            valid=false;
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Todos los campos del apartado de datos son obligatorios, al igual que el conductor designado");
            alert.showAndWait();
        }else if(check(kmTxt.getText()) || check(modeloTxt.getText()) || check(capacidadTxt.getText())){
            valid=false;
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Solo se aceptan números en los campos de kilometraje, modelo y capacidad");
            alert.showAndWait();
        }else if(checkDocument()) {
            valid=false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Ya existe un automovil registrado con esta placa");
            alert.showAndWait();
        }
        return valid;
    }

    private boolean checkDocument() {
        carDAO dao=new carDAO();
        List<Car> cars= dao.getAll();
        for(int i=0;i<cars.size();i++){
            if(cars.get(i).getPlaca().equals(this.placa)){
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

    private void getConductorImg(String imagen) {
        Image img;
        if(imagen==null){
            circleImg.setOpacity(0.01);
        }else{
            String imageDataBytes = imagen.substring(imagen.indexOf(",")+1);
            InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
            img=new Image(stream);
            if(img==null)
                circleImg.setOpacity(0.01);
            circleImg.setFill(new ImagePattern(img));
        }
    }

    private Image getImage(String image) {
        Image img;
        String imageDataBytes = image.substring(image.indexOf(",") + 1);
        InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
        img = new Image(stream);
        return img;
    }

    private void toBase() {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            BufferedImage img = ImageIO.read(new File(this.selectedFile.getPath()));
            ImageIO.write(img, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = "data:image/jpeg;base64," + encoder.encode(imageBytes).replace("\n", "").replace("\r", "");
            bos.close();
            switch (this.active) {
                case 1:
                    this.imgs.setFrontal(imageString);
                    break;
                case 2:
                    this.imgs.setTrasera(imageString);
                    break;
                case 3:
                    this.imgs.setLatIzq(imageString);
                    break;
                case 4:
                    this.imgs.setLatDer(imageString);
                    break;
                case 5:
                    this.imgs.setCabin(imageString);
                    break;
            }
        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error cargando la imagen seleccionada");
            alert.showAndWait();
        }
    }

    public void setConductor(Conductor conductor) {
        this.conductors.add(conductor);
        this.conductorInDB=false;
        choiceConductor.getItems().add(conductor.getNit());
        choiceConductor.setValue(conductor.getNit());
        nombreLbl.setText(conductor.getNombre());
        apellidoLbl.setText(conductor.getApellido());
        telefonoLbl.setText(String.valueOf(conductor.getTelefono()));
        licenciaLbl.setText(String.valueOf(conductor.getLicencia()));
        if(conductor.getImagen()!=null)
            this.setImage(conductor.getImagen());
    }

    private void setImage(String imagen) {
        Image image=getImg(imagen);
        if (imagen!=null){
            circleImg.setOpacity(1);
            circleImg.setFill(new ImagePattern(image));
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

    public void addDoc(Document document) {
        this.documents.add(document);
        switch (document.getName()) {
            case "SOAT":
                soatLbl.setText(document.getId());
                break;
            case "Tecnomecanica":
                tecnomecanicaLbl.setText(document.getId());
                break;
            case "Tarjeta de propiedad":
                tarjetaLbl.setText(document.getId());
                break;
        }
    }
}
