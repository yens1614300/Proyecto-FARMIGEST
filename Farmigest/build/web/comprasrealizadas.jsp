<%@page import="Layer.ENTITY.Compra;"%>  
<%@page import="Layer.ENTITY.Cliente;"%>  
<%@page import="Layer.ENTITY.Usuario;"%>  
<%@page import="Layer.ENTITY.Producto;"%>  


 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FARMIGEST - COMPRA ONLINE</title>
        
        <link rel="stylesheet" type="text/css" href="recursos/bootstrap/css/bootstrap.min.css">
        <link href="recursos/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="recursos/datatables/dataTables.bootstrap4.css" >
        <link rel="stylesheet" type="text/css" href="recursos/css/style.css">
        <link href="recursos/css/sb-admin.css" rel="stylesheet" type="text/css"/>
        <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
        
    </head>

   
 
  
        
    <body id="page-top">
        
        <%@include file="pagina_comun/header.jsp" %> 
        
        <div id="wrapper">
            
            <%@include file="pagina_comun/vertical.jsp" %> 
            
 

            <div id="content-wrapper">
            <!--CONTENIDO-->
            <div class="container-fluid">
                <!-- AREA RESERVA CARD-->
                <div class="card mb-3">
                    <div class="card-header bg-info text-white">
                      <i class="fa fa-truck"></i>
                        Lista de compras realizadas
                    </div>
                       
                    <div class="card-body">

                        <div class="form-row">
                            <div class="form-group col-md-1"></div>
                            <div class="form-group col-md-11"> 
                                    <p class="text-success">${mensaje}</p>
                                    <% session.setAttribute("mensaje", ""); %>
                            </div>
                        </div>
                             <form method="post" action="" role="form" id="form_login"  >                 
                            <!--TABLA PRODUCTOS SELECCIONADOS-->
                                <div class="form-row">
                                      <div class="form-group col-md-1"></div>
                                      <div class="form-group col-md-10">
                                        <div class="table-striped">
                                            <table class="table table-bordered small" id="dataTable" width="100%" cellspacing="0">
                                                <thead class="thead-dark ">
                                                <tr>
                                                 
                                                  <th >N° Documento</th>
                                                  <th >Cliente</th>
                                                  <th >DNI</th>
                                                  <th >Fecha</th>
                                                  <th>Estado</th>
                                                  <th style="width:70px"></th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                    <c:forEach var="dc" items="${listacmp}">
                                                          <tr>
                                                              <td ><c:out value="${dc.nroDocumento}"/></td>
                                                              <td ><c:out value="${dc.cliente.nombre}"/></td>
                                                              <td ><c:out value="${dc.cliente.dni}"/></td>
                                                              <td ><c:out value="${dc.fechaCompra}"/></td>
                                                              <td ><c:out value="${dc.estadoEntrega}"/></td>
                                                              <td >
                                        
                                                                  
                                                              </td>
                                                          </tr>
                                                    </c:forEach>
                                              <thead  >
          
                                               </thead>           
                                                          
                                              </tbody>
                                            </table>
                                          </div>
                                      </div>
                                      <div class="form-group col-md-1"></div>
                                </div>
                            </form>  
 
                    </div>
                    
                </div>
            </div>
            </div>  
 
         
 
    </body> 
    
 
    
     <script src="recursos/jquery/jquery.min.js" type="text/javascript"></script>
     <script src="recursos/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>
 
     <script src="recursos/js/sb-admin.js" type="text/javascript"></script>
    
</html>
