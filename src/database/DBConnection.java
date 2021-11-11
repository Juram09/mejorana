package database;
import logic.logs;

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
                logs.makeLog(ex);
                return null;
            }

        }
        protected void finalize() throws Throwable
        {
            try { getConnection().close(); }
            catch (SQLException e) {
                logs.makeLog(e);
            }
            super.finalize();
        }
}
