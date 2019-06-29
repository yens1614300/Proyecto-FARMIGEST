
package Layer.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
       
    public Connection getConnection()
            throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/db_farmigest", "root", "");
    }


}
 
 
