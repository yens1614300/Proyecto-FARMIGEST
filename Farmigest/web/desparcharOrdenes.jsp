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

   
   <% 
       String modal = (String) request.getSession().getAttribute("activarModal"); if (modal==null) modal="";
       if (modal.equals("x")){
       %>
       
        <script>
           $(document).ready(function()
           {
            $('#myModal').modal({
          keyboard: true,
          backdrop: "static"
          });
              
           });
        </script>
       
    <% request.getSession().setAttribute("activarModal", "");
       } %>
   
  
        
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
                        Lista de Ordenes pendientes de entrega
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
                                                    <c:forEach var="dc" items="${listaDespachar}">
                                                          <tr>
                                                              <td ><c:out value="${dc.nroDocumento}"/></td>
                                                              <td ><c:out value="${dc.cliente.nombre}"/></td>
                                                              <td ><c:out value="${dc.cliente.dni}"/></td>
                                                              <td ><c:out value="${dc.fechaCompra}"/></td>
                                                              <td ><c:out value="${dc.estadoEntrega}"/></td>
                                                              <td >
                                                        <button type="button" class="btn btn-info btn-sm smaller" 
                                                         onclick="orderPedido(${dc.idCompra},'${dc.nroDocumento}','${dc.cliente.nombre}',${dc.cliente.dni});">
                                                         <i class="fa fa-share"></i>
                                                          Entregar</button>
                                                                  
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
 
         
<!-- The Modal : DETALLE COMPRA-->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header ">
        <h4 class="modal-title col-sm-8 small "><B>ENTREGA DE PRODUCTOS</B></h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">      
          <form class="form-horizontal" action="DespachoController?action=registrar" method="post">
          
              <table border="0" class="small">
                <tr> 
                    <td  colspan=4><b> N° Documento: </b> ${xdocumento}</td>
                </tr>
                <tr>
                    <th>Cliente:</th>
                    <td>  ${xcliente} </td>
                    <th>N° DNI: </th>
                    <td> ${xdni} </td>
                </tr>
              </table>
   
                <div class="dropdown-divider"></div> <!--DIVISOR -->
                 
            <table class="table table-bordered small" >
                <thead class="thead-dark">
                <tr>
                    <th>Producto</th>
                    <th>Marca</th>
                    <th>Cant.</th>
                </tr>
                </thead>
                <c:forEach var="dc" items="${detalleCompra}">
                <tr>
                    <td>${dc.producto.nombre}</td>
                    <td>${dc.producto.marca}</td>
                    <td>${dc.cantidad}</td>
                </tr>
                </c:forEach>

            </table>
                
          <div class="dropdown-divider"></div> <!--DIVISOR -->
          
        <div class="form-group"> 
          <div class="col-sm-offset-3 col-sm-12">
            <button type="submit" class="btn btn-default smaller">Finalizar Entrega</button>
          </div>
        </div>
            
        </form>
          
          
      </div>

 

    </div>
  </div>
</div>

    </body> 
    
    <script>
        
        function quitarProducto(codigo){
            window.location = "CompraController?action=quitarProducto&idProducto="+codigo;
        }
        
        function orderPedido(codigo,documento,cliente,dni){         
        window.location = "DespachoController?action=detalleCompra&idCompra="+codigo + "&cliente=" + cliente + "&dni=" + dni + "&documento="+documento;

 
       /* $('#myModal').modal({
          keyboard: true,
          backdrop: "static"
          });*/
          

  };
       
    </script>
    
     <script src="recursos/jquery/jquery.min.js" type="text/javascript"></script>
     <script src="recursos/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>
 
     <script src="recursos/js/sb-admin.js" type="text/javascript"></script>
    
</html>
