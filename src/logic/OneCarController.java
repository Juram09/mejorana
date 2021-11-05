package logic;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import logic.ViewMaintsController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class OneCarController{

    @FXML
    private ImageView carImg;

    @FXML
    private Button editButton;

    @FXML
    private Button viewMantButton;

    @FXML
    private Button viewDocButton;

    @FXML
    private Button addTanqButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button showFrontalButton;

    @FXML
    private Button showBackButton;

    @FXML
    private Button showRightButton;

    @FXML
    private Button showLeftButton;

    @FXML
    private Button showCabinButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button goMenuButton;

    @FXML
    private Label placasLabel;

    @FXML
    private Label kmLabel;

    @FXML
    private Label colorLabel;

    @FXML
    private Label marcaLabel;

    @FXML
    private Label modeloLabel;

    @FXML
    private Label chasisLabel;

    @FXML
    private Label capacidadLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Button nextpgButton;

    @FXML
    private ImageView blackUnfocus;

    @FXML
    private LineChart<?, ?> grafica;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private Car car;
    private String placa;

    @FXML
    void addTanq(ActionEvent event) throws IOException {
        this.blackUnfocus.setOpacity(1);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/addTanq.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 690, 422);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        AddTanqController controller =fxmlLoader.getController();
        controller.setCar(this.placasLabel.getText());
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        this.blackUnfocus.setOpacity(0);
        this.setCar(this.placa);
    }

    @FXML
    void nextPg(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/oneCarSecond.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        OneCarSecondController controller=fxmlLoader.getController();
        controller.setCar(this.placa,Long.parseLong(this.kmLabel.getText()));
        stage.show();
    }

    @FXML
    void viewDoc(ActionEvent event) throws IOException {
        this.blackUnfocus.setOpacity(1);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/viewDocs.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 535, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        ViewDocsController controller=fxmlLoader.getController();
        controller.setDocs(this.placasLabel.getText());
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        this.blackUnfocus.setOpacity(0);
    }

    @FXML
    void viewMant(ActionEvent event) throws IOException {
        this.blackUnfocus.setOpacity(1);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/viewMaints.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 535, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        ViewMaintsController controller=fxmlLoader.getController();
        controller.setMaints(this.placasLabel.getText(),Long.parseLong(kmLabel.getText()));
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        this.blackUnfocus.setOpacity(0);
    }

    @FXML
    void goEdit(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/editCar.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        EditCarController controller=fxmlLoader.getController();
        controller.setCar(this.placasLabel.getText());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root,1280,720);
        stage.setScene(scene);
        stage.setTitle ("La mejorana");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void delete(ActionEvent event) throws IOException {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro que desea eliminar este automovil de manera permanente?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            carDAO dao=new carDAO();
            dao.delete(placasLabel.getText());
            goBack(event);
        }
    }

    @FXML
    void showBack(ActionEvent event) {
        show(this.car.getImgs().getTrasera());
    }

    @FXML
    void showCabin(ActionEvent event) {
        show(this.car.getImgs().getCabin());
    }

    @FXML
    void showFrontal(ActionEvent event) {
        show(this.car.getImgs().getFrontal());
    }

    @FXML
    void showLeft(ActionEvent event) {
        show(this.car.getImgs().getLatIzq());
    }

    @FXML
    void showRight(ActionEvent event) {
        show(this.car.getImgs().getLatDer());
    }

    public void setCar(String placa){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.placa=placa;
        try{
            carDAO dao=new carDAO();
            this.car=dao.getOne(this.placa);
            placasLabel.setText(this.placa);
            kmLabel.setText(String.valueOf(car.getKm()));
            colorLabel.setText(String.valueOf(car.getColor()));
            marcaLabel.setText(String.valueOf(car.getMarca()));
            modeloLabel.setText(String.valueOf(car.getModelo()));
            chasisLabel.setText(String.valueOf(car.getChasis()));
            capacidadLabel.setText(String.valueOf(car.getCapacidad()));
            tipoLabel.setText(car.getTipo());
            show(car.getImgs().getFrontal());
            List<Tank> tanks=dao.getTanks(this.placa);
            XYChart.Series series=new XYChart.Series();
            for(int i=tanks.size()-1;i>0;i--){
                series.getData().add(new XYChart.Data<>(dateFormat.format(tanks.get(i).getFecha()),tanks.get(i).getGalonesPerKm()));
            }
            series.setName("Galones por km recorrido");
            grafica.getData().addAll(series);
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error cargando el automovil seleccionada");
            alert.showAndWait();
        }
    }
    private void show(String image){
        Image img;
        try{
            if (image==null){
                img=new Image("imgs/NOT.jpg");
            }else{
                String imageDataBytes = image.substring(image.indexOf(",")+1);
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
                img=new Image(stream);
            }
            carImg.setPreserveRatio(false);
            carImg.setImage(img);
        }
        catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error cargando la imagen seleccionada");
            alert.showAndWait();
        }
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/principalCar.fxml"));
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
}
