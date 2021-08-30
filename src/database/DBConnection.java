package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
        public Connection getConnection() {

            String url = "jdbc:postgresql://localhost:5432/mejorana";
            String user = "postgres";
            String password = "admin";
            try{
                return DriverManager.getConnection(url,user,password);
            }catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }

        }
}
