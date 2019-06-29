<%@page import="Layer.ENTITY.Cliente"%>
<%@page import="Layer.ENTITY.Usuario"%>
<%@page import="Layer.ENTITY.Empleado"%>
<%@page import="Layer.ENTITY.Producto"%>
<%@page import="Layer.ENTITY.DetalleIngreso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FARMIGEST</title>
     
        <link rel="stylesheet" type="text/css" href="recursos/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="recursos/fontawesome-free/css/fontawesome.css">
        <link rel="stylesheet" type="text/css" href="recursos/datatables/dataTables.bootstrap4.css" >
        <link rel="stylesheet" type="text/css" href="recursos/css/style.css">
        <link href="recursos/css/sb-admin.css" rel="stylesheet" type="text/css"/>
        <link href="recursos/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"/>
    </head>

 
    
    <body id="page-top">
        
        <%@include file="pagina_comun/header.jsp" %> 
        
        <div id="wrapper">
            
            <%@include file="pagina_comun/vertical.jsp" %> 
            
            <div id="content-wrapper" >
                <!--LISTA--> 
                <div class="container-fluid"  ${visibleLista} >
                    <ol class="breadcrumb bg-info text-white">
                      <li class="breadcrumb-item text-white">Ingresos</li>
                    </ol>  
                    
                    
                            <div class="form-row">
                                <div class="form-group col-md-1"></div>
                                <div class="form-group col-md-11"> 
                                    <form action="IngresoController?action=new" method="post"> 
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

                                              <th >Tipo Doc.</th>
                                              <th >Documento</th>
                                              <th >Ruc Proveedor</th>
                                              <th >F. Ingreso</th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                                <c:forEach var="dc" items="${ingresos}">
                                                      <tr>
                                                          <td ><c:out value="${dc.tipoDocumento}"/></td>
                                                          <td ><c:out value="${dc.nroDocumento}"/></td>
                                                          <td ><c:out value="${dc.rucproveedor}"/></td>
                                                          <td ><c:out value="${dc.fechaIngreso}"/></td>
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
                
                <!--CONTENIDO-->
                <div class="container-fluid" ${visibleFormulario}>
                     
                        <div class="form-row" ${estadoMensaje}>
                            <div class="form-group col-md-1"></div>
                            <div class="form-group col-md-11"> 
                                    <p class="text-success">${mensaje}</p>
                                    <% session.setAttribute("mensaje", ""); %>
                                    <% session.setAttribute("estadoMensaje", "hidden"); %>
                            </div>
                        </div>
                    
                     <!--TITLE-->
                    <ol class="breadcrumb bg-info text-white">
                      <li class="breadcrumb-item text-white">Nuevo Ingreso</li>
                    </ol>                    
                    
                     <form  method="post" action="" role="form" id="formGeneral"> 
 
                        <div class="card-body">
                                <!--N° Documento -->
                                <div class="form-row">
                                  <div class="form-group col-md-1"></div>
                                  <div class="form-group col-md-3">
                                    <label for="inputEmail4">Tipo Documento</label>
                                    <input type="text" class="form-control" name="txtTipo" value="Guia Remision"  id="txtTipo" placeholder="Tipo de Documento" required readonly>
                                  </div>
                                  <div class="form-group col-md-3">
                                    <label for="inputEmail4" >N° Documento</label>
                                    <input type="text" class="form-control" name="txtDocumento"  value="${txtDocumento}"  id="txtDocumento" placeholder="N° Documento"required>
                                  </div>
                                  <div class="form-group col-md-3">
                                    <label for="inputEmail4">Fecha Ingreso</label>
                                    <input type="date" name="txtFeha" value="${txtFeha}"  class="form-control" id="txtFeha"  required>
                                  </div> 
                                </div>

                                <!-- TIPO DOCUMENTO / FECHA / RUC PROVEEDOR-->
                                <div class="form-row">
                                    <div class="form-group col-md-1"></div>

                                  <div class="form-group col-md-3">
                                    <label for="inputEmail4">Ruc Proveedor</label>
                                    <input type="number" 
                                           name="txtRuc" min="11" maxlength ="11" value="${txtRuc}"  class="form-control"   id="txtRuc" placeholder="Ruc Proveedor" required>
                                  </div>
                                  <div class="form-group col-md-3">
                                    <label for="inputEmail4">Sede</label>
                                    <select class="form-control"  name="txtSede" id="txtSede"   required>
                                        <option value="" disabled selected >Seleccione</option>
                                        <c:forEach var="obj" items="${sedes}">   
                                            <option value="${obj.idSede}" ${obj.idSede == txtSede ? 'selected' : ''}
                                                    >${obj}</option>
                                        </c:forEach>
                                    </select>
                                  </div>


                                </div>

                                <!--DIVISOR LINEA--> 
                                <div class="form-row">
                                    <div class="form-group col-md-1"></div>
                                    <div class="form-group col-md-9">
                                        <div class="dropdown-divider"></div>
                                    </div>
                                </div>

                                <!-- BOTON AGREGAR ITEM --><!--   -->
                                <div class="form-row">
                                    <div class="form-group col-md-1"></div>
                                    <div class="form-group col-md-2">
                                            <button type="submit"  id="inputAddress" class="btn btn-primary btn-block " 
                                                    onclick="changeAction('IngresoController?action=habilitarBusqueda');">
                                                <i class="fa fa-plus"></i>
                                                Producto</button>
                                    </div>
                                </div> 
                              
                                 
                                
                                
                                <!--HABILITA OPCION BUSCAR-->               
                                    <div class="form-row" ${estadoBusqueda}>
                                        <div class="form-group col-md-1"></div>
                                        <div class="form-group col-md-10">
                                            <div class="input-group">    
                                            <input type="text" name="txtFiltro" id="txtFiltro" class="form-control" 
                                                   placeholder="Buscar Medicamento" value="" required  >
                                            <button  type="submit" class="btn btn-primary block"
                                                     onclick="changeAction('IngresoController?action=buscar');"
                                                     ><i class="fa fa-search"></i> </button>
                                            </div>
                                        </div>
                                    </div>
                                 
                                <!--LISTA DE BUSQUEDA-->
                                <div class="form-row" ${estadoBusqueda}>
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

                                                  <th style="width:90px"> </th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                              <c:forEach var="producto" items="${listaBusqueda}">                     
                                                    <tr>
                                                        <td hidden><c:out value="${producto.idProducto}"/></td>
                                                        <td ><c:out value="${producto.nombre}"/></td>
                                                        <td ><c:out value="${producto.descripcion}"/></td>
                                                        <td ><c:out value="${producto.marca}"/></td>
                                                        <td>
                                                            <button type="button" class="btn btn-info btn-sm" 
                                                             onclick="agregar_producto(${producto.idProducto},'${producto.nombre}','${producto.descripcion}','${producto.marca}');">
                                                             <i class="fa fa-plus"></i>
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
                                <!--LISTA DE INGRESOS-->
                                <div class="form-row">
                                      <div class="form-group col-md-1"></div>
                                      <div class="form-group col-md-10">
                                        <div class="table-striped">

                                            <table class="table table-bordered small" id="dataTable" width="100%" cellspacing="0">
                                              <thead class="thead-dark ">
                                                <tr>
                                                  <th hidden>Codigo</th>
                                                  <th>Nombre</th>
                                                  <th>Descripción</th>
                                                  <th>Marca</th>

                                                  <th style="width:90px"> </th>
                                                </tr>
                                              </thead>
                                              <tbody>
                                              <c:forEach var="DetalleIngreso" items="${listaIngreso}">                     
                                                    <tr>
                                                        <td ><c:out value="${DetalleIngreso.producto.nombre}"/></td>
                                                        <td ><c:out value="${DetalleIngreso.producto.descripcion}"/></td>
                                                        <td ><c:out value="${DetalleIngreso.producto.marca}"/></td>
                                                        <td><c:out value="${DetalleIngreso.cantidad}"/>
                                                        </td>
                                                    </tr>
                                              </c:forEach>
                                              </tbody>
                                            </table>
                                          </div>
                                      </div>

                                      <div class="form-group col-md-1"></div>
                                </div>
                                
                                <!-- BOTON GRABAR -->
                                    <div class="form-row">
                                    <div class="form-group col-md-1"></div>
                                    <div class="form-group col-md-2">
                                            <button type="submit"  id="inputAddress" class="btn btn-primary btn-block "
                                                    onclick="changeAction('IngresoController?action=registrar');"
                                                    >
                                            Registrar</button>
                                    </div>
                                </div>
                              
   
                        </div>
 
                       
                    </form>
 
                </div>
                                          
            </div>
            
        </div>
                                 
    <!--VENTANAS MODALES-->
