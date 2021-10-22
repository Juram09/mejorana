package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Tank;
import models.carDAO;

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
         }catch(Exception e){
         }
         dao.insertTank(tank, java.sql.Date.valueOf(dateTxt.getValue()), this.placa);
      }else{
         //TODO: ALERT
      }
   }

   @FXML
   void exit(ActionEvent event) {
      ((Node)(event.getSource())).getScene().getWindow().hide();
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
         valid=false;
      }
      return valid;
   }

   public void setCar(String placa){
      this.placa=placa;
   }
}