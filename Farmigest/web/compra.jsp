<%@page import="Layer.ENTITY.Producto;"%>
<%@page import="Layer.ENTITY.DetalleIngreso;"%>  
<%@page import="Layer.ENTITY.Sede;"%>  

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
                      <i class="fa fa-shopping-cart"></i>
                     Compra
                    </div>
                    

                         
                    <div class="card-body">

                        <div class="form-row">
                            <div class="form-group col-md-1"></div>
                            <div class="form-group col-md-11"> 
                                    <p class="text-success">${mensaje}</p>
                                    <% session.setAttribute("mensaje", ""); %>
                            </div>
                        </div>
                        
                            <form method="post" action="ProductoController?action=buscar" role="form" id="form_login"  >                 
                            <!--BUSCAR MEDICAMENTO-->
                                <div class="form-row">
                                    <div class="form-group col-md-1"></div>
                                    <div class="form-group col-md-10">
                                        <div class="input-group">    

                                        <input type="text" name="txtFiltro"  class="form-control" 
                                               placeholder="Buscar Medicamento" value="" required  >
                                        <button  type="submit" class="btn btn-primary block" ><i class="fa fa-search"></i> </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        
                         <!-- BUSQUEDA DE PRODUCTO-->
                         
                            <div class="form-row">
                                
                                  <div class="form-group col-md-1"></div>
                                  <div class="form-group col-md-10">
                                    <div class="table-striped">
                                         
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                          <thead>
                                            <tr>
                                              <th hidden>Codigo</th>
                                              <th>Nombre</th>
                                              <th>Descripción</th>
                                              <th>Marca</th>
                                              <th>P.Unitario</th>
                                              <th>Stock</th>
                                              <th>Sede</th> 
                                              <th style="width:90px"> </th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                          <c:forEach var="det" items="${listaBusqueda}">                     
                                                <tr>
                                                    <td hidden><c:out value="${det.producto.idProducto}"/></td>
                                                    <td ><c:out value="${det.producto.nombre}"/></td>
                                                    <td ><c:out value="${det.producto.descripcion}"/></td>
                                                    <td ><c:out value="${det.producto.marca}"/></td>
                                                    <td style="width:60px"><c:out value="${det.producto.pre_venta}"/></td>
                                                    <td ><c:out value="${det.cantDisponible}"/></td>
                                                    <td style="width:100px"><c:out value="${det.sede}"/></td>
                                                    <td>
                                                        <button type="button" class="btn btn-info btn-sm" 
                                                         onclick="orderModal(${det.producto.idProducto},'${det.producto.nombre}','${det.producto.descripcion}','${det.producto.marca}',${det.producto.pre_venta},${det.idSede},'${det.sede}',${det.cantDisponible});">
                                                         <i class="fa fa-cart-plus"></i>
                                                          Agregar</button>
                                                    </td>
                                                </tr>
                                          </c:forEach>
                                          </tbody>
                                        </table>
                                      </div>
                                  </div>
                                  
                                  <div class="form-group col-md-1"></div>
                            </div>
                        
                            <div class="dropdown-divider"></div> <!--DIVISOR -->
                            
                            <form method="post" action="" role="form" id="form_login"  >                 
                            <!--TABLA PRODUCTOS SELECCIONADOS-->
                                <div class="form-row">
                                      <div class="form-group col-md-1"></div>
                                      <div class="form-group col-md-10">
                                        <div class="table-striped">
                                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                                <thead class="thead-dark">
                                                <tr>
                                                  <th style="width:60px" ></th>
                                                  <th >Nombre</th>
                                                  <th>Marca</th>
                                                  <th style="width:200px">Sede</th>
                                                  <th style="width:50px">Cantidad</th>
                                                  <th style="width:70px">Precio</th>
                                                  <th style="width:70px">Monto</th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                                    <c:forEach var="dc" items="${listaOrden}">
                                                          <tr>
                                                            <td >
                                                                <button type="button" class="btn btn-danger btn-sm" 
                                                                 onclick="quitarProducto(${dc.idProducto});">
                                                                 <i class="fa fa-remove"></i>
                                                                  Quitar</button>
                                                            </td>
                                                              <td scope="row"><c:out value="${dc.producto.nombre}"/></td>
                                                              <td scope="row"><c:out value="${dc.producto.marca}"/></td>
                                                              <td scope="row"><c:out value="${dc.sede.direccion}"/></td>
                                                              <td scope="row"><c:out value="${dc.cantidad}"/></td>
                                                              <td scope="row"><c:out value="${dc.precio}"/></td>
                                                              <td scope="row"><c:out value="${dc.monto}"/></td>
                                                          </tr>
                                                    </c:forEach>
                                              <thead>
                                                      <tr>
                                                          <th colspan="5"></th>
                                                          <th class="bg-color bg-dark">Subtotal:</th>
                                                          <th class="bg-color bg-dark">${sumMonto}</th> 
                                                      </tr>
                                                      <tr >
                                                          <th colspan="5"></th>
                                                          <th class="bg-color bg-dark" >Impuesto:</th>
                                                          <th class="bg-color bg-dark">${sumImpuesto}</th> 
                                                      </tr>
                                                      <tr>
                                                          <th colspan="5"></th>
                                                          <th class="bg-color bg-dark">Total:</th>
                                                          <th class="bg-color bg-dark">${sumTotal}</th> 
                                                      </tr>
                                               </thead>           
                                                          
                                              </tbody>
                                            </table>
                                          </div>
                                      </div>
                                      <div class="form-group col-md-1"></div>
                                </div>
                            </form>              
                                                          
                            <div class="dropdown-divider"></div> <!--DIVISOR -->
                            
                                              
                            <!--BOTON COMPRAR-->
                                <div class="form-row">
                                    <div class="form-group col-md-10"></div>
                                    <div class="form-group col-md-2">
                                        <button  type="submit" class="btn btn-primary block" onclick="ordenCompra();" >Comprar</button>
                                    </div>
                                </div>   
                    </div>
    
                </div>
            </div>
    
            </div>  
 
         
            
