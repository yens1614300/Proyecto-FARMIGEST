
package Layer.DAO;

import Layer.ENTITY.Sede;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SedeDAO {
    
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}

    public SedeDAO() {
        Conexion con = new Conexion();
        try {
            connection = con.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    public ArrayList<Sede> lista()  {

        ArrayList<Sede> lista= new ArrayList<>();
        String query= "SELECT *  FROM sede";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Sede obj= new  Sede();
                    obj.setIdSede(rs.getInt("idSede"));
                    obj.setDepartamento(rs.getString("departamento"));
                    obj.setDireccion(rs.getString("direccion"));
                    obj.setDistrito(rs.getString("distrito"));
                    lista.add(obj);
                    obj=null;
                }  
        } catch (SQLException sqle) {
        }

        return lista;
    } 

    
}