<div class="modal" id="myModalAgregar" >
  <div class="modal-dialog">
    <div class="modal-content">

      <!-- Modal Header -->
      <div class="modal-header ">
        <h4 class="modal-title col-sm-3 ">Producto</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>

      <!-- Modal body -->
      <div class="modal-body">      
          <form class="form-horizontal" action="IngresoController?action=agregarProducto" method="post">
          
            <div class="form-group" hidden>
            <label class="control-label col-sm-3" for="email">Codigo:</label>
            <div class="col-sm-12">
                <input type="text" name="txt_xcodigo" class="form-control" id="xcodigo" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-3" for="email">Nombre:</label>
            <div class="col-sm-12">
              <input type="text" name="txt_xnombre" class="form-control" id="xnombre" readonly>
            </div>
          </div>

          <div class="form-group">
            <label class="control-label col-sm-3" for="email">Descripción:</label>
            <div class="col-sm-12">
              <input type="text" name="txt_xdescripcion" class="form-control" id="xdescripcion" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-3" for="email">Marca:</label>
            <div class="col-sm-12">
              <input type="text" name="txt_xmarca" class="form-control" id="xmarca" readonly>
            </div>
          </div>
            
          <div class="form-group">
            <label class="control-label col-sm-3" for="email">Cantidad</label>
            <div class="col-sm-12">
                <input type="number" name="txt_xcantidad" class="form-control" required>
            </div>
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
    
    <!--SCRIPTS-->
    <script>
 
 
        
        function agregar_producto(codigo, nombre, descripcion, marca){
          // get the current info in html table 
          var xcodigo = codigo;
          var xnombre = nombre;
          var xdescripcion = descripcion;
          var xmarca = marca;
          
          document.getElementById("xcodigo").value=xcodigo;
          document.getElementById("xnombre").value=xnombre;
          document.getElementById("xdescripcion").value=xdescripcion;
          document.getElementById("xmarca").value=xmarca;

            $('#myModalAgregar').modal({
            keyboard: true,
            backdrop: "static"
          });
        };
        
      function changeAction(newAction){
         
            var frm=document.getElementById("formGeneral");
            
            frm.action=newAction;
            doc=document.getElementById("txtDocumento").value;
            tdoc=document.getElementById("txtTipo").value;
            fecha=document.getElementById("txtFeha").value;
            ruc=document.getElementById("txtRuc").value;
            sede=document.getElementById("txtSede").value;
            
            
            if(doc !== "" && tdoc !=="" && fecha !== "" && ruc !== "" && sede !== ""){
                frm.submit();
            }
            
        
      };
 
    </script>

    
    
    </body>
 
     <script src="recursos/jquery/jquery.min.js" type="text/javascript"></script>
     <script src="recursos/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script> 
     <script src="recursos/js/sb-admin.js" type="text/javascript"></script>
    
</html>
