
package Layer.Controller;

import Layer.DAO.UsuarioDAO;
import Layer.ENTITY.Cliente;
import Layer.ENTITY.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import static Layer.Controller.Utility.*;

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {
    UsuarioDAO usuarioDao;
    SimpleDateFormat objFecha = new SimpleDateFormat("yyyy-MM-dd"); 
    
    public void init(){
        usuarioDao= new UsuarioDAO();
    }
            
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
         String action= (String) request.getParameter("action");
        try {
            switch(action){
                case "index":
                    index(request, response);
                    break;
                case "login":
                    login(request, response);
                    break;
                case "registrar":
                    registrar(request, response);
                    break;
                case "cerrarsesion":
                    cerrar_sesion(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } catch (Exception e) {
        } 
        
    }

    private void index(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher= request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
    
    private void login(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(); 
        session.setMaxInactiveInterval(600*3);
        String usuario =request.getParameter("txtUsuario");
        String clave = request.getParameter("txtClave");
        Usuario xusuario= usuarioDao.Login(usuario, clave);
        RequestDispatcher dispatcher;
        if (xusuario!=null){
            session.setAttribute("usuario", xusuario);
            response.sendRedirect("principal.jsp");
        }else{
            session.setAttribute("mensaje", "Usuario o contraseña incorrecta");
            response.sendRedirect("index.jsp");
        }
        
        //dispatcher.forward(request, response);
    }
    
    private void registrar(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        
        Date objDate=new Date();
        String fecha =objFecha.format(objDate);
        
        String nombre = request.getParameter("txtNombre");
        String paterno = request.getParameter("txtPaterno");
        String materno = request.getParameter("txtMaterno");
        String celular = request.getParameter("txtCelular");
        String nacimiento = request.getParameter("txtFechaNacimiento");
        String dni = request.getParameter("txtDni");
        String sexo = request.getParameter("txtSexo");
        String usuario =request.getParameter("txtCorreo");
        String clave = request.getParameter("txtClave");
        
        if (dni.length()!= 8){
            session.setAttribute("mensaje", "El número de DNI no es válidos,verificar.");
        }else if (celular.length()<9){
            session.setAttribute("mensaje", "El número de Celular no es válidos,verificar.");
        }else{
            Cliente cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setApellidoPaterno(paterno);
            cliente.setApellidoMaterno(materno);
            cliente.setDni(dni);
            cliente.setSexo(sexo);
            cliente.setCelular(celular);
            cliente.setFechaNacimiento(nacimiento);
            cliente.setFechaRegistro(fecha);
            cliente.setEstado(ACTIVO);
            Usuario usu = new Usuario();
            usu.setLogUsuario(usuario);
            usu.setPwdUsuario(clave);
            usu.setIdNivelUsuario(NIVEL_CLIENTE);
            usu.setEstado(ACTIVO);
            cliente.setUsuario(usu);
            usu.setCliente(cliente);
            String valida = usuarioDao.validarDatos_registro(cliente);
            if (valida==""){
                String respuesta= usuarioDao.registrar(cliente);
                session.setAttribute("usuario", usu);
                response.sendRedirect("principal.jsp");
            }else{
                session.setAttribute("mensaje", valida);
            }
        }
        
        
        response.sendRedirect("registrarUsuario.jsp");

        
    }
    
    private void cerrar_sesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            HttpSession session = request.getSession();
            session.invalidate();
//            session.setAttribute("usuario", null);
//            session.setAttribute("listaBusqueda",null);
//            session.setAttribute("sumMonto", null);
//            session.setAttribute("sumImpuesto", null);
//            session.setAttribute("sumTotal", null);
//            session.setAttribute("listaOrden", null);
            
            response.sendRedirect("index.jsp");
    }	

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        
        
        String action= (String) request.getParameter("action");
        try {
            switch(action){
                case "index":
                    index(request, response);
                    break;
                case "login":
                    login(request, response);
                    break;
                case "registrar":
                    registrar(request, response);
                    break;
                case "cerrarsesion":
                    cerrar_sesion(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } catch (Exception e) {
        }        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
