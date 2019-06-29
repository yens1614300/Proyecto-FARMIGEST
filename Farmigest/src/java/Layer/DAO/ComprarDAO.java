 
package Layer.DAO;

import Layer.ENTITY.Compra;
import Layer.ENTITY.DetalleCompra;
import Layer.ENTITY.Mes;
import Layer.ENTITY.Pago;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ComprarDAO {
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}
    public ComprarDAO() {
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
 
    private String superaCantidad_Producto(ArrayList<DetalleCompra> detalleCompra){
        String mensaje="";
        String query="";
        int stock=0;
        try{
            Statement stmt = connection.createStatement();
        for (DetalleCompra item : detalleCompra) {
            query =format(" select sum(d.cantDisponible)cantDisponible,p.Nombre  from producto p inner join detalleingreso d on p.idProducto=d.idProducto where d.idProducto='%s' and d.idsede='%s' group by p.Nombre",item.getIdProducto(),item.getIdSede());   
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                stock=rs.getInt("cantDisponible");
                item.getProducto().setNombre(rs.getString("Nombre"));
            }
            
            if(stock==0){
                mensaje="Ya no se cuenta con stock disponible para " + item.getProducto().getNombre() + " de la marca " + item.getProducto().getMarca();
            }else if(item.getCantidad()>stock){
                mensaje="El stock disponible para " + item.getProducto().getNombre() + " de la marca " + item.getProducto().getMarca() + " es de " + stock + " y ya a sido superada.";
                break;
            }
        }
            
            
        } catch (SQLException sqle) {
        }
        return mensaje;
    }
            
    
    public String registrar_compra(Compra compra, ArrayList<DetalleCompra> detalleCompra,Pago pago)  {
        String query="";
        String mensaje="Al parecer hubo un error al registrar su compra, intentelo más tarde."; 
        
        String msj=superaCantidad_Producto(detalleCompra);
        if(msj.contains("stock")){
            mensaje=msj;
        }else{
        
            try {

                  //insertando el compra
                  query= format("INSERT INTO compra (idUsuario, Monto, Impuesto, MontoTotal,FechaCompra,EstadoCompra,EstadoEntrega,NroDocumento) values('%s','%s','%s','%s','%s','%s','%s','%s')"
                          ,compra.getIdUsuario(),compra.getMonto(),compra.getImpuesto(),compra.getMonoTotal(),compra.getFechaCompra(),
                          compra.getEstadoCompra(),compra.getEstadoEntrega(),compra.getNroDocumento()); 
                  int idCompra =MySqlExecute(query, OPERACION.INSERT);

                  //insertar al cliente
                  if(idCompra!=0){
                      //actualizo el nro de docuemtno
                      compra.setIdCompra(idCompra);
                      String nroDocumento="001-"+"0000"+String.valueOf(idCompra);
                      compra.setNroDocumento(nroDocumento);
                      query=format("update compra set NroDocumento='%s' where idCompra='%s'",compra.getNroDocumento(),compra.getIdCompra());
                      MySqlExecute(query, OPERACION.UPDATE);

                      int idDetalle=0;
                      //inserto el detalle
                      for (DetalleCompra item : detalleCompra) {
                          query= format("INSERT INTO detallecompra (idCompra,idProducto,Cantidad,PreUnidad,Monto,Impuesto,MontoTotal,Estado,idSede) values('%s','%s','%s','%s','%s','%s','%s','%s','%s')",
                          idCompra,item.getIdProducto(),item.getCantidad(),item.getPrecio(),item.getMonto(),item.getImpuesto(),item.getTotal(),
                          item.getEstado(),item.getIdSede());
                          idDetalle= MySqlExecute(query, OPERACION.INSERT);
                          if (idDetalle==0) break;
                      }

                      //insertando el pago
                      if (idDetalle==0){
                          query= format("delete from detallecompra where idCompra='%s')",idCompra);
                          MySqlExecute(query, OPERACION.DELETE);
                          query= format("delete from compra where idCompra='%s')",idCompra);
                          MySqlExecute(query, OPERACION.DELETE);
                      }else{
                          query= format("INSERT INTO pago(idCompra,Monto,NumeroTarjeta,FechaPago)values('%s','%s','%s','%s')",
                                 idCompra, pago.getMonto(),pago.getNumTarjeta(),pago.getFechaPago());
                          int idPago= MySqlExecute(query, OPERACION.INSERT);

                          if (idPago==0){
                              query= format("delete from detallecompra where idCompra='%s'",idCompra);
                              MySqlExecute(query, OPERACION.DELETE);
                              query= format("delete from compra where idCompra='%s'",idCompra);
                              MySqlExecute(query, OPERACION.DELETE);
                          }else{
                             mensaje="Su compra ha sido realizada satisfactoriamente con el número de compra "+compra.getNroDocumento()+".";
                          }
                      }

                  }   

            } catch (Exception e) {
                mensaje = e.getStackTrace().toString();
            }

        }

        return mensaje;
    } 
  
    public ArrayList<Compra> ListaDespachar()  {
    ArrayList<Compra> lista= new ArrayList<>();
    String query= format("select x.idCompra,x.NroDocumento, concat(y.Nombre ,' ', y.ApellidoPaterno) Nombre ,y.DNI,x.FechaCompra,x.EstadoEntrega from compra x inner join cliente y on x.idUsuario=y.idUsuario where EstadoEntrega='Pendiente'");

    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        Compra compra =new Compra();
        
        compra.setIdCompra(rs.getInt("idCompra"));
        compra.setNroDocumento(rs.getString("NroDocumento"));
        compra.getCliente().setDni(rs.getString("dni"));
        compra.getCliente().setNombre(rs.getString("Nombre"));
        compra.setFechaCompra(rs.getString("FechaCompra"));
        compra.setEstadoEntrega(rs.getString("EstadoEntrega"));
        
        lista.add(compra);
        compra=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
 
    
   public ArrayList<Compra> ListaCompras(int idUsuario)  {
    ArrayList<Compra> lista= new ArrayList<>();
    String query= format("select x.idCompra,x.NroDocumento, concat(y.Nombre ,' ', y.ApellidoPaterno) Nombre ,y.DNI,x.FechaCompra,x.EstadoEntrega from compra x inner join cliente y on x.idUsuario=y.idUsuario where EstadoEntrega='Pendiente' and x.idUsuario='%s'",idUsuario);

    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        Compra compra =new Compra();
        
        compra.setIdCompra(rs.getInt("idCompra"));
        compra.setNroDocumento(rs.getString("NroDocumento"));
        compra.getCliente().setDni(rs.getString("dni"));
        compra.getCliente().setNombre(rs.getString("Nombre"));
        compra.setFechaCompra(rs.getString("FechaCompra"));
        compra.setEstadoEntrega(rs.getString("EstadoEntrega"));
        
        lista.add(compra);
        compra=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
    
    public ArrayList<DetalleCompra> DetalleCompra(int idCompra)  {
    ArrayList<DetalleCompra> lista= new ArrayList<>();
    String query= format("SELECT y.Nombre,y.marca,x.Cantidad FROM detallecompra x INNER join producto y on x.idProducto=y.idProducto where x.idCompra='%s'",idCompra);

    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        DetalleCompra detalle =new DetalleCompra();
        detalle.getProducto().setNombre(rs.getString("Nombre"));
        detalle.getProducto().setMarca(rs.getString("marca"));
        detalle.setCantidad(rs.getInt("Cantidad"));
        lista.add(detalle);
        detalle=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
 
    public ArrayList<Compra> listaVenta(String fechaIni, String fechaFin)  {
    ArrayList<Compra> lista= new ArrayList<>();
    String query= format("SELECT c.NroDocumento,c.Monto,c.Impuesto,c.MontoTotal,cl.Nombre,cl.ApellidoPaterno,c.FechaCompra,c.EstadoCompra,c.EstadoEntrega FROM compra c inner JOIN cliente cl on c.idUsuario=cl.idUsuario where FechaCompra between '%s' and '%s' ",fechaIni,fechaFin);

    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        Compra compra =new Compra();
      
        compra.setNroDocumento(rs.getString("NroDocumento"));
        compra.setMonto(rs.getDouble("monto"));
        compra.setImpuesto(rs.getDouble("impuesto"));
        compra.setMonoTotal(rs.getDouble("montototal"));
        compra.setFechaCompra(rs.getString("FechaCompra"));
        compra.setEstadoCompra("estadocompra");
        compra.setEstadoEntrega(rs.getString("EstadoEntrega"));
        
        compra.getCliente().setNombre(rs.getString("Nombre"));
        compra.getCliente().setApellidoPaterno("apellidopaterno");
        
        lista.add(compra);
        compra=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
 
    public ArrayList<Mes> listaVentaxMes(String anio)  {
    ArrayList<Mes> lista= new ArrayList<>();
    String query= format(" SELECT x.Mes ,IFNULL(sum(y.MontoTotal),0)Total from meses x left join (SELECT FechaCompra, montoTotal from compra where year(FechaCompra)='%s') y on month(x.Fecha)=month(y.FechaCompra) group by x.mes   ",anio);

    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        Mes mes =new Mes();
        mes.setMes(rs.getString("Mes"));
        mes.setTotal(rs.getDouble("Total"));
        
        lista.add(mes);
        mes=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
 
     
    
}
