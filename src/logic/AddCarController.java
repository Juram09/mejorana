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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Car;
import models.Conductor;
import models.Images;
import models.conductorDAO;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

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

    private Car car;
    private Conductor conductor;
    private Images imgs;
    private int active;
    private File selectedFile;

    @FXML
    void add(ActionEvent event) {
        //TODO: VALIDATOR, INSERT
    }

    @FXML
    void addConductor(ActionEvent event) {
        //TODO: VENTANA
    }

    @FXML
    void addDocument(ActionEvent event) {
        //TODO: VENTANA
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
            //TODO: Ventana emergente error
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
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
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

    @FXML
    void goMenu(ActionEvent event) throws IOException {
        goBack(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.active = 1;
        this.imgs = new Images();
        conductorDAO dao = new conductorDAO();
        List<Conductor> conductors = dao.getAll();
        for (int i = 0; i < conductors.size(); i++)
            this.choiceConductor.getItems().add(conductors.get(i).getNit());
        this.choiceConductor.setStyle("-fx-font: 16px \"Century Gothic\";");
        choiceConductor.setOnAction(event -> {
            Conductor conductor = dao.getOne(choiceConductor.getValue());
            this.nombreLbl.setText(conductor.getNombre());
            this.apellidoLbl.setText(conductor.getApellido());
            this.telefonoLbl.setText(String.valueOf(conductor.getTelefono()));
            this.licenciaLbl.setText(String.valueOf(conductor.getLicencia()));
            circleImg.setOpacity(1);
            getConductorImg(conductor.getImagen());
        });

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
            //TODO
            System.out.println("No hay imagen");
            //e.printStackTrace();
        }
    }

}
