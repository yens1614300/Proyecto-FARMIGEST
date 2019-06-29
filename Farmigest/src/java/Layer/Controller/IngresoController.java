package Layer.Controller;

import Layer.DAO.IngresoDAO;
import Layer.DAO.ProductoDao;
import Layer.DAO.SedeDAO;
import Layer.ENTITY.DetalleIngreso;
import Layer.ENTITY.Ingreso;
import Layer.ENTITY.Producto;
import Layer.ENTITY.Sede;
import Layer.ENTITY.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "IngresoController", urlPatterns = {"/IngresoController"})
public class IngresoController extends HttpServlet {
        ProductoDao proDAO;
        IngresoDAO ingDAO;
        SedeDAO sedeDAO;
        ArrayList<DetalleIngreso> listaIngreso;
    public void init(){
        
        ingDAO = new IngresoDAO();
        proDAO=new ProductoDao();
        sedeDAO = new SedeDAO();
        listaIngreso= new ArrayList<>();
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action= (String) request.getParameter("action");
            
            HttpSession session = request.getSession();
            
            
            String tdoc=request.getParameter("txtTipo");
            String doc=request.getParameter("txtDocumento");
            String fecha=request.getParameter("txtFeha");
            String ruc=request.getParameter("txtRuc");
            String sede=request.getParameter("txtSede");
            if (tdoc!=null){
                session.setAttribute("txtTipo", tdoc);
                session.setAttribute("txtDocumento",doc);
                session.setAttribute("txtFeha", fecha);
                session.setAttribute("txtRuc", ruc);
                session.setAttribute("txtSede", sede);
            }

            try {
                switch(action){
                    case "ingreso":
                        registroIngreso(request, response);
                        break;
                    case "listar":
                        listar(request, response);
                        break;
                    case "buscar":
                        buscarProducto(request, response);
                        break;
                    case "agregarProducto":
                        agregarProducto(request, response);
                        break;
                    case "habilitarBusqueda":
                        habilitaOpcionBusqueda(request, response);
                        break;
                    case "registrar":
                        registroIngreso(request, response);
                        break;
                    case "new":
                        nuevo(request, response);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
            }
    }
   
    private void habilitaOpcionBusqueda(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.setAttribute("estadoBusqueda", "");
        response.sendRedirect("ingresomedicamento.jsp");
    }
    
    private void nuevo(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.setAttribute("visibleLista", "hidden");
        session.setAttribute("visibleFormulario", "");
        response.sendRedirect("ingresomedicamento.jsp");
    }
    
    private void listar(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        ArrayList<Sede> sedes= sedeDAO.lista();
        ArrayList<Ingreso> ingresos = ingDAO.findAll();
        session.setAttribute("ingresos", ingresos);
        
        session.setAttribute("estadoBusqueda", "hidden");
        session.setAttribute("estadoMensaje", "hidden");
        session.setAttribute("sedes", sedes);
        
        session.setAttribute("visibleLista", "");
        session.setAttribute("visibleFormulario", "hidden");
        response.sendRedirect("ingresomedicamento.jsp");
    }
   
    private void registroIngreso(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(); 
        String documento=request.getParameter("txtDocumento");
        String tipoDocumento=request.getParameter("txtTipo");
        String fecha=request.getParameter("txtFeha");
        String ruc=request.getParameter("txtRuc");
        String idSede=request.getParameter("txtSede");
        
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        
        ArrayList<DetalleIngreso> detalle =(ArrayList<DetalleIngreso>)session.getAttribute("listaIngreso");
        
        Ingreso ingreso = new Ingreso();
        ingreso.setNroDocumento(documento);
        ingreso.setTipoDocumento(tipoDocumento);
        ingreso.setFechaIngreso(fecha);
        ingreso.setRucproveedor(ruc);
        ingreso.setIdSede(Integer.parseInt(idSede));
        ingreso.setDetalle(detalle);
        ingreso.setIdEmpleado(usuario.getEmpleado().getIdEmpleado()); //capturo el id del empleadowh
        String mensaje="";
        if (detalle!=null & ruc.length()==11){
             mensaje=ingDAO.registrar_ingreso(ingreso);
            if (!mensaje.equals("Al parecer hubo un error al registrar el ingreso, intentelo más tarde.")){
               listaIngreso=null;
               session.setAttribute("listaIngreso", null);
               session.setAttribute("listaBusqueda", null);
               session.setAttribute("estadoBusqueda", "hidden");
                session.setAttribute("txtTipo", null);
                session.setAttribute("txtDocumento",null);
                session.setAttribute("txtFeha", null);
                session.setAttribute("txtRuc", null);
                session.setAttribute("txtSede", null);
                
                ArrayList<Ingreso> ingresos = ingDAO.findAll();
                session.setAttribute("ingresos", ingresos);
            }
        }else{
            if (ruc.length()!=11 ){
                mensaje="El número de RUC no es válido, verificar!";
            }else{
                mensaje="Falta agregar items, verificar!";
            }
            
        }
        
        session.setAttribute("mensaje", mensaje);
        session.setAttribute("estadoMensaje", "");
        response.sendRedirect("ingresomedicamento.jsp");
    }
    
    private void buscarProducto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
           HttpSession session = request.getSession(); 
           String filtro =request.getParameter("txtFiltro");
           String fecha = request.getParameter("txtFeha");
           
           ArrayList<Producto> listaBusqueda = proDAO.Listar_busqueda_ing(filtro);
           session.setAttribute("listaBusqueda", listaBusqueda);
           
           response.sendRedirect("ingresomedicamento.jsp");
       }

    private void agregarProducto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
           HttpSession session = request.getSession(); 
           
           if(listaIngreso==null){
               listaIngreso = new ArrayList<>();
           }
           String fecha = request.getParameter("txtFeha");
           String codigo =request.getParameter("txt_xcodigo");
           String nombre =request.getParameter("txt_xnombre");
           String descripcion =request.getParameter("txt_xdescripcion");
           String marca =request.getParameter("txt_xmarca");
           String cantidad =request.getParameter("txt_xcantidad");
 
           DetalleIngreso det = new DetalleIngreso();
           det.setIdProducto(Integer.parseInt(codigo));
           det.getProducto().setNombre(nombre);
           det.getProducto().setDescripcion(descripcion);
           det.getProducto().setMarca(marca);
           det.setCantDisponible(Integer.parseInt(cantidad));
           det.setCantidad(Integer.parseInt(cantidad));
           listaIngreso.add(det);
           det = null;
           
           session.setAttribute("listaIngreso", listaIngreso);
           session.setAttribute("listaBusqueda", null);
           session.setAttribute("estadoBusqueda", "hidden");
           response.sendRedirect("ingresomedicamento.jsp");
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