<!-- The Modal : AGREGAR PRODUCTOS-->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header ">
          <h5 class="modal-title ">Producto:
          <label class="control-label" id="xnombre"></label>
          </h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">      
          <form class="form-horizontal" action="CompraController?action=agregarItem" method="post">
          
            <div class="form-group" hidden>
            <label class="control-label col-sm-3" for="email">Codigo:</label>
            <div class="col-sm-12">
                <input type="text" name="txt_xcodigo" class="form-control" id="xcodigo" readonly>
            </div>
          </div>
            
            <div class="form-group" hidden>
            <label class="control-label col-sm-3" for="email">idSede:</label>
            <div class="col-sm-12">
              <input type="text" name="txt_xidsede" class="form-control" id="xidsede" readonly>
            </div>
          </div>

            <div class="form-row align-content-md-center" >
                <div class="form-group col-md-1"></div>
                <label class="control-label col-md-3" for="email">Descripción:</label>
                <input type="text" name="txt_xdescripcion" class="form-control col-md-7" id="xdescripcion" readonly>
            </div>

            <div class="form-row align-content-md-center" >
                <div class="form-group col-md-1"></div>
                <label class="control-label col-md-3" for="email">Marca:</label>
                <input type="text" name="txt_xmarca" class="form-control  col-md-7" id="xmarca" readonly>
            </div>

            <div class="form-row align-content-md-center" >
                <div class="form-group col-md-1"></div>
                <label class="control-label col-md-3" for="email">Sede:</label>
                <input type="text" name="txt_xsede" class="form-control  col-md-7" id="xsede" readonly>
            </div>


            <div class="form-row align-content-md-center" >
                <div class="form-group col-md-1"></div>
                <label class="control-label col-md-3" for="email">Precio:</label>
                <input type="text" name="txt_xprecio" class="form-control  col-md-7" id="xprecio" readonly>
            </div>

            <div class="form-row align-content-md-center" >
                <div class="form-group col-md-1"></div>
                <label class="control-label col-md-3" for="email">Stock:</label>
                <input type="text" name="txt_xstock" class="form-control  col-md-7" id="xstock" readonly>
            </div>

            <div class="form-row align-content-md-center" >
                <div class="form-group col-md-1"></div>
                <label class="control-label col-md-3" for="email">Cantidad</label>
                <input type="number" name="txt_xcantidad" id="txt_xcantidad" min="1" class="form-control  col-md-7" required>
            </div>
              
 
            
          <div class="dropdown-divider"></div> <!--DIVISOR -->
          
        <div class="form-group"> 
          <div class="col-sm-offset-3 col-sm-12">
            <button type="submit" class="btn btn-default">Agregar</button>
          </div>
        </div>
            
        </form>
          
          
      </div>

 

    </div>
  </div>
