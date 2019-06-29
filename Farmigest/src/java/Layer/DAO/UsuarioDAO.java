package Layer.DAO;

 
import Layer.ENTITY.Cliente;
import Layer.ENTITY.Empleado;
import Layer.ENTITY.Usuario;
import static java.lang.String.format;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {
    private Connection connection;
    enum OPERACION {INSERT, UPDATE, DELETE, SELECT}
    
    public UsuarioDAO() {
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

    public Usuario Login(String usuario, String clave)  {
    int idUsuario=0;
    Usuario xusuario = null;
    String query= format("select * from usuario where logUsuario='%s' and pwdUsuario='%s' ",usuario, clave);

    try {
    Statement stmt = connection.createStatement();
    ResultSet res = stmt.executeQuery(query);
        if (res.next()) {

            idUsuario=res.getInt("idUsuario");
            xusuario =new Usuario();
            xusuario.setLogUsuario(usuario);
            xusuario.setIdUsuario(res.getInt("idUsuario"));
            xusuario.setIdNivelUsuario(res.getInt("IdNivelUsuario"));

        if(xusuario.getIdNivelUsuario()==2){
            query = format("select * from cliente where idusuario='%s'",idUsuario);
            ResultSet rs =stmt.executeQuery(query);
            while(rs.next()){
                Cliente cliente= new Cliente();
                cliente.setIdCliente(res.getInt("idCliente"));
                cliente.setNombre(res.getString("Nombre"));
                cliente.setApellidoPaterno(res.getString("ApellidoPaterno"));
                cliente.setApellidoMaterno(res.getString("ApellidoMaterno"));
                cliente.setDni(res.getString("dni"));
                cliente.setSexo(res.getString("Sexo"));
                cliente.setCelular(res.getString("celular"));
                cliente.setFechaNacimiento(res.getString("FechaNacimiento"));
                cliente.setFechaRegistro(res.getString("FechaRegistro"));
                cliente.setIdUsuario(idUsuario);
                cliente.setEstado(res.getInt("Estado"));
                xusuario.setCliente(cliente);
	    }
        }else{
            query = format("select * from empleado where idusuario='%s'",idUsuario);
            ResultSet rs =stmt.executeQuery(query);
            while(rs.next()){
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("idEmpleado"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setApellido(rs.getString("Apellido"));
                empleado.setArea(rs.getString("Area"));
                empleado.setCelular(rs.getString("celular"));
                empleado.setDni(rs.getString("dni"));
                empleado.setSexo(rs.getString("sexo"));
                empleado.setEstado(rs.getInt("estado"));
                xusuario.setEmpleado(empleado); 
            }
        } 
        }
    } catch (SQLException sqle) {
    }
    return xusuario;
    } 

    public String registrar(Cliente cliente)  {
    Usuario usuario = cliente.getUsuario();
    String query="";
    String mensaje="Al parecer hubo un error al registrar, intentelo más tarde.";
    //insertando el usuario
    query= format("INSERT INTO usuario (logUsuario, pwdUsuario, IdNivelUsuario, Estado) values('%s','%s','%s','%s')",
        usuario.getLogUsuario(),usuario.getPwdUsuario(),usuario.getIdNivelUsuario(),usuario.getEstado()); 
    int idUsuario =MySqlExecute(query, OPERACION.INSERT);

    //insertar al cliente
    if(idUsuario!=0){
    cliente.setIdUsuario(idUsuario); //actualizo el id del usuario insertado
    query= format("INSERT INTO cliente ( Nombre, ApellidoPaterno, ApellidoMaterno,DNI, Sexo, Celular, FechaNacimiento, FechaRegistro, idUsuario, Estado) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')",
        cliente.getNombre(),cliente.getApellidoPaterno(),cliente.getApellidoMaterno(),cliente.getDni(),
        cliente.getSexo(),cliente.getCelular(),cliente.getFechaNacimiento(),cliente.getFechaRegistro(),
        cliente.getIdUsuario(),cliente.getEstado());
    int idCliente =MySqlExecute(query, OPERACION.INSERT);

    if (idCliente==0){
        query= format("delete from usuario where idUsuario='%s')",cliente.getIdUsuario());
        MySqlExecute(query, OPERACION.DELETE);
    }else{
        mensaje="El registro se ha realizado satisfactoriamente.";
    }

    }

    return mensaje;
    } 

    public String validarDatos_registro(Cliente cliente){
        String mensaje ="";
        
        String query= " SELECT X.idCliente,X.Celular,X.DNI,Y.logUsuario FROM cliente X INNER JOIN usuario  Y  ON x.idUsuario=y.idUsuario";
        query=query + " where X.Celular='%s' OR X.DNI='%s' OR Y.logUsuario='%s'";
        query= format(query,cliente.getCelular(),cliente.getDni(),cliente.getUsuario().getLogUsuario());
    try {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) { 
            String celular =rs.getString("Celular");
            String dni=rs.getString("DNI");
            String usuario=rs.getString("logUsuario");
            
            if (celular.equals(cliente.getCelular())){
                mensaje="El Número de celular ya existe."; break;
            }else if (dni.equals( cliente.getDni())){
                mensaje="El Número de DNI ya existe."; break;
            }else if (usuario.equals( cliente.getUsuario().getLogUsuario())){
                mensaje="El correo ya existe."; break;
            }
        }  
    } catch (SQLException sqle) {
    }
        switch(mensaje){
            case "El Número de celular ya existe.": cliente.setCelular("");break;
            case "El Número de DNI ya existe.": cliente.setDni("");break;
            case "El correo ya existe.": cliente.getUsuario().setLogUsuario("");break;
        }
    
        return mensaje;
    }


    
    
}



