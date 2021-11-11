package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import models.Tank;
import models.carDAO;

import java.util.Optional;

public class AddTanqController {

   @FXML
   private Button exitBtn;

   @FXML
   private TextField kmTxt;

   @FXML
   private DatePicker dateTxt;

   @FXML
   private TextField galonTxt;

   @FXML
   private Button addBtn;

   private String placa;

   @FXML
   void addTanq(ActionEvent event) {
      if(Validate()){
         Tank tank=new Tank();
         tank.setKm(Long.parseLong(kmTxt.getText()));
         tank.setGalones(Long.parseLong(galonTxt.getText()));
         carDAO dao= new carDAO();
         Tank tanq=dao.getTank(this.placa);
         try{
            Long kmpGalon=((tank.getKm()-tanq.getKm())/tanq.getGalones());
            dao.updateTank(tanq.getId(),kmpGalon);
            dao.insertTank(tank, java.sql.Date.valueOf(dateTxt.getValue()), this.placa);
         }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error añadiendo el tanqueo");
            alert.showAndWait();
         }
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

   private boolean Validate(){
      boolean valid=true;
      boolean numeric = true;

      try {
         Double num = Double.parseDouble(this.galonTxt.getText());
         Double nmbr = Double.parseDouble(this.kmTxt.getText());
      } catch (NumberFormatException e) {
         numeric = false;
      }

      if(this.kmTxt.getText().isEmpty() || this.galonTxt.getText().isEmpty() || !numeric || this.dateTxt.getValue()==null){
         Alert alert=new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText(null);
         alert.setContentText("Todos los campos son obligatorios y el tanqueo y el numero de galones deben ser numeros");
         alert.showAndWait();
         valid=false;
      }
      return valid;
   }

   public void setCar(String placa){
      this.placa=placa;
   }
}