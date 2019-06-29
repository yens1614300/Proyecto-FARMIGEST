<%@page import="Layer.ENTITY.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <title>FARMIGEST</title>
     
        <link rel="stylesheet" type="text/css" href="recursos/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="recursos/fontawesome-free/css/fontawesome.css">
        <link rel="stylesheet" type="text/css" href="recursos/datatables/dataTables.bootstrap4.css" >
        <link rel="stylesheet" type="text/css" href="recursos/css/style.css">
        <link href="recursos/css/sb-admin.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"/>
        <script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
    </head>

   <% 
       String modal = (String) request.getSession().getAttribute("mensaje"); if (modal==null) modal="";
       if (modal != ""){
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
       
    <% } %>
    
    <body id="page-top">
        
        <%@include file="pagina_comun/header.jsp" %> 
        
        <div id="wrapper">
            
            <%@include file="pagina_comun/vertical.jsp" %> 
            
            <div id="content-wrapper">
            <!--CONTENIDO-->
            <div class="container-fluid" >
                
                <!-- AREA LISTA EMPLEADO-->
                <div class="card mb-3" ${visibleLista}>
                    <div class="card-header bg-info text-white">
                      <i class="fa fa-users"></i>
                        Medicamento
                    </div>
                       
                    <div class="card-body">
                         
                            <div class="form-row">
                                <div class="form-group col-md-1"></div>
                                <div class="form-group col-md-11"> 
                                    <form action="ProductoController?action=new" method="post"> 
                                    <button type="submit" class="btn btn-primary">
                                        &nbsp;&nbsp;&nbsp;Nuevo&nbsp;&nbsp;&nbsp;</button>                                        
                                    </form>

                                        
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

                                              <th >Nombre</th>
                                              <th >Descripcion</th>
                                              <th >Categoria</th>
                                              <th >Marca</th>
                                              <th style="width:120px"></th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                                <c:forEach var="dc" items="${listaProducto}">
                                                      <tr>
                                                          <td ><c:out value="${dc.nombre}"/></td>
                                                          <td ><c:out value="${dc.descripcion}"/></td>
                                                          <td ><c:out value="${dc.categoria}"/></td>
                                                          <td ><c:out value="${dc.marca}"/></td>
                                                          <td >
                                                              <a href="ProductoController?action=edit&idProducto=${dc.idProducto}" >Editar</a> |
                                                               <a href="ProductoController?action=delete&idProducto=${dc.idProducto}" >Eliminar</a>

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
                            
                <!-- AREA EDITAR/GRABAR EMPLEADO-->
                <div class="card mb-3" ${visibleFormulario}>
                    <div class="card-header bg-info text-white">
                      <i class="fa fa-users"></i>
                        Medicamento
                    </div>
                     <form method="post" action="ProductoController?action=${op == 'editar' ? 'update' : 'create'}" role="form" id="form_login"  >       
                        <div class="card-body">
                            <div class="form-row" hidden>
                                <input type="text" class="form-control" name="txtidProducto"   value="${opProducto.idProducto}" hidden >
                                
                            </div>
                                <!--Nombre de producto -->
                                <div class="form-row">

                                  <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-8">
                                    <label for="inputEmail4" >Nombre</label>
                                    <input type="text" class="form-control" name="txtNombre"   value="${opProducto.nombre}" id="inputAddress" placeholder="Nombre" required>
                                  </div>

                                </div>

                                <!--Descripcion de producto-->
                                <div class="form-row">
                                    <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-8">
                                    <label for="inputEmail4">Descripcion</label>
                                    <input type="text" class="form-control" name="txtDescripcion" value="${opProducto.descripcion}" id="inputAddress" placeholder="Descripcion" required>
                                  </div>

                                </div>

                                 <!--Marca / Categoria-->
                                <div class="form-row">
                                  <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Marca</label>
                                    <input type="text" class="form-control" name="txtMarca" value="${opProducto.marca}" id="inputAddress" placeholder="Marca" required>
                                  </div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Categoria</label>
                                    <input type="text" class="form-control" name="txtCategoria" value="${opProducto.categoria}" id="inputAddress" placeholder="Categoria" required>
                                  </div>
                                </div> 

                                <!--  Precio venta-->
                                <div class="form-row">
                                    <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Precio de Venta</label>
                                    <input type="number" name="txtVenta" value="${opProducto.pre_venta}" min="0" step="0.01" data-number-to-fixed="2" data-number-stepfactor="100" class="form-control currency" id="c1" required />
                                  </div>
        
                                </div>
 
                                <!--OPCION DE REGISTRO-->
                                <div class="dropdown-divider"></div> <!--DIVISOR LINEA--> 
                                <div class="form-row">

                                 <div class="form-group col-md-2"></div>
                                 <div class="form-group col-md-6">
                                     <a href="ProductoController?action=listar" class="small" >Volver</a>
                                 </div>
                                 
                                 
                                 <div class="form-group col-md-2">

                                    <button type="submit"  id="inputAddress" 
                                            class="btn btn-primary btn-block ">${op == 'editar' ? 'Actualizar' : 'Registrar'}</button>

                                  </div>

                                </div>

                        </div>

                     </form>   
                    </div>
     
            </div>       
                                           
            </div>
                                            
        </div>  
   
                                            
<!-- Modal -->
<div class="modal" id="myModal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Mensaje</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>${mensaje}<% session.setAttribute("mensaje", ""); %></p>
      </div>
      <div class="modal-footer">
         
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>                           
                                            
 

    </body> 
    
 
     <script src="recursos/jquery/jquery.min.js" type="text/javascript"></script>
     <script src="recursos/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>
 
     <script src="recursos/js/sb-admin.js" type="text/javascript"></script>
    

        
        
        </html>
