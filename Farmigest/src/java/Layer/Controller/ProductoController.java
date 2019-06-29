
package Layer.Controller;

import Layer.DAO.ProductoDao;
import Layer.ENTITY.DetalleIngreso;
import Layer.ENTITY.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ProductoController", urlPatterns = {"/ProductoController"})
public class ProductoController extends HttpServlet {
    ProductoDao productoDao;
    SimpleDateFormat objFecha = new SimpleDateFormat("yyyy-MM-dd"); 
    
    public void init(){
        productoDao=new ProductoDao();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action= (String) request.getParameter("action");
            try {
                switch(action){
                    case "new":
                        nuevoProduto(request, response);
                        break;
                    case "edit":
                        editarProduto(request, response);
                        break;
                    case "delete":
                        eliminarProduto(request, response);
                        break;
                    case "listar":
                        listarProdutos(request, response);
                        break;
                    case "create":
                        crearProduto(request, response);
                        break;
                    case "update":
                        actualizarProduto(request, response);
                        break;
                    case "buscar":
                        buscar(request, response);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                response.sendRedirect("medicamentocrud.jsp");
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
    
    private void buscar(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
           HttpSession session = request.getSession(); 
           String filtro =request.getParameter("txtFiltro");
 
           ArrayList<DetalleIngreso> listaBusqueda = productoDao.Listar_busqueda(filtro);
           session.setAttribute("listaBusqueda", listaBusqueda);
           response.sendRedirect("compra.jsp");

       }

 private void listarProdutos(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        ArrayList<Producto> listaProducto = productoDao.findAll();
        session.setAttribute("listaProducto", listaProducto);
        session.setAttribute("visibleLista", "");
        session.setAttribute("visibleFormulario", "hidden");
        response.sendRedirect("medicamentocrud.jsp");
 }
 
  private void editarProduto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto opProducto = productoDao.findById(idProducto);
        
        session.setAttribute("visibleLista", "hidden");
        session.setAttribute("visibleFormulario", "");
        session.setAttribute("op", "editar");
        session.setAttribute("opProducto", opProducto);

        response.sendRedirect("medicamentocrud.jsp");
 }
  
  private void nuevoProduto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        session.setAttribute("visibleLista", "hidden");
        session.setAttribute("visibleFormulario", "");
        session.setAttribute("op", "nuevo");
        session.setAttribute("opProducto", null);
        response.sendRedirect("medicamentocrud.jsp");
 }
  
  private void crearProduto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        
        String nombre = request.getParameter("txtNombre");
        String Descripcion=request.getParameter("txtDescripcion");
        double pre_venta=Double.parseDouble( request.getParameter("txtVenta"));
        String categoria = request.getParameter("txtCategoria");
        String marca=request.getParameter("txtMarca");

        Producto pro = new Producto();
        pro.setNombre(nombre);
        pro.setDescripcion(Descripcion);
        pro.setPre_venta(pre_venta);
        pro.setCategoria(categoria);
        pro.setMarca(marca);
        
        String mensaje = productoDao.validate(pro);
        if(mensaje.equals("")){
            mensaje=productoDao.create(pro);

            if (mensaje.equals("Al parecer hubo un error al registrar, intentelo más tarde.")){
                session.setAttribute("mensaje", mensaje);
                session.setAttribute("opProducto", pro);
            }else{
                ArrayList<Producto> listaProducto = productoDao.findAll();
                session.setAttribute("listaProducto", listaProducto);
                session.setAttribute("mensaje", mensaje);
                session.setAttribute("opProducto", null);
            }           
        }else{
            session.setAttribute("mensaje", mensaje);
            session.setAttribute("opProducto", pro);
        }

        
        //session.setAttribute("visibleLista", "hidden");
        //session.setAttribute("visibleFormulario", "");
        //session.setAttribute("op", "nuevo");
        response.sendRedirect("medicamentocrud.jsp");
 }
  
  private void actualizarProduto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        
        int idProducto =Integer.parseInt(request.getParameter("txtidProducto"));
        String nombre = request.getParameter("txtNombre");
        String Descripcion=request.getParameter("txtDescripcion");
        double pre_venta=Double.parseDouble( request.getParameter("txtVenta"));
        String categoria = request.getParameter("txtCategoria");
        String marca=request.getParameter("txtMarca");

        Producto pro = new Producto();
        pro.setIdProducto(idProducto);
        pro.setNombre(nombre);
        pro.setDescripcion(Descripcion);
        pro.setPre_venta(pre_venta);
        pro.setCategoria(categoria);
        pro.setMarca(marca);
        
        
        String mensaje=productoDao.validateUpdate(pro);
        if(mensaje.equals("")){
            mensaje=productoDao.update(pro);
        }
        session.setAttribute("opProducto", pro);
        session.setAttribute("mensaje", mensaje);
 
        response.sendRedirect("medicamentocrud.jsp");
 }
  
  private void eliminarProduto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();     
        int idProducto=Integer.parseInt(request.getParameter("idProducto"));
        String mensaje = productoDao.validateDelete(idProducto);
        
        if (mensaje==""){
            mensaje=productoDao.delete(idProducto);
            ArrayList<Producto> listaProducto = productoDao.findAll();
            session.setAttribute("listaProducto", listaProducto);
        
        }
        session.setAttribute("mensaje", mensaje);
        response.sendRedirect("medicamentocrud.jsp");
 }
    
 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
