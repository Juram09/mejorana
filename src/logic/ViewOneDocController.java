package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import models.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

public class ViewOneDocController {

    @FXML
    private ImageView img;

    @FXML
    private Label nombreLbl;

    @FXML
    private Label idLbl;

    @FXML
    private Label placaLbl;

    @FXML
    private Label fechaLbl;

    @FXML
    private Button exitBtn;

    @FXML
    private Label vencimientoLbl;

    @FXML
    void exit(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
    public void setDoc(Document document,String placa){
        nombreLbl.setText(document.getName());
        idLbl.setText(document.getId());
        placaLbl.setText(placa);
        fechaLbl.setText(getFinalDate(document));
        setImg(document.getImg());
    }

    private String getFinalDate(Document document){
        String finalDate;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (document.getDateOut()!=null) {
            finalDate = dateFormat.format(document.getDate()) + " - " + dateFormat.format(document.getDateOut());
            Date now= java.sql.Date.valueOf(LocalDate.now());
            long days=(document.getDateOut().getTime()-now.getTime())/86400000;
            if(days>180 && days<365){
                vencimientoLbl.setTextFill(Color.GREEN);
            }else if(days>60){
                vencimientoLbl.setTextFill(Color.valueOf("#fecb02"));
            }else{
                vencimientoLbl.setTextFill(Color.RED);
            }
            vencimientoLbl.setText("El documento se vencerá en menos de "+((days/30)+1)+" meses ("+ days+" dias)");
            return finalDate;
        }
        return dateFormat.format(document.getDate());
    }

    private void setImg(String image){
        Image Img;
        try{
            if (image==null){
                Img=new Image("imgs/NOTD.jpg");
            }else{
                String imageDataBytes = image.substring(image.indexOf(",")+1);
                InputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(imageDataBytes.getBytes()));
                Img=new Image(stream);
            }
            img.setPreserveRatio(false);
            img.setImage(Img);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
