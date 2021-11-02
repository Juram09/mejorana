package models;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class imagesDAO {
    private Connection connection;

    public imagesDAO() {
        connection=new DBConnection().getConnection();
    }

    public Images getOne(String placa){
        String sql="SELECT * FROM imagenes WHERE placa=?;";
        Images images=new Images();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                images.setFrontal(data.getString("frontal"));
                images.setTrasera(data.getString("trasera"));
                images.setLatDer(data.getString("derecha"));
                images.setLatIzq(data.getString("izquierda"));
                images.setCabin(data.getString("cabina"));
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return images;
    }

    public void insert(Images img, String placa) {
        String sql="INSERT INTO imagenes(placa, frontal, trasera, izquierda, derecha, cabina) VALUES (?, ?, ?, ?, ?, ?);";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.setString(2, img.getFrontal());
            statement.setString(3, img.getTrasera());
            statement.setString(4, img.getLatIzq());
            statement.setString(5, img.getLatDer());
            statement.setString(6, img.getCabin());
            statement.execute();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void update(Images img,String placa){
        String sql="UPDATE imagenes SET frontal=?, trasera=?, izquierda=?, derecha=?, cabina=? WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, img.getFrontal());
            statement.setString(2, img.getTrasera());
            statement.setString(3, img.getLatIzq());
            statement.setString(4, img.getLatDer());
            statement.setString(5, img.getCabin());
            statement.setString(6, placa);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void delete(String placa){
        String sql="DELETE FROM imagenes WHERE placa=?;";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            statement.execute();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
