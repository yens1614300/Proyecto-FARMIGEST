package Layer.Controller;

import Layer.DAO.EmpleadoDAO;
import Layer.ENTITY.Empleado;
import Layer.ENTITY.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static Layer.Controller.Utility.*;
 
@WebServlet(name = "EmpleadoController", urlPatterns = {"/EmpleadoController"})
public class EmpleadoController extends HttpServlet {
  
    EmpleadoDAO empDAO;
    SimpleDateFormat objFecha = new SimpleDateFormat("yyyy-MM-dd"); 
  
    public void init(){
        empDAO=new EmpleadoDAO();  
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action= (String) request.getParameter("action");
            try {
                switch(action){
                    case "new":
                        nuevoEmpleado(request, response);
                        break;
                    case "edit":
                        editarEmpleado(request, response);
                        break;
                    case "delete":
                        eliminarEmpleado(request, response);
                        break;
                    case "listar":
                        listarEmpleados(request, response);
                        break;
                    case "create":
                        crearEmpleado(request, response);
                        break;
                    case "update":
                        actualizarEmpleado(request, response);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
            }
    }

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    
 private void listarEmpleados(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        ArrayList<Empleado> listaEmpleado = empDAO.findAll();
        session.setAttribute("listaEmpleado", listaEmpleado);
        session.setAttribute("visibleLista", "");
        session.setAttribute("visibleFormulario", "hidden");
        response.sendRedirect("empleadocrud.jsp");
 }
 
  private void editarEmpleado(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
        Empleado opUsuario = empDAO.findById(idEmpleado);
        
        session.setAttribute("visibleLista", "hidden");
        session.setAttribute("visibleFormulario", "");
        session.setAttribute("op", "editar");
        session.setAttribute("opUsuario", opUsuario);

        response.sendRedirect("empleadocrud.jsp");
 }
  
  private void nuevoEmpleado(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        session.setAttribute("visibleLista", "hidden");
        session.setAttribute("visibleFormulario", "");
        session.setAttribute("op", "nuevo");
        session.setAttribute("opUsuario", null);
        response.sendRedirect("empleadocrud.jsp");
 }
  
  private void crearEmpleado(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        
        String dni = request.getParameter("txtDni");
        String area=request.getParameter("txtArea");
        String nombre = request.getParameter("txtNombre");
        String apellido=request.getParameter("txtApellido");
        String sexo = request.getParameter("txtSexo");
        String celular=request.getParameter("txtCelular");
        String mail = request.getParameter("txtCorreo");
        String clave=request.getParameter("txtClave");
        int idNivel = Integer.parseInt(request.getParameter("txtNivel"));
        
        Empleado emp = new Empleado();
        emp.setDni(dni);
        emp.setArea(area);
        emp.setNombre(nombre);
        emp.setApellido(apellido);
        emp.setSexo(sexo);
        emp.setCelular(celular);
        emp.getUsuario().setLogUsuario(mail);
        emp.getUsuario().setPwdUsuario(clave);
        emp.getUsuario().setEstado(ACTIVO);
        emp.getUsuario().setIdNivelUsuario(idNivel);
        
        String mensaje=empDAO.create(emp);
        
        if (mensaje.equals("Al parecer hubo un error al registrar, intentelo más tarde.")){
            session.setAttribute("mensaje", mensaje);
            session.setAttribute("opUsuario", emp);
        }else{
            ArrayList<Empleado> listaEmpleado = empDAO.findAll();
            session.setAttribute("listaEmpleado", listaEmpleado);
            session.setAttribute("mensaje", mensaje);
            session.setAttribute("opUsuario", null);
        }
        
        //session.setAttribute("visibleLista", "hidden");
        //session.setAttribute("visibleFormulario", "");
        //session.setAttribute("op", "nuevo");
        response.sendRedirect("empleadocrud.jsp");
 }
  
  private void actualizarEmpleado(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        
        String idEmpleado = request.getParameter("txtidEmpleado");
        String idUsuario=request.getParameter("txtidUsuario");
        String dni = request.getParameter("txtDni");
        String area=request.getParameter("txtArea");
        String nombre = request.getParameter("txtNombre");
        String apellido=request.getParameter("txtApellido");
        String sexo = request.getParameter("txtSexo");
        String celular=request.getParameter("txtCelular");
        String mail = request.getParameter("txtCorreo");
        String clave=request.getParameter("txtClave");
        int idNivel = Integer.parseInt(request.getParameter("txtNivel"));
        
        Empleado emp = new Empleado();
        emp.setIdUsuario(Integer.parseInt(idUsuario));
        emp.setDni(dni);
        emp.setArea(area);
        emp.setNombre(nombre);
        emp.setApellido(apellido);
        emp.setSexo(sexo);
        emp.setCelular(celular);
        emp.setIdEmpleado(Integer.parseInt(idEmpleado));
        emp.setIdUsuario(Integer.parseInt(idUsuario));
        
        emp.getUsuario().setLogUsuario(mail);
        emp.getUsuario().setPwdUsuario(clave);
        emp.getUsuario().setIdUsuario(Integer.parseInt(idUsuario));
        emp.getUsuario().setIdNivelUsuario(idNivel);
        
        String mensaje=empDAO.update(emp);
        
        session.setAttribute("opUsuario", emp);
        session.setAttribute("mensaje", mensaje);
        
        //session.setAttribute("visibleLista", "hidden");
        //session.setAttribute("visibleFormulario", "");
        //session.setAttribute("op", "nuevo");
        //session.setAttribute("opUsuario", null);
        response.sendRedirect("empleadocrud.jsp");
 }
  
  private void eliminarEmpleado(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        int idEmpleado=Integer.parseInt(request.getParameter("idEmpleado"));
        
        String mensaje=empDAO.delete(idEmpleado);
        
        ArrayList<Empleado> listaEmpleado = empDAO.findAll();
        session.setAttribute("listaEmpleado", listaEmpleado);
        session.setAttribute("mensaje", mensaje);
        response.sendRedirect("empleadocrud.jsp");
 }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
