
package Layer.Controller;

import Layer.ENTITY.DetalleCompra;
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
import static Layer.Controller.Utility.*;
import Layer.DAO.ComprarDAO;
import Layer.ENTITY.Compra;
import Layer.ENTITY.DetalleIngreso;
import Layer.ENTITY.Pago;
import Layer.ENTITY.Usuario;
import java.util.Date;
/**
 *
 * @author admin
 */
@WebServlet(name = "CompraController", urlPatterns = {"/CompraController"})
public class CompraController extends HttpServlet {
        ComprarDAO comprarDAO;
        SimpleDateFormat objFecha = new SimpleDateFormat("yyyy-MM-dd"); 
        ArrayList<DetalleCompra> listaOrden;
        
    public void init(){
        comprarDAO=new ComprarDAO();
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
                    case "quitarProducto":
                        quitar_producto(request, response);
                        break;
                    default:
                        response.sendRedirect("compra.jsp");
                        break;
                }
            } catch (Exception e) {
            }
    }
 
    private void agregarItem(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(); 
        RequestDispatcher dispatcher;
        String mensaje="";
        ArrayList<DetalleIngreso> listaBusqueda =(ArrayList<DetalleIngreso>) session.getAttribute("listaBusqueda");

        int idProducto =Integer.parseInt(request.getParameter("txt_xcodigo"));
        String nombre =request.getParameter("txt_xnombre");
        String descripcion =request.getParameter("txt_xdescripcion");
        String marca =request.getParameter("txt_xmarca");
        double precio =Double.parseDouble(request.getParameter("txt_xprecio"));
        int cantidad =Integer.parseInt(request.getParameter("txt_xcantidad"));
        int idSede = Integer.parseInt(request.getParameter("txt_xidsede"));
        String sedeNombre  =request.getParameter("txt_xsede");

        double monto=  funciones.round(precio*cantidad, 2) ;
        double impuesto =funciones.round(monto *0.18, 2) ;
        double total= funciones.round(monto*1.18, 2) ;

        DetalleCompra detalle = new DetalleCompra();
        detalle.setIdProducto(idProducto);
        detalle.getProducto().setIdProducto(idProducto);
        detalle.getProducto().setNombre(nombre);
        detalle.getProducto().setDescripcion(descripcion);
        detalle.getProducto().setPre_venta(precio);
        detalle.getProducto().setMarca(marca);
        detalle.setPrecio(precio);
        detalle.setCantidad(cantidad);
        detalle.setMonto(monto);
        detalle.setImpuesto(impuesto);
        detalle.setTotal(total);
        detalle.setIdSede(idSede);
        detalle.getSede().setIdSede(idSede);
        detalle.getSede().setDireccion(sedeNombre);
        detalle.setEstado(ACTIVO);


        
        if(listaOrden==null){
            listaOrden = new ArrayList<>();
        }
        
        //verificar si supera la cantidad
        if(superarCantidad_Producto(detalle,listaBusqueda)){
            mensaje="La cantidad ingresada supera el stock del producto, verificar.";
        }else{
            if(Actualiza_ProductoExistente(detalle)==false){
                listaOrden.add(detalle);
            }

            double sumMonto = sumProducts(1);
            double sumImpuesto = sumProducts(2);
            double sumTotal = sumProducts(3);       
        }
            
        session.setAttribute("mensaje", mensaje);
        session.setAttribute("listaOrden", listaOrden);
        totalizado_orden(session);
        response.sendRedirect("compra.jsp");

    }

    public void totalizado_orden(HttpSession session){

    double sumMonto = sumProducts(1);
    double sumImpuesto = sumProducts(2);
    double sumTotal = sumProducts(3);
    session.setAttribute("sumMonto", sumMonto);
    session.setAttribute("sumImpuesto", sumImpuesto);
    session.setAttribute("sumTotal", sumTotal);
    }

    public double sumProducts(int opcion) {
    double sum = 0;
    for (DetalleCompra obj : listaOrden) {
        switch(opcion){
        case 1: sum=sum+obj.getMonto();break;
        case 2: sum=sum+obj.getImpuesto();break;
        case 3: sum=sum+obj.getTotal();break;
        }
    }
    return funciones.round(sum, 2);
    }

    public boolean Actualiza_ProductoExistente(DetalleCompra det) {
        boolean existe = false;
        for (DetalleCompra obj : listaOrden) {
            if (obj.getIdProducto()==det.getIdProducto() & obj.getIdSede()==det.getIdSede()){ //si existe 
                obj.setCantidad(obj.getCantidad()+det.getCantidad());
                obj.setMonto(funciones.round(obj.getCantidad()*obj.getPrecio(),2));
                obj.setImpuesto(funciones.round(obj.getMonto()*0.18,2));
                obj.setTotal(funciones.round(obj.getMonto()*1.18, 2));
                existe = true;
                break;
            }
        }
        return existe;
    }

    public boolean superarCantidad_Producto(DetalleCompra det,ArrayList<DetalleIngreso> busqueda) {
        boolean existe = false;
        int cantidad=det.getCantidad();
        int cantiExistente=0;
        for (DetalleCompra obj : listaOrden) {
            if (obj.getIdProducto()==det.getIdProducto() & obj.getIdSede()==det.getIdSede()){ //si existe
                cantidad = obj.getCantidad()+det.getCantidad();
                break;
            }
        }
        
        for (DetalleIngreso obj : busqueda) {
            if (obj.getIdProducto()==det.getIdProducto() & obj.getIdSede()==det.getIdSede()){ //si existe
                cantiExistente = obj.getCantDisponible();
                break;
            }
        }
        
        if (cantidad>cantiExistente){
            existe=true;
        }
        
        return existe;
    }
 
    private void quitar_producto(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(); 
        int idProducto=Integer.parseInt(request.getParameter("idProducto"));
        for (DetalleCompra obj : listaOrden) {
            if (obj.getIdProducto()==idProducto){
                listaOrden.remove(obj);
                break;
            }
        }
        session.setAttribute("listaOrden", listaOrden);
        totalizado_orden(session);
        response.sendRedirect("compra.jsp");
    }
  
    private void comprar(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
            HttpSession session = request.getSession(); 
            Date objDate=new Date();
            String fecha =objFecha.format(objDate);
            String numTarjeta= request.getParameter("txt_xnumTarjeta");
            double sumMonto= Double.parseDouble(request.getParameter("txt_xsumMonto"));
            double sumImpuesto= Double.parseDouble(request.getParameter("txt_xsumImpuesto"));
            double sumTotal= Double.parseDouble(request.getParameter("txt_xsumTotal"));
            
            Usuario usuario=(Usuario)session.getAttribute("usuario");
            
            Pago pago = new Pago();
            pago.setMonto(sumTotal);
            pago.setNumTarjeta(numTarjeta);
            pago.setFechaPago(fecha);
            
            Compra compra = new Compra();
            compra.setNroDocumento("");
            compra.setIdUsuario(usuario.getIdUsuario());
            compra.setMonto(sumMonto);
            compra.setImpuesto(sumImpuesto);
            compra.setMonoTotal(sumTotal);
            compra.setFechaCompra(fecha);
            compra.setEstadoCompra("Cancelado");
            compra.setEstadoEntrega("Pendiente");
            
            String mensaje=comprarDAO.registrar_compra(compra, listaOrden, pago);
            
            if (mensaje.contains("Su compra ha sido realizada satisfactoriamente con el número de compra")){
                Limpiar(session);
            }
            
            session.setAttribute("mensaje", mensaje);
            response.sendRedirect("compra.jsp");
        }

    private void Limpiar(HttpSession session){
        listaOrden = null;
        session.setAttribute("listaOrden",null);
        session.setAttribute("listaBusqueda",null);
        session.setAttribute("sumMonto", null);
        session.setAttribute("sumImpuesto", null);
        session.setAttribute("sumTotal", null);   
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action= (String) request.getParameter("action");
            try {
                switch(action){
                    case "agregarItem":
                        agregarItem(request, response);
                        break;
                    case "comprar":
                        comprar(request, response);
                        break;
                    default:
                        response.sendRedirect("compra.jsp");
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
