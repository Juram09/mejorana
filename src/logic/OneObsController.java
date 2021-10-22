package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import models.Observation;
import models.carDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class OneObsController {

   @FXML
   private Label obsLbl;

   @FXML
   private Label dateLbl;

   @FXML
   private Button deleteObs;

   long id;
   private Observation observation;
   private OneCarSecondController controller;

   @FXML
   void delete(ActionEvent event) {
      Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Eliminación de observaciones");
      alert.setHeaderText(null);
      alert.setContentText("¿Desea eliminar la observacion seleccionada?");
      ButtonType buttonTypeYes = new ButtonType("Sí");
      ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
      alert.getButtonTypes().setAll(buttonTypeYes,buttonTypeCancel);
      Optional<ButtonType> result=alert.showAndWait();
      if(result.get()==buttonTypeYes){
         carDAO dao=new carDAO();
         dao.deleteObs(this.id);
         this.controller.update();
      }

   }

   public void setObs(Observation observation,OneCarSecondController controller){
      this.controller=controller;
      this.id=observation.getId();
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      this.dateLbl.setText(dateFormat.format(observation.getDate()));
      this.obsLbl.setText(observation.getObservation());
   }

}