</div>
 
<!-- The Moda : REALIZAR COMPRA -->
<div class="modal" id="myModalCompra">
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header ">
        <h4 class="modal-title col-sm-6 ">Comprar</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">      
          <form class="form-horizontal" action="CompraController?action=comprar" method="post">
          
            <div class="form-group" hidden>
            <label class="control-label col-sm-6" for="email">Fecha</label>
            <div class="col-sm-12">
                <input type="text"  class="form-control" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-6" for="email">Cantidad Items:</label>
            <div class="col-sm-12">
              <input type="text"  class="form-control" value="${listaOrden.size()}" readonly>
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-sm-6" for="email">Subtotal:</label>
            <div class="col-sm-12">
              <input type="text"  name="txt_xsumMonto" class="form-control" value="${sumMonto}" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-6" for="email">Impuesto:</label>
            <div class="col-sm-12">
              <input type="text" name="txt_xsumImpuesto" class="form-control" value="${sumImpuesto}" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-6" for="email">Monto a Pagar:</label>
            <div class="col-sm-12">
                <input type="text"   name="txt_xsumTotal" class="form-control" value="${sumTotal}" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-6" for="email">Número de Tarjeta:</label>
            <div class="col-sm-12">
                <input type="number" name="txt_xnumTarjeta" class="form-control" value="" required>
            </div>
          </div>
            
          <div class="dropdown-divider"></div> <!--DIVISOR -->
          
        <div class="form-group"> 
          <div class="col-sm-offset-3 col-sm-12">
            <button type="submit" class="btn btn-default">Pagar</button>
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
        
        function orderModal(codigo, nombre, descripcion, marca, precio,idsede, sede,stock){
          // get the current info in html table 
          var xcodigo = codigo;
          var xnombre = nombre;
          var xdescripcion = descripcion;
          var xmarca = marca;
          var xprecio =precio;
          var xidsede=idsede;
          var xsede=sede;
          var xstock=stock;
          
          document.getElementById("xcodigo").value=xcodigo;
          document.getElementById("xnombre").innerHTML =xnombre;
          document.getElementById("xdescripcion").value=xdescripcion;
          document.getElementById("xmarca").value=xmarca;
          document.getElementById("xprecio").value=xprecio;
          document.getElementById("xidsede").value=xidsede;
          document.getElementById("xsede").value=xsede;
          document.getElementById("xstock").value=xstock;

          $("#txt_xcantidad").prop('max',xstock);
          
          $('#myModal').modal({
            keyboard: true,
            backdrop: "static"
          });
        };
        
        //myModalCompra
        function ordenCompra(){
          $('#myModalCompra').modal({
            keyboard: true,
            backdrop: "static"
          });
        }
    </script>
    
     <script src="recursos/jquery/jquery.min.js" type="text/javascript"></script>
     <script src="recursos/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script>
 
     <script src="recursos/js/sb-admin.js" type="text/javascript"></script>
    
</html>