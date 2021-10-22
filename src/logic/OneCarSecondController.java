package logic;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class OneCarSecondController{

   @FXML
   private GridPane grid;

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
   private Button goBackButton;

   @FXML
   private Button exitButton;

   @FXML
   private Button goMenuButton;

   @FXML
   private Button nextpgButton;

   @FXML
   private Label documentoLbl;

   @FXML
   private Label nombreLbl;

   @FXML
   private Label apellidoLbl;

   @FXML
   private Label TelefonoLbl;

   @FXML
   private Label LicenciaLbl;

   @FXML
   private Label placaLbl;

   @FXML
   private Label obsLbl;

   @FXML
   private ScrollPane scroll;

   @FXML
   private TableView<Tank> table;

   @FXML
   private TableColumn<Tank, Date> dateColumn=new TableColumn<>();

   @FXML
   private TableColumn<Tank, Long> kmColumn=new TableColumn<>();

   @FXML
   private TableColumn<Tank, Long> tanqueoColumn=new TableColumn<>();

   @FXML
   private TableColumn<Tank, Long> galonPKmColumn=new TableColumn<>();

   @FXML
   private Circle conductorImg;

   @FXML
   private Button addBtn;

   @FXML
   private TextArea obsTxt;

   @FXML
   private ImageView blackUnfocus;

   carDAO dao=new carDAO();
   private List<Observation> observations;

   @FXML
   void addTanq(ActionEvent event) {

   }

   @FXML
   void addObs(ActionEvent event) {
      this.dao.insertObs(this.obsTxt.getText(),this.placaLbl.getText());
      this.obsTxt.setText("");
      //TODO: Alert
      this.update();
   }

   @FXML
   void delete(ActionEvent event) {

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
   void goEdit(ActionEvent event) {

   }

   @FXML
   void goMenu(ActionEvent event) throws IOException {
      goBack(event);
   }

   @FXML
   void nextPg(ActionEvent event) throws IOException {
      ((Node)(event.getSource())).getScene().getWindow().hide();
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/oneCar.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      OneCarController controller=fxmlLoader.getController();
      controller.setCar(placaLbl.getText());
      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      Scene scene = new Scene(root,1280,720);
      stage.setScene(scene);
      stage.setTitle ("La mejorana");
      stage.setResizable(false);
      stage.show();
   }

   @FXML
   void viewDoc(ActionEvent event) {

   }

   @FXML
   void viewMant(ActionEvent event) {

   }

   public void setCar(String placa){
      this.placaLbl.setText(placa);
      this.observations = this.dao.getObs(placa);
      int row = 0;
      if (observations.size() > 0) {
         this.obsLbl.setOpacity(0);
         this.scroll.setOpacity(1);
         try {
            for (int i = 0; i < observations.size(); i++) {
               FXMLLoader fxmlLoader = new FXMLLoader();
               fxmlLoader.setLocation(getClass().getResource("/ui/oneObs.fxml"));
               AnchorPane anchorPane = fxmlLoader.load();
               OneObsController controller = fxmlLoader.getController();
               controller.setObs(observations.get(i), this);
               grid.add(anchorPane, 0, row++);

               //set grid width
               grid.setMinWidth(Region.USE_COMPUTED_SIZE);
               grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
               grid.setMaxWidth(Region.USE_PREF_SIZE);

               //set grid height
               grid.setMinHeight(Region.USE_COMPUTED_SIZE);
               grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
               grid.setMaxHeight(Region.USE_PREF_SIZE);
               GridPane.setMargin(anchorPane, new Insets(0, 0, 5, 0));
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      } else {
         this.obsLbl.setOpacity(1);
         this.scroll.setOpacity(0);
      }
      conductorDAO cdao = new conductorDAO();
      Conductor conductor = cdao.getOne(dao.getConductor(placa));
      documentoLbl.setText(String.valueOf(conductor.getNit()));
      nombreLbl.setText(String.valueOf(conductor.getNombre()));
      apellidoLbl.setText(String.valueOf(conductor.getApellido()));
      TelefonoLbl.setText(String.valueOf(conductor.getTelefono()));
      LicenciaLbl.setText(String.valueOf(conductor.getLicencia()));
      show(conductor.getImagen());
      fill();
   }

   public void update() {
      grid.getChildren().clear();
      setCar(this.placaLbl.getText());
   }

   private void show(String image){
      Image img;
      try{
         if (image==null){
               img=new Image("imgs/NOTC.jpg");
         }else{
            String imageDataBytes = image.substring(image.indexOf(",")+1);
            InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
            img=new Image(stream);
         }
         conductorImg.setFill(new ImagePattern(img));
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void fill(){
      carDAO dao=new carDAO();
      List<Tank> tanks=dao.getTanks(this.placaLbl.getText());
      ObservableList<Tank> tanqs = FXCollections.observableArrayList(tanks);
      kmColumn.setCellValueFactory(new PropertyValueFactory<>("km"));
      tanqueoColumn.setCellValueFactory(new PropertyValueFactory<>("galones"));
      galonPKmColumn.setCellValueFactory(new PropertyValueFactory<>("galonesPerKm"));
      dateColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
      table.setItems(tanqs);
   }
}

