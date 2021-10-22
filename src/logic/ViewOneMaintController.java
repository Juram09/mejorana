package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import models.Maintenance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class ViewOneMaintController {

    @FXML
    private ImageView Img;

    @FXML
    private Button exitMaintBtn;

    @FXML
    private Label tipoLbl;

    @FXML
    private Label kmLbl;

    @FXML
    private Label fechaLbl;

    @FXML
    private Label nextLbl;

    @FXML
    private Label descripcionLbl;

    private Long actualKm;
    private Maintenance maint;

    @FXML
    void exit(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    public void setMaint(Maintenance maint, Long actualKm){
        this.maint=maint;
        this.actualKm=actualKm;
        this.tipoLbl.setText(maint.getTipo());
        this.kmLbl.setText(Long.toString(maint.getKm()));
        this.fechaLbl.setText(getFinalDate());
        this.descripcionLbl.setText(maint.getDescripcion());
        this.setImg(maint.getImagen());
    }

    private String getFinalDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(this.maint.getProx()!=0){
            Long nxt=this.maint.getProx()-this.actualKm;
            if(nxt>7000){
                nextLbl.setTextFill(Color.GREEN);
            }else if (nxt>3000){
                nextLbl.setTextFill(Color.valueOf("#fecb02"));
            }else{
                nextLbl.setTextFill(Color.RED);
            }
            if(!this.maint.isActive()) {
                nextLbl.setTextFill(Color.RED);
                nextLbl.setText("Este documento no se encuentra activo");
            }else{
                nextLbl.setText("Faltan "+nxt+" km para el proximo mantenimiento de "+tipoLbl.getText());
            }
        }
        if(!this.maint.isActive()) {
            nextLbl.setTextFill(Color.RED);
            nextLbl.setText("Este documento no se encuentra activo");
        }
        return dateFormat.format(this.maint.getFecha());
    }

    private void setImg(String image){
        Image img;
        try{
            if (image==null){
                img=new Image("imgs/NOTD.jpg");
            }else{
                String imageDataBytes = image.substring(image.indexOf(",")+1);
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
                img=new Image(stream);
            }
            Img.setPreserveRatio(false);
            Img.setImage(img);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}

