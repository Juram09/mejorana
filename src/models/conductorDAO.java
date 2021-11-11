package models;

import database.DBConnection;
import logic.logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class conductorDAO {
    private Connection connection;

    public conductorDAO() {
        connection=new DBConnection().getConnection();
    }

    public List<Conductor> getAll(){
        List<Conductor> conductors=new ArrayList<>();
        String sql="SELECT * FROM conductor;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                Conductor conductor=new Conductor();
                conductor.setNit(data.getString("nit"));
                conductor.setDocumento(data.getString("documento"));
                conductor.setNombre(data.getString("nombre"));
                conductor.setApellido(data.getString("apellido"));
                conductor.setTelefono(data.getLong("telefono"));
                conductor.setLicencia(data.getLong("licencia"));
                conductor.setImagen(data.getString("imagen"));
                conductors.add(conductor);
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return conductors;
    }

    public Conductor getOne(String nit){
        Conductor conductor=new Conductor();
        String sql="SELECT * FROM conductor WHERE nit=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nit);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                conductor.setNit(data.getString("nit"));
                conductor.setDocumento(data.getString("documento"));
                conductor.setNombre(data.getString("nombre"));
                conductor.setApellido(data.getString("apellido"));
                conductor.setTelefono(data.getLong("telefono"));
                conductor.setLicencia(data.getLong("licencia"));
                conductor.setImagen(data.getString("imagen"));
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
        return conductor;
    }
    public void insert(Conductor conductor) {
        String sql="INSERT INTO conductor(nit, documento, nombre, apellido, telefono, licencia,imagen) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, conductor.getNit());
            statement.setString(2, conductor.getDocumento());
            statement.setString(3, conductor.getNombre());
            statement.setString(4, conductor.getApellido());
            statement.setLong(5, conductor.getTelefono());
            statement.setLong(6, conductor.getLicencia());
            statement.setString(7, conductor.getImagen());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }
    public void update(Conductor conductor){
        String sql="UPDATE conductor SET documento=?, nombre=?, apellido=?, telefono=?, licencia=?,imagen=? WHERE nit=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, conductor.getDocumento());
            statement.setString(2, conductor.getNombre());
            statement.setString(3, conductor.getApellido());
            statement.setLong(4, conductor.getTelefono());
            statement.setLong(5, conductor.getLicencia());
            statement.setString(6, conductor.getImagen());
            statement.setString(7, conductor.getNit());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }
    public void delete(String nit){
        String sql="DELETE FROM conductor WHERE nit=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nit);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            logs.makeLog(e);
        }
    }
}
