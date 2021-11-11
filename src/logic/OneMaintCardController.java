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
import models.Maintenance;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OneMaintCardController {

    @FXML
    private Label tipoLbl;

    @FXML
    private Label kmLbl;

    @FXML
    private Label fechaLbl;

    @FXML
    private Label descripcionLbl;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button goMaintBtn;

    @FXML
    private Label nextLbl;

    private Maintenance maintenance;
    private String placa;
    private ViewMaintsController controller;
    private ViewAllMaintsController controller1;
    private Long actualKm;

    @FXML
    void delete(ActionEvent event) throws IOException {
        if(this.controller!=null){
            this.controller.deleteMaint(this.maintenance);
            this.controller.updateMaints(this.placa);
        }else{
            this.controller1.deleteMaint(this.maintenance);
            this.controller1.updateMaints(this.placa);
        }
    }

    @FXML
    void goMaint(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/viewOneMaint.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 755, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        ViewOneMaintController controller=fxmlLoader.getController();
        controller.setMaint(this.maintenance,this.actualKm);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    public void setData(Maintenance maintenance, String placa, ViewMaintsController controller, Long km){
        this.maintenance=maintenance;
        this.placa=placa;
        this.controller=controller;
        this.actualKm=km;
        tipoLbl.setText(maintenance.getTipo());
        kmLbl.setText(maintenance.getKm().toString());
        fechaLbl.setText(getFinalDate(maintenance));
        descripcionLbl.setText(maintenance.getDescripcion());
    }
    public void setData(Maintenance maintenance,String placa, ViewAllMaintsController controller, Long km){
        this.maintenance=maintenance;
        this.placa=placa;
        this.controller1=controller;
        this.actualKm=km;
        tipoLbl.setText(maintenance.getTipo());
        kmLbl.setText(maintenance.getKm().toString());
        fechaLbl.setText(getFinalDate(maintenance));
        descripcionLbl.setText(maintenance.getDescripcion());
    }

    private String getFinalDate(Maintenance maintenance){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(maintenance.getProx()!=0){
            Long nxt=maintenance.getProx()-this.actualKm;
            if(nxt>7000){
                nextLbl.setTextFill(Color.GREEN);
            }else if (nxt>3000){
                nextLbl.setTextFill(Color.valueOf("#fecb02"));
            }else{
                nextLbl.setTextFill(Color.RED);
            }
            if(!maintenance.isActive()) {
                nextLbl.setTextFill(Color.RED);
                nextLbl.setText("Este mantenimiento no se encuentra activo");
            }else{
                nextLbl.setText("Faltan "+nxt+" km para el proximo mantenimiento de "+tipoLbl.getText());
            }
        }
        if(!maintenance.isActive()) {
            nextLbl.setTextFill(Color.RED);
            nextLbl.setText("Este mantenimiento no se encuentra activo");
        }
        return dateFormat.format(maintenance.getFecha());
    }
}
