
package Layer.Controller;

import Layer.DAO.ComprarDAO;
import Layer.DAO.SalidaDAO;
import Layer.ENTITY.Compra;
import Layer.ENTITY.DetalleCompra;
import Layer.ENTITY.Usuario;
import Layer.ENTITY.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "DespachoController", urlPatterns = {"/DespachoController"})
public class DespachoController extends HttpServlet {
    ComprarDAO comprarDAO;
    SalidaDAO salidaDAO;
    SimpleDateFormat objFecha = new SimpleDateFormat("yyyy-MM-dd"); 
    ArrayList<Compra> listaOrden;
        
    public void init(){
        comprarDAO=new ComprarDAO();
        salidaDAO = new SalidaDAO();
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action= (String) request.getParameter("action");
            try {
                switch(action){
                    case "ListaOrdenes":
                        despachar_ordenes(request, response);
                        break;
                    case "detalleCompra":
                        Detalle_compra(request, response);
                        break;
                    case "registrar":
                        registrar_salida(request, response);
                        break;  
                    case "ListaCompras":
                        listar_compras(request ,response);
                        break;
                    default:
                        response.sendRedirect("desparcharOrdenes.jsp");
                        break;
                }
            } catch (Exception e) {
                response.sendRedirect("desparcharOrdenes.jsp");
            }
    }

 private void despachar_ordenes(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        ArrayList<Compra> lista = comprarDAO.ListaDespachar();
        session.setAttribute("listaDespachar", lista);
        response.sendRedirect("desparcharOrdenes.jsp");
 }
  private void listar_compras(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();    
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ArrayList<Compra> lista = comprarDAO.ListaCompras(usuario.getIdUsuario());
        session.setAttribute("listacmp", lista);
        response.sendRedirect("comprasrealizadas.jsp");
 }
 private void Detalle_compra(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        int idCompra =Integer.parseInt(request.getParameter("idCompra"));
        String cliente =(String)request.getParameter("cliente");
        String dni =(String)request.getParameter("dni");
        String documento =(String)request.getParameter("documento");
        
        ArrayList<DetalleCompra> detalle = comprarDAO.DetalleCompra(idCompra);
        session.setAttribute("detalleCompra", detalle);
        session.setAttribute("activarModal", "x");
        session.setAttribute("xcliente", cliente);
        session.setAttribute("xdocumento", documento);
        session.setAttribute("xdni", dni);
        session.setAttribute("xidCompra", idCompra);
        
        response.sendRedirect("desparcharOrdenes.jsp");
 }
 
  private void registrar_salida(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        Date objDate=new Date();
        String fecha =objFecha.format(objDate);
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        int idCompra =(Integer)(request.getSession().getAttribute("xidCompra"));
        
        String mensaje = salidaDAO.registrar_salida(idCompra, usuario.getEmpleado().getIdEmpleado(), fecha);
        
        session.setAttribute("activarModal", null);
        session.setAttribute("xcliente", null);
        session.setAttribute("xdocumento", null);
        session.setAttribute("xdni", null);
        session.setAttribute("xidCompra", null);

        session.setAttribute("mensaje", mensaje);
        //actualizar la lista de despachos pendientes
        ArrayList<Compra> lista = comprarDAO.ListaDespachar();
        session.setAttribute("listaDespachar", lista);
        response.sendRedirect("desparcharOrdenes.jsp");
 }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action= (String) request.getParameter("action");
        try {
            switch(action){
                case "ListaOrdenes":
                    despachar_ordenes(request, response);
                    break;
                 case "registrar":
                    registrar_salida(request, response);
                    break;                   
                    
                default:
                    response.sendRedirect("desparcharOrdenes.jsp");
                    break;
            }
        } catch (Exception e) {
              response.sendRedirect("desparcharOrdenes.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
