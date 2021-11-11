package models;

import database.DBConnection;
import logic.logs;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class carDAO {
    private Connection connection;

    public carDAO() {
        connection=new DBConnection().getConnection();
    }
    //Car
    public List<Car> getAll(){
        List<Car> cars=new ArrayList<>();
        String sql="SELECT placa, km, tipo FROM car;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Car car=new Car();
                car.setPlaca(data.getString("placa"));
                car.setKm(data.getLong("km"));
                car.setTipo(data.getString("tipo"));
                cars.add(car);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return cars;
    }

    public List<Car> getBackup(){
        List<Car> cars=new ArrayList<>();
        String sql="SELECT * FROM car;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Car car=new Car();
                car.setPlaca(data.getString("placa"));
                car.setKm(data.getLong("km"));
                car.setColor(data.getString("color"));
                car.setMarca(data.getString("marca"));
                car.setModelo(data.getInt("modelo"));
                car.setChasis(data.getString("chasis"));
                car.setCapacidad(data.getInt("capacidad"));
                car.setTipo(data.getString("tipo"));
                car.setConductor(data.getString("conductor"));
                cars.add(car);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return cars;
    }

    public Car getOne(String placa){
        Car car=new Car();
        String sql="SELECT * FROM car WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                car.setPlaca(data.getString("placa"));
                car.setKm(data.getLong("km"));
                car.setColor(data.getString("color"));
                car.setMarca(data.getString("marca"));
                car.setModelo(data.getInt("modelo"));
                car.setChasis(data.getString("chasis"));
                car.setCapacidad(data.getInt("capacidad"));
                car.setTipo(data.getString("tipo"));
                car.setConductor(data.getString("conductor"));
                car.setImgs(getImages(placa));
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return car;
    }

    public void insert(Car car) {
        String sql="INSERT INTO car(placa, km, color, marca, modelo, chasis, capacidad, tipo, conductor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, car.getPlaca());
            statement.setLong(2, car.getKm());
            statement.setString(3, car.getColor());
            statement.setString(4, car.getMarca());
            statement.setInt(5, car.getModelo());
            statement.setString(6, car.getChasis());
            statement.setInt(7, car.getCapacidad());
            statement.setString(8, car.getTipo());
            statement.setString(9, car.getConductor());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void update(Car car){
        String sql="UPDATE car SET km=?, color=?, marca=?, modelo=?, chasis=?, capacidad=?, tipo=?, conductor=? WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, car.getKm());
            statement.setString(2, car.getColor());
            statement.setString(3, car.getMarca());
            statement.setInt(4, car.getModelo());
            statement.setString(5, car.getChasis());
            statement.setInt(6, car.getCapacidad());
            statement.setString(7, car.getTipo());
            statement.setString(8, car.getConductor());
            statement.setString(9, car.getPlaca());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void updateKm(String placa, Long km){
        String sql="UPDATE car SET km=? WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, km);
            statement.setString(2, placa);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void delete(String placa){
        String sql="DELETE FROM car WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }
    //Conductor
    public String getConductor(String placa){
        String sql="SELECT conductor FROM car WHERE placa=?;";
        String conductor=null;
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                conductor=data.getString("conductor");
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return conductor;
    }

    //Document
    public List<Document> getAllDocs(){
        List<Document> docs=new ArrayList<>();
        String sql="SELECT * FROM documento;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Document doc=new Document();
                doc.setId(data.getString("id"));
                doc.setName(data.getString("nombre"));
                doc.setPlaca(data.getString("placa"));
                doc.setDate(data.getDate("fecha_in"));
                doc.setImg(data.getString("imagen"));
                doc.setDescription(data.getString("descripcion"));
                doc.setActive(data.getBoolean("active"));
                if(data.getString("fecha_out")!=null){
                    doc.setDateOut(data.getDate("fecha_out"));
                }
                docs.add(doc);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return docs;
    }

    public List<Document> getDocs(String placa){
        List<Document> docs=new ArrayList<>();
        String sql="SELECT * FROM documento WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Document doc=new Document();
                doc.setId(data.getString("id"));
                doc.setDate(data.getDate("fecha_in"));
                doc.setName(data.getString("nombre"));
                doc.setImg(data.getString("imagen"));
                doc.setActive(data.getBoolean("active"));
                if(data.getString("fecha_out")!=null){
                    doc.setDateOut(data.getDate("fecha_out"));
                }
                docs.add(doc);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return docs;
    }

    public void insertDoc(Document document,String placa){
        String sql="INSERT INTO documento(id, nombre, placa, fecha_in, imagen, descripcion, fecha_out, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, document.getId());
            statement.setString(2, document.getName());
            statement.setString(3, placa);
            statement.setDate(4, new java.sql.Date(document.getDate().getTime()));
            statement.setString(5, document.getImg());
            statement.setString(6, document.getDescription());
            if(document.getDateOut()!=null){
                statement.setDate(7, new java.sql.Date(document.getDateOut().getTime()));
            }else{
                statement.setDate(7, null);
            }
            statement.setBoolean(8, document.isActive());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void updateDoc(Document document, String placa){
        String sql="UPDATE documento SET active=? WHERE id=? AND nombre=? AND placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, document.isActive());
            statement.setString(2, document.getId());
            statement.setString(3, document.getName());
            statement.setString(4, placa);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void deleteDoc(String placa, String name, String id){
        String sql="DELETE FROM documento where placa=? AND id=? AND nombre=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.setString(2, id);
            statement.setString(3, name);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    //Observations
    public List<Observation> getAllObs(){
        List<Observation> observations=new ArrayList<>();
        String sql="SELECT * FROM observacion;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Observation obs=new Observation();
                obs.setId(data.getLong("id"));
                obs.setPlaca(data.getString("placa"));
                obs.setObservation(data.getString("observacion"));
                obs.setDate(data.getDate("fecha"));
                observations.add(obs);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return observations;
    }

    public List<Observation> getObs(String placa){
        List<Observation> observations=new ArrayList<>();
        String sql="SELECT id, fecha, observacion FROM observacion WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Observation obs=new Observation();
                obs.setId(data.getLong("id"));
                obs.setObservation(data.getString("observacion"));
                obs.setDate(data.getDate("fecha"));
                observations.add(obs);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return observations;
    }

    public void insertObs(String observation,String placa){
        String sql="INSERT INTO observacion(placa, observacion, fecha) VALUES (?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.setString(2, observation);
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void deleteObs(long id){
        String sql="DELETE FROM observacion where id=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    //Tanks
    public List<Tank> getAllTanks(){
        List<Tank> tanks=new ArrayList<>();
        String sql="SELECT * FROM tanqueo;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Tank tank=new Tank();
                tank.setId(data.getLong("id"));
                tank.setPlaca(data.getString("placa"));
                tank.setFecha(data.getDate("fecha"));
                tank.setKm(data.getLong("km"));
                tank.setGalones(data.getLong("galones"));
                tank.setGalonesPerKm(data.getLong("kmpgalon"));
                tanks.add(tank);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return tanks;
    }

    public List<Tank> getTanks(String placa){
        List<Tank> tanks=new ArrayList<>();
        String sql="SELECT * FROM tanqueo WHERE placa=? ORDER BY id DESC LIMIT 20;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Tank tank=new Tank();
                tank.setPlaca(placa);
                tank.setKm(data.getLong("km"));
                tank.setGalones(data.getLong("galones"));
                tank.setGalonesPerKm(data.getLong("kmpgalon"));
                tank.setFecha(data.getDate("fecha"));
                tanks.add(tank);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return tanks;
    }

    public Tank getTank(String placa){
        Tank tank=new Tank();
        String sql="SELECT * FROM tanqueo WHERE placa=? ORDER BY id DESC LIMIT 1;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            if(data.next()){
                tank.setId(data.getLong("id"));
                tank.setPlaca(placa);
                tank.setKm(data.getLong("km"));
                tank.setGalones(data.getLong("galones"));
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
            return null;
        }
        return tank;
    }

    public void insertTank(Tank tank, Date date, String placa){
        String sql="INSERT INTO tanqueo (placa, fecha, km, galones) VALUES (?, ?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.setDate(2, new java.sql.Date(date.getTime()));
            statement.setLong(3, tank.getKm());
            statement.setLong(4, tank.getGalones());
            statement.execute();
            statement.close();
            updateKm(placa,tank.getKm());
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void updateTank(Long id, Long kmpgalon){
        String sql="UPDATE tanqueo SET kmpgalon=? WHERE id=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, kmpgalon);
            statement.setLong(2, id);
            statement.execute();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    //Maintenance
    public List<Maintenance> getAllMaints(){
        List<Maintenance> maints=new ArrayList<>();
        String sql="SELECT * FROM mantenimiento;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Maintenance maint=new Maintenance();
                maint.setId(data.getLong("id"));
                maint.setPlaca(data.getString("placa"));
                maint.setKm(data.getLong("km"));
                maint.setFecha(data.getDate("fecha"));
                maint.setTipo(data.getString("tipo"));
                maint.setImagen(data.getString("imagen"));
                maint.setDescripcion(data.getString("descripcion"));
                maint.setActive(data.getBoolean("active"));
                maint.setProx(data.getLong("prox"));
                maints.add(maint);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return maints;
    }

    public List<Maintenance> getMaints(String placa){
        List<Maintenance> maints=new ArrayList<>();
        String sql="SELECT * FROM mantenimiento WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Maintenance maint=new Maintenance();
                maint.setId(data.getLong("id"));
                maint.setKm(data.getLong("km"));
                maint.setFecha(data.getDate("fecha"));
                maint.setTipo(data.getString("tipo"));
                maint.setImagen(data.getString("imagen"));
                maint.setDescripcion(data.getString("descripcion"));
                maint.setActive(data.getBoolean("active"));
                maint.setProx(data.getLong("prox"));
                maints.add(maint);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return maints;
    }

    public void insertMaint(Maintenance maint,String placa){
        String sql="INSERT INTO mantenimiento(placa, km, fecha, tipo, imagen, descripcion, active, prox) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.setLong(2, maint.getKm());
            statement.setDate(3, new java.sql.Date(maint.getFecha().getTime()));
            statement.setString(4, maint.getTipo());
            statement.setString(5, maint.getImagen());
            if(maint.getDescripcion()!=null){
                statement.setString(6, maint.getDescripcion());
            }else{
                statement.setString(6, null);
            }
            statement.setBoolean(7, maint.isActive());
            statement.setLong(8, maint.getProx());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void updateMaint(Maintenance maint){
        String sql="UPDATE mantenimiento SET active=? WHERE id=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, maint.isActive());
            statement.setLong(2, maint.getId());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public void deleteMaint(Long id){
        String sql="DELETE FROM mantenimiento where id=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }

    public Images getImages(String placa){
        imagesDAO img = new imagesDAO();
        Images image = img.getOne(placa);
        return image;
    }
}
