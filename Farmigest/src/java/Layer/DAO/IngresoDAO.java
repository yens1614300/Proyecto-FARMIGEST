
package Layer.DAO;

import Layer.ENTITY.DetalleIngreso;
import Layer.ENTITY.Ingreso;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IngresoDAO {
    
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}

    public IngresoDAO() {
        Conexion con = new Conexion();
        try {
            connection = con.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private int MySqlExecute(String query,OPERACION operacion){
    boolean inserted=false;
    int repuesta=0;
    Statement stm;
    try {
    stm = connection.createStatement();
    try {  
        if (operacion==OPERACION.UPDATE || operacion == OPERACION.DELETE){
            inserted = stm.executeUpdate(query)>0;
            if (inserted==true) repuesta=1;
        }else if(operacion==OPERACION.INSERT){
            inserted = stm.executeUpdate(query,Statement.RETURN_GENERATED_KEYS)>0;
            if (inserted==true){
                ResultSet keys = stm.getGeneratedKeys();    
                keys.next();  
                repuesta= keys.getInt(1);
            }
        }
    } catch (SQLException sqle) {
        String error=sqle.getMessage();
    }
    } catch (Exception e) {
        String error=e.getStackTrace().toString();
    }  
    return repuesta;
    }
 
   
    public String registrar_ingreso(Ingreso ingreso)  {
        String mensaje="Al parecer hubo un error al registrar el ingreso, intentelo más tarde."; 
        String nroDocumento="";
        String query="";
        
      try {
          
           //insertando el ingreso
            query = " INSERT INTO ingreso ( nroDocumento, rucproveedor, fechaIngreso, idEmpleado, tipoDocumento) VALUES ( '%s', '%s', '%s', '%s', '%s'); "; 
            query= format(query,ingreso.getNroDocumento(),ingreso.getRucproveedor(),ingreso.getFechaIngreso(),ingreso.getIdEmpleado(),ingreso.getTipoDocumento());
        
            int idIngreso =MySqlExecute(query, OPERACION.INSERT);

            //insertar detalle de salida
            if(idIngreso!=0){
                //actualizo el nro de docuento de salida
                nroDocumento="I001-"+"0000"+String.valueOf(idIngreso);
                query=format("update ingreso set nroDocumento='%s' where idIngreso='%s'",nroDocumento,idIngreso);
                MySqlExecute(query, OPERACION.UPDATE);
                
                //inserto detalle de ingreso
                int idDetalle=0;
                ArrayList<DetalleIngreso> detalle = ingreso.getDetalle(); //asigno la lista de detalle ingreso
                
                //ANOTACION: CUANDO SE REALIZA EL INGRESO SE DISPARA UN EVENTO A NIVEL DE MYSQL
                //QUE ACTUALIZA EL STOCK DEL PRODUCTO
                for (DetalleIngreso det : detalle) {
                    det.setIdSede(ingreso.getIdSede());
                    query= "INSERT INTO detalleingreso ( idIngreso, idProducto, idSede, cantidad, cantDisponible, cantEntregada) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";
                    query= format(query, idIngreso,det.getIdProducto(),det.getIdSede(),det.getCantidad(),det.getCantDisponible(),det.getCantEntregada());
                    idDetalle= MySqlExecute(query, OPERACION.INSERT);
                    if(idDetalle==0 ){
                        break;//culmina el proceso
                    }else{
                        query="";
                    }
                }

                if(idDetalle ==0){
                    query =format("delete from ingreso where idSalida='%s'",idIngreso);
                    MySqlExecute(query, OPERACION.DELETE);
                    query= format("delete from detalleingreso where idsalida='%s'",idIngreso);
                    MySqlExecute(query, OPERACION.DELETE);
                }else{
                    mensaje="El registro ha sido realizada satisfactoriamente con el número de guía " + nroDocumento + ".";
                }
            }
            
      } catch (Exception e) {
          mensaje = e.getStackTrace().toString();
      }

    return mensaje;
    } 
  //SELECT * FROM ingreso ORDER by idIngreso DESC LIMIT 20
    
    public ArrayList<Ingreso> findAll()  {

        ArrayList<Ingreso> lista= new ArrayList<>();
        String query= "SELECT * FROM ingreso ORDER by idIngreso DESC LIMIT 20";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Ingreso obj= new Ingreso();
                    obj.setNroDocumento(rs.getString("nroDocumento"));
                    obj.setRucproveedor(rs.getString("rucproveedor"));
                    obj.setTipoDocumento(rs.getString("tipoDocumento"));
                    obj.setFechaIngreso(rs.getString("fechaIngreso"));
                    lista.add(obj);
                    obj=null;
                }  
        } catch (SQLException sqle) {
        }

        return lista;
    } 

}
