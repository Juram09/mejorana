package database;
import logic.logs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
        public Connection getConnection() {

            String url = "jdbc:postgresql://localhost:5432/mejorana1";
            String user = "postgres";
            String password = "admin";
            try{
                return DriverManager.getConnection(url,user,password);
            }catch (SQLException ex) {
                logs.makeLog(ex);
                return null;
            }

        }
}
