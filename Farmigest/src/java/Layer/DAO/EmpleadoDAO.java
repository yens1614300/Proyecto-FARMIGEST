
package Layer.DAO;

import Layer.ENTITY.Empleado;
import Layer.ENTITY.Usuario;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmpleadoDAO {
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}
    
    public EmpleadoDAO() {
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

    public ArrayList<Empleado> findAll()  {

        ArrayList<Empleado> lista= new ArrayList<>();
        String query= "SELECT nombre,apellido,dni,sexo,area,idempleado FROM empleado";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Empleado emp= new Empleado();
                    emp.setNombre(rs.getString("nombre"));
                    emp.setApellido(rs.getString("apellido"));
                    emp.setDni(rs.getString("dni"));
                    emp.setSexo(rs.getString("sexo"));
                    emp.setArea(rs.getString("area"));
                    emp.setIdEmpleado(rs.getInt("idempleado"));
                    lista.add(emp);
                    emp=null;
                }  
        } catch (SQLException sqle) {
        }

        return lista;
    } 

    public Empleado findById(int idEmpleado)  {

        Empleado emp=null;
        String query= format("select x.*,y.logUsuario,y.pwdUsuario,IdNivelUsuario from empleado x inner join usuario y on x.idUsuario=y.idUsuario where x.idEmpleado='%s'",idEmpleado);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                emp= new Empleado();
                emp.setNombre(rs.getString("nombre"));
                emp.setApellido(rs.getString("apellido"));
                emp.setDni(rs.getString("dni"));
                emp.setSexo(rs.getString("sexo"));
                emp.setArea(rs.getString("area"));
                emp.setIdEmpleado(rs.getInt("idempleado"));
                emp.setCelular(rs.getString("celular"));
                emp.setIdUsuario(rs.getInt("idUsuario"));
                
                emp.getUsuario().setIdUsuario(rs.getInt("idUsuario"));
                emp.getUsuario().setLogUsuario(rs.getString("logUsuario"));
                emp.getUsuario().setPwdUsuario(rs.getString("pwdUsuario"));
                emp.getUsuario().setIdNivelUsuario(rs.getInt("IdNivelUsuario"));
            }  
        } catch (SQLException sqle) {
        }

        return emp;
    }    

    public String create(Empleado emp)  {
        Usuario usuario = emp.getUsuario();
        String query="";
        String mensaje="Al parecer hubo un error al registrar, intentelo más tarde.";

        //insertando el usuario
        query= format("INSERT INTO usuario (logUsuario, pwdUsuario, IdNivelUsuario, Estado) values('%s','%s','%s','%s')",
            usuario.getLogUsuario(),usuario.getPwdUsuario(),usuario.getIdNivelUsuario(),usuario.getEstado()); 
        int idUsuario =MySqlExecute(query, OPERACION.INSERT);

        //insertando el empleado
        if (idUsuario!=0){
            emp.setIdUsuario(idUsuario); //asigno el id generado de usuario
            query= format("INSERT INTO empleado (nombre, apellido,dni,sexo,celular,area,idusuario,estado) values('%s','%s','%s','%s','%s','%s','%s','%s')",
                emp.getNombre(),emp.getApellido(),emp.getDni(),emp.getSexo(),emp.getCelular(),emp.getArea(),emp.getIdUsuario(),emp.getEstado()); 
            int idEmpleado =MySqlExecute(query, OPERACION.INSERT);

            if (idEmpleado==0){
                query= format("delete from usuario where idUsuario='%s')",emp.getIdUsuario());
                MySqlExecute(query, OPERACION.DELETE);
            }else{
                mensaje="El registro se ha realizado satisfactoriamente.";
            }

        }
        return mensaje;
    } 
    
    public String update(Empleado emp)  {
        String query="";
        String mensaje="Al parecer hubo un error al intentar actualizar los datos, intentelo más tarde.";

        //actualizar empleado
        query= format("UPDATE empleado SET nombre='%s', apellido='%s',dni='%s',sexo='%s',celular='%s',area='%s' WHERE idEmpleado = '%s'",
            emp.getNombre(),emp.getApellido(),emp.getDni(),emp.getSexo(),emp.getCelular(),emp.getArea(),emp.getIdEmpleado()); 
        int idEmpleado =MySqlExecute(query, OPERACION.UPDATE);

        if (idEmpleado!=0){
            Usuario usu= emp.getUsuario();
            query= format("UPDATE usuario set logUsuario='%s',pwdUsuario='%s',IdNivelUsuario='%s' WHERE idUsuario = '%s'",
                usu.getLogUsuario(),usu.getPwdUsuario(),usu.getIdNivelUsuario(),usu.getIdUsuario()); 
            int idUsuario =MySqlExecute(query, OPERACION.UPDATE); 
            if (idUsuario!=0){
                mensaje="Los datos han sido actualizados correctamente.";
            }
        }
        
        return mensaje;
    }    
    
    public String delete(int idEmpleado)  {
        String query="";
        String mensaje="Al parecer hubo un error al intentar eliminar los datos, intentelo más tarde.";

        //eliminar empleado
        query= format("delete from  usuario where idUsuario in (select idUsuario from empleado where idEmpleado='%s')",idEmpleado); 
        int afectado =MySqlExecute(query, OPERACION.DELETE); //se elimina el empleado

        if (afectado!=0){
            query= format("delete from  empleado where idEmpleado = '%s'",idEmpleado); 
            MySqlExecute(query, OPERACION.DELETE);//se elimina el usuario
            mensaje="Los datos se han eliminado de manera satisfactoria.";
        }

        return mensaje;
    }
    
    public String validate(Empleado emp){
        String mensaje ="";
        
        String query= " select x.Nombre,x.DNI,y.logUsuario from empleado x inner join usuario y on x.idUsuario=y.idUsuario";
        query= query + " where  (x.Celular ='%s' or x.DNI='%s' or y.logUsuario='%s')";
        query= format(query,emp.getCelular(),emp.getDni(),emp.getUsuario().getLogUsuario());
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { 
                String celular =rs.getString("Celular");
                String dni=rs.getString("DNI");
                String usuario=rs.getString("logUsuario");

                if (celular.equals(emp.getCelular())){
                    mensaje="El Número de celular ya se encuentra actualmente en uso."; break;
                }else if (dni.equals( emp.getDni())){
                    mensaje="El Número de DNI ya se encuentra actualmente en uso."; break;
                }else if (usuario.equals( emp.getUsuario().getLogUsuario())){
                    mensaje="El Correo Eletrónico ya se encuentra actualmente en uso."; break;
                }
            }  
        } catch (SQLException sqle) {
        }
        //se limpia atributo de objeto que existe
        switch(mensaje){
            case "El Número de celular ya se encuentra actualmente en uso.": emp.setCelular("");break;
            case "El Número de DNI ya se encuentra actualmente en uso.": emp.setDni("");break;
            case "El Correo Eletrónico ya se encuentra actualmente en uso.": emp.getUsuario().setLogUsuario("");break;
        }
    
        return mensaje;
    }
    
    public String validateUpdate(Empleado emp){
        String mensaje ="";
        
        String query= " select x.Nombre,x.DNI,y.logUsuario from empleado x inner join usuario y on x.idUsuario=y.idUsuario";
        query= query + " where  x.idUsuario<>'' and (x.Celular ='%s' or x.DNI='%s' or y.logUsuario='%s')";
        query= format(query,emp.getIdEmpleado(),emp.getCelular(),emp.getDni(),emp.getUsuario().getLogUsuario());
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { 
                String celular =rs.getString("Celular");
                String dni=rs.getString("DNI");
                String usuario=rs.getString("logUsuario");

                if (celular.equals(emp.getCelular())){
                    mensaje="El Número de celular al intentan actualizar ya se encuentra en uso."; break;
                }else if (dni.equals( emp.getDni())){
                    mensaje="El Número de DNI al intentan actualizar ya se encuentra en uso."; break;
                }else if (usuario.equals( emp.getUsuario().getLogUsuario())){
                    mensaje="El Correo Electrónico al intentan actualizar ya se encuentra en uso."; break;
                }
            }  
        } catch (SQLException sqle) {
        }
        //se limpia atributo de objeto que existe
        switch(mensaje){
            case "El Número de celular al intentan actualizar ya se encuentra en uso.": emp.setCelular("");break;
            case "El Número de DNI al intentan actualizar ya se encuentra en uso.": emp.setDni("");break;
            case "El Correo Electrónico al intentan actualizar ya se encuentra en uso.": emp.getUsuario().setLogUsuario("");break;
        }
    
        return mensaje;
    }
       
    
}
