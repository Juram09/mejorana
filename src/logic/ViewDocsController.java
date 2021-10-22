package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Document;
import models.carDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewDocsController {

    @FXML
    private GridPane grid;

    @FXML
    private Button exitDocButton;

    @FXML
    private Button viewAllBtn;

    @FXML
    private Button addDocBtn;

    private String placa;

    private List<Document> docs;
    private int row;
    private List<Document> pDocs;

    @FXML
    void exitDoc(ActionEvent event) {
        if(this.docs.equals(this.pDocs)){
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        else{
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Actualización de datos");
            alert.setHeaderText(null);
            alert.setContentText("¿Desea confirmar los cambios efectuados en los documentos?");
            ButtonType buttonTypeYes = new ButtonType("Sí");
            ButtonType buttonTypeNo = new ButtonType("No");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
            Optional<ButtonType> result=alert.showAndWait();
            if(result.get()==buttonTypeYes){
                updateDocs();
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }else if(result.get()==buttonTypeNo){
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        }
    }

    @FXML
    void goAddDoc(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/addDocument.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 750, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        AddDocumentController controller=fxmlLoader.getController();
        controller.setCar(this.placa, this);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    void goViewAllDoc(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/viewAllDocs.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 535, 535);
        stage.setScene(scene);
        stage.setTitle("La mejorana");
        stage.setResizable(false);
        ViewAllDocsController controller=fxmlLoader.getController();
        controller.setDocs(this.placa, this.docs,this);
        stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    void setDocs(String placa){
        this.placa=placa;
        carDAO dao=new carDAO();
        this.docs=dao.getDocs(placa);
        organize(docs);
        this.pDocs= new ArrayList<>(this.docs);
        try{
            for(int i=0;i<docs.size();i++){
                FXMLLoader fxmlLoader= new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneDocCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                if(docs.get(i).isActive()){
                    OneDocCardController controller=fxmlLoader.getController();
                    controller.setData(docs.get(i),placa,this);
                    grid.add(anchorPane,0,this.row++);
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane,new Insets(10));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDoc(Document document) throws IOException {
        for(int i=0;i<docs.size();i++){
            if(docs.get(i).getName().equals(document.getName()) && docs.get(i).isActive()){
                Document doc=new Document(docs.get(i));
                this.docs.add(doc);
                doc.setActive(false);
                this.docs.remove(i);
                break;
            }
        }
        this.docs.add(document);
    }

    public void deleteDoc(Document document) throws IOException {
        this.docs.remove(document);
    }

    private void organize(List<Document> docs){
        Document aux;
        if(docs.size()>3){
            for (int i = 0; i < docs.size(); i++) {
                if (docs.get(i).getName().equalsIgnoreCase("tarjeta de propiedad") && (docs.get(i).isActive()) ) {
                    aux = docs.get(0);
                    docs.set(0, docs.get(i));
                    docs.set(i, aux);
                } else if (docs.get(i).getName().equalsIgnoreCase("soat") && docs.get(i).isActive()) {
                    aux = docs.get(1);
                    docs.set(1, docs.get(i));
                    docs.set(i, aux);
                } else if (docs.get(i).getName().equalsIgnoreCase("tecnomecanica") && docs.get(i).isActive()) {
                    aux = docs.get(2);
                    docs.set(2, docs.get(i));
                    docs.set(i, aux);
                }
            }
        }
    }

    public void updateDocs(String placa){
        grid.getChildren().clear();
        organize(this.docs);
        try{
            for(int i=0;i<docs.size();i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ui/oneDocCard.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                if(docs.get(i).isActive()){
                    OneDocCardController controller = fxmlLoader.getController();
                    controller.setData(docs.get(i), placa, this);

                    grid.add(anchorPane, 0, this.row++);
                    //set grid width
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    //set grid height
                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateDocs(){
        carDAO dao=new carDAO();
        for(int i=0;i<this.pDocs.size();i++){
            boolean updated=false;
            for(int j=0;j<this.docs.size();j++){
               if(pDocs.get(i).equals(docs.get(j))){
                    updated=true;
                    break;
                }else if(pDocs.get(i).getId().equals(docs.get(j).getId()) && pDocs.get(i).getName().equals(docs.get(j).getName()) && !(pDocs.get(i).isActive()==(docs.get(j).isActive()))){
                    updated=true;
                    dao.updateDoc(docs.get(j),this.placa);
                    break;
                }
            }
            if(!updated){
                dao.deleteDoc(this.placa,this.pDocs.get(i).getName(),this.pDocs.get(i).getId());
            }
        }
        for(int i=0;i<this.docs.size();i++){
            boolean updated=false;
            for(int j=0;j<this.pDocs.size();j++){
                if(docs.get(i).equals(pDocs.get(j))){
                    updated=true;
                    break;
                }else if(pDocs.get(j).getId().equals(docs.get(i).getId()) && pDocs.get(j).getName().equals(docs.get(i).getName()) && (pDocs.get(j).isActive()!=(docs.get(i).isActive()))){
                    updated=true;
                    break;
                }
            }
            if(!updated){
                dao.insertDoc(this.docs.get(i),this.placa);
            }
        }
    }
}
