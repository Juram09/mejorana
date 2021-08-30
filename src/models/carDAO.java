package models;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class carDAO {
    private Connection connection;

    public carDAO() {
        connection=new DBConnection().getConnection();
    }

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
            e.printStackTrace();
            throw new RuntimeException(e);
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
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return car;
    }
    public String getImage(String placa){
        String sql="SELECT frontal FROM imagenes WHERE placa=?;";
        String image="no";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, placa);
            ResultSet data=statement.executeQuery();
            while(data.next()){
                image=(data.getString("frontal"));
            }
            data.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return image;
    }
    public void insert(Car car) {
        String sql="INSERT INTO car(placa, km, color, marca, modelo, chasis, capacidad, tipo, conductor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
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
            e.printStackTrace();
            throw new RuntimeException(e);
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
            e.printStackTrace();
            throw new RuntimeException(e);
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
