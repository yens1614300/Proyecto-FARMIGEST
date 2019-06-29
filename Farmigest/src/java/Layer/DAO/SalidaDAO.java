package Layer.DAO;

import static java.lang.String.format;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

 public class SalidaDAO {
    
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}
    
    public SalidaDAO() {
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
 
    
    public String registrar_salida(int idCompra,int idEmpleado, String fecha)  {
        String mensaje="Al parecer hubo un error al registrar su compra, intentelo más tarde."; 
        String nroDocumento="";
        String query="";
        
      try {
          
           //insertando la salida
            query = "insert into salida (idCompra,idCliente,idEmpleado,FechaSalida,NroDocumento,TipoDocumento,Estado) ";
            query = query +  " select x.idCompra,y.idCliente,'%s','%s','','Guia Remision',1 from compra x inner join cliente y on x.idUsuario=y.idUsuario where idCompra='%s'";
            query= format(query,idEmpleado,fecha,idCompra);
        
            int idSalida =MySqlExecute(query, OPERACION.INSERT);

            //insertar detalle de salida
            if(idSalida!=0){
                //actualizo el nro de docuento de salida
                nroDocumento="S01-"+"0000"+String.valueOf(idSalida);
                query=format("update salida set NroDocumento='%s' where idSalida='%s'",nroDocumento,idSalida);
                MySqlExecute(query, OPERACION.UPDATE);

                
                //inserto el detalle
                int idDetalle=0;
                query= format("INSERT into detallesalida (idSalida,idProducto,idSede,Cantidad) SELECT '%s',x.idProducto,0,x.Cantidad from detallecompra x where idCompra='%s'",idSalida,idCompra);
                idDetalle= MySqlExecute(query, OPERACION.INSERT);
                
                //actualizo el estado de entrega de la compra
                query =format("update compra set EstadoEntrega='Entregado' WHERE idCompra='%s'",idCompra);
                MySqlExecute(query, OPERACION.UPDATE);
                
                if(idDetalle ==0){
                    query =format("delete from salida where idSalida='%s'",idSalida);
                    MySqlExecute(query, OPERACION.DELETE);
                    query= format("delete from detallesalida where idsalida='%s'",idSalida);
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
  
    
    
}
