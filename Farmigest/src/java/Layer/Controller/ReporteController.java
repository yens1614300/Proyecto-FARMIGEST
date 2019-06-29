/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Layer.Controller;

import Layer.DAO.ComprarDAO;
import Layer.ENTITY.Compra;
import Layer.ENTITY.Mes;
import Layer.ENTITY.Producto;
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

/**
 *
 * @author admin
 */
@WebServlet(name = "ReporteController", urlPatterns = {"/ReporteController"})
public class ReporteController extends HttpServlet {
    ComprarDAO cmpDao;
    SimpleDateFormat objFecha = new SimpleDateFormat("yyyy-MM-dd"); 
    
        public void init(){
        cmpDao=new ComprarDAO();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action= (String) request.getParameter("action");
            try {
                switch(action){
                    case "":
                        principal(request,response);
                        break;
                    case "filtro":
                        listarProdutos(request,response);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                response.sendRedirect("reporteventas.jsp");
            }
    }
    
    
 private void listarProdutos(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();  
        
        String fechaIni= request.getParameter("txtFecha_ini");
        String fechaFin= request.getParameter("txtFecha_fin");   
        String checkAnual =   request.getParameter("chkReporteAnual");
        
        if (checkAnual==null) checkAnual="";
            session.setAttribute("disabledFecha", "");
            session.setAttribute("mensaje", "");
            session.setAttribute("visiblemensaje", "hidden");
            session.setAttribute("reporteMes", null);
            session.setAttribute("reporteCompra", null);
            
            if(!checkAnual.equals("on")){
                 session.setAttribute("checked", "");
                if(Integer.parseInt( fechaIni.replace("-", "") )> Integer.parseInt( fechaFin.replace("-", ""))){
                    session.setAttribute("mensaje", "La fecha inicial no puede ser mayor a la fecha final, verificar.");
                    session.setAttribute("visiblemensaje", "");
                    session.setAttribute("visibleTabla", "hidden");
                    session.setAttribute("visibleTablaMes", "hidden");
                  
                }else{
                    ArrayList<Compra> reporteCompra = cmpDao.listaVenta(fechaIni,fechaFin);
                    session.setAttribute("reporteCompra", reporteCompra);
                    session.setAttribute("visibleTabla", "");
                    session.setAttribute("visibleTablaMes", "hidden");
                    session.setAttribute("mensaje", "");
                    
                }

            }else{
                session.setAttribute("disabledFecha", "disabled");
                session.setAttribute("checked", "checked");
                ArrayList<Mes> reporteMes= cmpDao.listaVentaxMes(fechaIni.substring(0,4));
                session.setAttribute("reporteMes", reporteMes);
                session.setAttribute("visibleTablaMes", "");
                session.setAttribute("visibleTabla", "hidden");
            }
 

        
        session.setAttribute("fechaInicial", fechaIni);
        session.setAttribute("fechaFinal", fechaFin);
        
        response.sendRedirect("reporteventas.jsp");
 }
 
 private void principal(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();  
        session.setAttribute("visiblemensaje", "hidden");
        session.setAttribute("visibleTablaMes", "hidden");
        session.setAttribute("visibleTabla", "hidden");
        session.setAttribute("disabledFecha", "");
        response.sendRedirect("reporteventas.jsp");
 }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
