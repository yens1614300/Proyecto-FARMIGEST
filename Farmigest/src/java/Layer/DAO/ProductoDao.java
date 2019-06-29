 
package Layer.DAO;

import Layer.ENTITY.DetalleIngreso;
import Layer.ENTITY.Producto;
import Layer.ENTITY.Sede;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
public class ProductoDao {
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}
    
    public ProductoDao() {
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
    }
    } catch (Exception e) {
    }  
    return repuesta;
    }
 
    public ArrayList<DetalleIngreso> Listar_busqueda(String filtro)  {
    filtro = "%" + filtro + "%";
    ArrayList<DetalleIngreso> lista= new ArrayList<>();
    
    String query= " ";
    query= query + " select i.idProducto,p.Nombre,p.Descripcion,p.marca,p.categoria,p.pre_venta,sum(i.cantDisponible)cantDisponible,";
    query= query + " i.idSede,s.Direccion,s.Distrito from detalleingreso i inner join producto p on i.idProducto=p.idProducto ";
    query = query + " inner JOIN sede s on i.idSede = s.idSede where (p.nombre like '%s' or p.descripcion like '%s' or p.marca like '%s')  ";
    query = query  + "  GROUP by i.idProducto,s.idSede ";
    query = format(query,filtro, filtro,filtro);
//query = query.replace("&", "%");
    
    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        DetalleIngreso det = new DetalleIngreso();
        
        Producto producto= new Producto(); //producto
        producto.setIdProducto(rs.getInt("idProducto"));
        producto.setNombre(rs.getString("Nombre"));
        producto.setDescripcion(rs.getString("Descripcion"));
        producto.setMarca(rs.getString("marca"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setPre_venta(rs.getDouble("pre_venta"));
        
        Sede sede = new Sede();//sede
        sede.setIdSede(rs.getInt("idSede"));
        sede.setDireccion(rs.getString("Direccion"));
        sede.setDistrito(rs.getString("Distrito"));
        sede.setDepartamento("");
        
        det.setIdProducto(rs.getInt("idProducto"));
        det.setIdSede(rs.getInt("idSede"));
        det.setCantDisponible(rs.getInt("cantDisponible"));
        
        det.setProducto(producto);
        det.setSede(sede);
        if (det.getCantDisponible()>0){
            lista.add(det);
        }     
        producto=null;
        det=null;
        sede=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
 
    public ArrayList<Producto> Listar_busqueda_ing(String filtro)  {
    filtro = "%" + filtro + "%";
    ArrayList<Producto> lista= new ArrayList<>();
    String query= format("SELECT * FROM producto where nombre like '%s' or descripcion like '%s' or marca like '%s'  ",filtro,filtro,filtro);
    //query = query.replace("&", "%");
    
    try {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
        Producto producto= new Producto();
        producto.setIdProducto(rs.getInt("idProducto"));
        producto.setNombre(rs.getString("Nombre"));
        producto.setDescripcion(rs.getString("Descripcion"));
        producto.setMarca(rs.getString("marca"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setPre_venta(rs.getDouble("pre_venta"));
        lista.add(producto);
        producto=null;
    }  
    } catch (SQLException sqle) {
    }

    return lista;
    } 
    
    public ArrayList<Producto> findAll()  {

        ArrayList<Producto> lista= new ArrayList<>();
        String query= "SELECT *  FROM producto";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Producto pro= new Producto();
                    pro.setIdProducto(rs.getInt("idProducto"));
                    pro.setNombre(rs.getString("nombre"));
                    pro.setDescripcion(rs.getString("descripcion"));
                    pro.setPre_venta(rs.getDouble("pre_venta"));
                    pro.setCategoria(rs.getString("categoria"));
                    pro.setMarca(rs.getString("marca"));
                    lista.add(pro);
                    pro=null;
                }  
        } catch (SQLException sqle) {
        }

        return lista;
    } 

    public Producto findById(int idProducto)  {

        Producto pro=null;
        String query= format("select * from producto where idProducto='%s'",idProducto);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                pro= new Producto();
                pro.setIdProducto(idProducto);
                pro.setNombre(rs.getString("nombre"));
                pro.setDescripcion(rs.getString("descripcion"));
                pro.setPre_venta(rs.getDouble("pre_venta"));
                pro.setCategoria(rs.getString("categoria"));
                pro.setMarca(rs.getString("marca"));
 
            }  
        } catch (SQLException sqle) {
        }

        return pro;
    }    

    public String create(Producto pro)  {
        String query="";
        String mensaje="Al parecer hubo un error al registrar, intentelo más tarde.";

        //insertando el usuario
        query= format("INSERT INTO producto (Nombre,Descripcion,pre_venta,categoria,marca,pre_compra,stock) values('%s','%s','%s','%s','%s','%s',0)",
            pro.getNombre(),pro.getDescripcion(),pro.getPre_venta(),pro.getCategoria(),pro.getMarca(),pro.getPre_compra()); 
        int idProducto =MySqlExecute(query, OPERACION.INSERT);

        //insertando el empleado
        if (idProducto!=0){
            mensaje="El registro se ha realizado satisfactoriamente.";
        }
 
        return mensaje;
    } 
    
    public String update(Producto pro)  {
        String query="";
        String mensaje="Al parecer hubo un error al intentar actualizar los datos, intentelo más tarde.";

        //actualizar empleado
        query= format("UPDATE producto SET Nombre= '%s',Descripcion= '%s',pre_venta= '%s',pre_compra= '%s',categoria= '%s',marca= '%s' WHERE idProducto = '%s'",
            pro.getNombre(),pro.getDescripcion(),pro.getPre_venta(),pro.getPre_compra(),pro.getCategoria(),pro.getMarca(),pro.getIdProducto()); 
        int idProducto =MySqlExecute(query, OPERACION.UPDATE);

        if (idProducto!=0){
            mensaje="Los datos han sido actualizados correctamente.";
        }
        return mensaje;
    }    
    
    public String delete(int idProducto)  {
        String query="";
        String mensaje="Al parecer hubo un error al intentar eliminar los datos, intentelo más tarde.";

        //eliminar empleado
        query= format("delete from  producto where idProducto='%s'",idProducto); 
        int afectado =MySqlExecute(query, OPERACION.DELETE); //se elimina el producto

        if (afectado!=0){
            mensaje="Los datos se han eliminado de manera satisfactoria.";
        }

        return mensaje;
    }
    
    public String validate(Producto pro){
        String mensaje ="";
        
        String query= " select idProducto,Nombre,Descripcion,pre_venta,pre_compra,categoria,marca from producto ";
        query= query + " where Nombre='%s' and marca='%s'  ";
        query= format(query,pro.getNombre(),pro.getMarca());
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { 
                    mensaje="Ya existe un producto con el mismo nombre y la misma marca.";
            }  
        } catch (SQLException sqle) {
        }

        return mensaje;
    }
    
    public String validateUpdate(Producto pro){
        String mensaje ="";
        
        String query= " select idProducto,Nombre,Descripcion,pre_venta,pre_compra,categoria,marca from producto ";
        query= query + " where idProducto<>'%s' and  (Nombre='%s' and marca='%s')  ";
        query= format(query,pro.getIdProducto(),pro.getNombre(),pro.getMarca());
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { 
                mensaje="Ya existe un producto con el mismo nombre y la misma marca.";
            }  
        } catch (SQLException sqle) {
        }

    
        return mensaje;
    }
       
    public String validateDelete(int idProducto){
        String mensaje ="";
        
        String query= format( " SELECT * FROM detallecompra WHERE idProducto='%s' ",idProducto);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { 
                mensaje="No es posible eliminar el producto, debido a que actualmente se encuentro en uso en otros registros.";
            }  
        } catch (SQLException sqle) {
        }

    
        return mensaje;
    }
        
    
    
}
