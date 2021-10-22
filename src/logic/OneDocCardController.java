package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Document;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class OneDocCardController {

   @FXML
   private Button deleteBtn;

   @FXML
   private Label nameLbl;

   @FXML
   private Label idLbl;

   @FXML
   private Label dateLbl;

   @FXML
   private Label vencimiento;

   @FXML
   private Button view;

   private Document document;
   private String placa;
   private ViewDocsController controller;
   private ViewAllDocsController controller1;

   @FXML
   void goDocument(ActionEvent event) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/viewOneDoc.fxml"));
      Parent root = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.initStyle(StageStyle.UNDECORATED);
      Scene scene = new Scene(root, 750, 535);
      stage.setScene(scene);
      stage.setTitle("La mejorana");
      stage.setResizable(false);
      ViewOneDocController controller=fxmlLoader.getController();
      controller.setDoc(this.document,this.placa);
      stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
      stage.initModality(Modality.WINDOW_MODAL);
      stage.show();
   }

   @FXML
   void delete(ActionEvent event) throws IOException {
      if(this.controller!=null){
         this.controller.deleteDoc(this.document);
         this.controller.updateDocs(this.placa);
      }else{
         this.controller1.deleteDoc(this.document);
         this.controller1.updateDocs(this.placa);
      }
   }

   public void setData(Document document,String placa, ViewDocsController controller){
      this.document=document;
      this.placa=placa;
      this.controller=controller;
      nameLbl.setText(document.getName());
      idLbl.setText(document.getId());
      dateLbl.setText(getFinalDate(document));
   }

   public void setData(Document document,String placa, ViewAllDocsController controller){
      this.document=document;
      this.placa=placa;
      this.controller1=controller;
      nameLbl.setText(document.getName());
      idLbl.setText(document.getId());
      dateLbl.setText(getFinalDate(document));
   }

   private String getFinalDate(Document document){
      String finalDate;
      DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
      if (document.getDateOut()!=null) {
         finalDate = dateFormat.format(document.getDate()) + " - " + dateFormat.format(document.getDateOut());
         Date now= java.sql.Date.valueOf(LocalDate.now());
         long days=(document.getDateOut().getTime()-now.getTime())/86400000;
         if(days>180){
            System.out.println("COLOR LA PTM");
            vencimiento.setTextFill(Color.GREEN);
         }else if(days>60){
            vencimiento.setTextFill(Color.valueOf("#fecb02"));
         }else{
            vencimiento.setTextFill(Color.RED);
         }
         if(!document.isActive()) {
            vencimiento.setTextFill(Color.RED);
            vencimiento.setText("Este documento no se encuentra activo");
         }else{
            vencimiento.setText("El documento se vencerá en menos de "+((days/30)+1)+" meses ("+ days+" dias)");
         }
         return finalDate;
      }
      return dateFormat.format(document.getDate());
   }
}
