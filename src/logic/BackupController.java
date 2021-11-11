package logic;

import database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class BackupController {

    @FXML
    private Button exitBtn;

    @FXML
    private Button importBtn;

    @FXML
    private Button exportBtn;

    @FXML
    private Button logsBtn;

    private File selectedFile;
    boolean isData=true;
    private int conductors=0;
    private int cars=0;
    private int images=0;
    private int documents=0;
    private int maintenances=0;
    private int observations=0;
    private int tanks=0;
    private List<Conductor> conductors1;
    private List<Car> cars1;
    private List<Images> images1;
    private List<Document> documents1;
    private List<Maintenance> maints;
    private List<Observation> obs;
    private List<Tank> tanks1;


    @FXML
    void exit(ActionEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ui/principalCar.fxml"));
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
    void export(ActionEvent event){
        try{
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar backup");
            Stage stage = (Stage) exportBtn.getScene().getWindow();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SQL extensions (*.sql)", "*.sql"));
            File dir = fc.showSaveDialog(stage);
            File file = new File(dir.getParent(), dir.getName().replace(getExtension(dir),"").replace(".","")+".sql");
            FileWriter fw = null;
            if (file != null) {
                try {
                    fw = new FileWriter(file);
                    fw.write("--BACKUP LA MEJORANA\n DELETE FROM documento;\n DELETE FROM imagenes;\n DELETE FROM mantenimiento;\n DELETE FROM observacion;\n DELETE FROM tanqueo;\n DELETE FROM car;\n DELETE FROM conductor;\n");
                    getDB();
                    while(this.isData){
                        fw.write(getStrings()+"\n");
                    }
                    fw.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if(fw != null) {
                        try {
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Backup exportado correctamente");
                            alert.setHeaderText(null);
                            alert.setContentText("El backup ha sido exportado");
                            alert.showAndWait();
                            fw.close();
                            exit(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Selección de backup");
                alert.setHeaderText(null);
                alert.setContentText("No se ha seleccionado ningun backup");
                alert.showAndWait();
            }
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creando backup");
            alert.showAndWait();
        }
    }

    @FXML
    void viewLogs(ActionEvent event) throws IOException {
        String data="";
        try {
            File myObj = new File(""+getClass().getClassLoader().getResource("./logs/logs.log"));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e){
            data="No hay ningun log asociado al software";
        }
        try{
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar log");
            Stage stage = (Stage) logsBtn.getScene().getWindow();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("log extensions (*.log)", "*.log"));
            File dir = fc.showSaveDialog(stage);
            File file = new File(dir.getParent(), dir.getName().replace(getExtension(dir),"").replace(".","")+".log");
            FileWriter fw = null;
            if (file != null) {
                try {
                    fw = new FileWriter(file);
                    fw.write(data);
                    fw.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if(fw != null) {
                        try {
                            Alert alert=new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Archivo log exportado correctamente");
                            alert.setHeaderText(null);
                            alert.setContentText("El archivo log ha sido exportado");
                            alert.showAndWait();
                            fw.close();
                            exit(event);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Selección de logs");
                alert.setHeaderText(null);
                alert.setContentText("No se ha seleccionado ningun log");
                alert.showAndWait();
            }
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creando logs");
            alert.showAndWait();
        }
    }

    @FXML
    void imprt(ActionEvent event) throws IOException {
        try{
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar backup");
            Stage stage = (Stage) exportBtn.getScene().getWindow();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("SQL extensions (*.sql)", "*.sql"));
            this.selectedFile = fc.showOpenDialog(stage);
            if (this.selectedFile != null) {
                Connection connection=new DBConnection().getConnection();
                ScriptRunner sr = new ScriptRunner(connection);
                Reader reader = new BufferedReader(new FileReader(selectedFile));
                sr.runScript(reader);
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Backup cargado correctamente");
                alert.setHeaderText(null);
                alert.setContentText("El backup ya ha sido cargado");
                alert.showAndWait();
            } else {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Selección de backup");
                alert.setHeaderText(null);
                alert.setContentText("No se ha seleccionado ningun backup");
                alert.showAndWait();
            }
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error cargando backup");
            alert.showAndWait();
        }finally {
            exit(event);
        }
    }

    private String getStrings() {
        String db="";
        if(this.conductors<conductors1.size()){
            db="INSERT INTO conductor(nit, documento, nombre, apellido, telefono, licencia, imagen) VALUES ('"+conductors1.get(this.conductors).getNit()+"', '"+conductors1.get(this.conductors).getDocumento()+"', '"+conductors1.get(this.conductors).getNombre()+"', '"+conductors1.get(this.conductors).getApellido()+"', "+conductors1.get(this.conductors).getTelefono()+", "+conductors1.get(this.conductors).getLicencia()+", '"+conductors1.get(this.conductors).getImagen()+"');";
            this.conductors+=1;
        }else if(this.cars<cars1.size()){
            db="INSERT INTO car(placa, km, color, marca, modelo, chasis, capacidad, tipo, conductor) VALUES ('"+cars1.get(this.cars).getPlaca()+"', "+cars1.get(this.cars).getKm()+", '"+cars1.get(this.cars).getColor()+"', '"+cars1.get(this.cars).getMarca()+"', "+cars1.get(this.cars).getModelo()+", '"+cars1.get(this.cars).getChasis()+"', "+cars1.get(this.cars).getCapacidad()+", '"+cars1.get(this.cars).getTipo()+"', '"+cars1.get(this.cars).getConductor()+"');";
            this.cars+=1;
        }else if(this.images<images1.size()){
            db="INSERT INTO imagenes(placa, frontal, trasera, izquierda, derecha, cabina) VALUES ('"+images1.get(this.images).getPlaca()+"', '"+images1.get(this.images).getFrontal()+"', '"+images1.get(this.images).getTrasera()+"', '"+images1.get(this.images).getLatIzq()+"', '"+images1.get(this.images).getLatDer()+"', '"+images1.get(this.images).getCabin()+"');";
            this.images+=1;
        }else if(this.documents<documents1.size()){
            db="INSERT INTO documento(id, nombre, placa, fecha_in, imagen, descripcion, fecha_out, active) VALUES ("+documents1.get(this.documents).getId()+", '"+documents1.get(this.documents).getName()+"', '"+documents1.get(this.documents).getPlaca()+"', '"+new java.sql.Date(documents1.get(this.documents).getDate().getTime())+"', '"+documents1.get(this.documents).getImg()+"', '"+documents1.get(this.documents).getDescription()+"', '"+new java.sql.Date(documents1.get(this.documents).getDateOut().getTime())+"', '"+documents1.get(this.documents).isActive()+"');";
            this.documents+=1;
        }else if(this.maintenances<maints.size()){
            db="INSERT INTO mantenimiento(id, placa, km, fecha, tipo, imagen, descripcion, active, prox) VALUES ("+maints.get(this.maintenances).getId()+", '"+maints.get(this.maintenances).getPlaca()+"', "+maints.get(this.maintenances).getKm()+", '"+new java.sql.Date(maints.get(this.maintenances).getFecha().getTime())+"', '"+maints.get(this.maintenances).getTipo()+"', '"+maints.get(this.maintenances).getImagen()+"', '"+maints.get(this.maintenances).getDescripcion()+"', '"+maints.get(this.maintenances).isActive()+"', "+maints.get(this.maintenances).getProx()+");";
            this.maintenances+=1;
        }else if(this.observations<obs.size()){
            db="INSERT INTO observacion(id, placa, observacion, fecha) VALUES ("+obs.get(this.observations).getId()+", '"+obs.get(this.observations).getPlaca()+"', '"+obs.get(this.observations).getObservation()+"', '"+new java.sql.Date(maints.get(this.observations).getFecha().getTime())+"');";
            this.observations+=1;
        }else if(this.tanks<tanks1.size()){
            db="INSERT INTO tanqueo(id, placa, fecha, km, galones, kmpgalon) VALUES ("+tanks1.get(this.tanks).getId()+", '"+tanks1.get(this.tanks).getPlaca()+"', '"+new java.sql.Date(tanks1.get(this.tanks).getFecha().getTime())+"', "+tanks1.get(this.tanks).getKm()+", "+tanks1.get(this.tanks).getGalones()+", "+tanks1.get(this.tanks).getGalonesPerKm()+");";
            this.tanks+=1;
            if(this.tanks==tanks1.size()){this.isData=false;}
        }else{
            db="--";
            this.isData=false;
        }
        db=db.replace("'null'","null");
        System.out.println(db);
        return db;
    }

    private void getDB() {
        conductorDAO cDAO=new conductorDAO();
        carDAO DAO=new carDAO();
        imagesDAO iDAO=new imagesDAO();
        this.conductors1= cDAO.getAll();
        this.cars1=DAO.getBackup();
        this.images1=iDAO.getAll();
        this.documents1=DAO.getAllDocs();
        this.maints=DAO.getAllMaints();
        this.obs=DAO.getAllObs();
        this.tanks1=DAO.getAllTanks();
    }

    private String getExtension(File dir) {
        String extension = "";
        int i = dir.getName().lastIndexOf('.');
        if (i > 0) {
            extension =dir.getName().substring(i+1);
        }
        return extension;
    }

}