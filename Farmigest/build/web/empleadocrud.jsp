<%@page import="Layer.ENTITY.Cliente"%>
<%@page import="Layer.ENTITY.Usuario"%>
<%@page import="Layer.ENTITY.Empleado"%>

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
                        Personal
                    </div>
                       
                    <div class="card-body">
                         
                            <div class="form-row">
                                <div class="form-group col-md-1"></div>
                                <div class="form-group col-md-11"> 
                                    <form action="EmpleadoController?action=new" method="post"> 
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
                                              <th >Apellidos</th>
                                              <th >DNI</th>
                                              <th >Sexo</th>
                                              <th > Area</th>
                                              <th style="width:120px"></th>
                                            </tr>
                                          </thead>
                                          <tbody>
                                                <c:forEach var="dc" items="${listaEmpleado}">
                                                      <tr>
                                                          <td ><c:out value="${dc.nombre}"/></td>
                                                          <td ><c:out value="${dc.apellido}"/></td>
                                                          <td ><c:out value="${dc.dni}"/></td>
                                                          <td ><c:out value="${dc.sexo}"/></td>
                                                          <td ><c:out value="${dc.area}"/></td>
                                                          <td >
                                                              <a href="EmpleadoController?action=edit&idEmpleado=${dc.idEmpleado}" >Editar</a> |
                                                               <a href="EmpleadoController?action=delete&idEmpleado=${dc.idEmpleado}" >Eliminar</a>

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
                        Personal
                    </div>
                     <form method="post" action="EmpleadoController?action=${op == 'editar' ? 'update' : 'create'}" role="form" id="form_login"  >       
                        <div class="card-body">
                            <div class="form-row" hidden>
                                <input type="text" class="form-control" name="txtidUsuario"   value="${opUsuario.idUsuario}" hidden>
                                <input type="text" class="form-control" name="txtidEmpleado"   value="${opUsuario.idEmpleado}" hidden >
                            </div>
                                <!--DNI -->
                                <div class="form-row">

                                  <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4" >N° DNI</label>
                                    <input type="text" class="form-control" name="txtDni"   ${op == 'editar' ? 'readonly' : ''}  value="${opUsuario.dni}" id="inputAddress" placeholder="N° Documento" required>
                                  </div>

                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Area</label>
                                    <select class="form-control"  name="txtArea" id="Especialidad" required>
                                          <option value="" disabled selected >Seleccione</option>
                                          <option value="Sistemas"  ${opUsuario.area == 'Sistemas' ? 'selected' : ''}>Sistemas</option>
                                          <option value="Logística"  ${opUsuario.area == 'Logística' ? 'selected' : ''}>Logística</option>
                                          <option value="Finanza"  ${opUsuario.area == 'Finanza' ? 'selected' : ''}>Finanza</option>
                                          <option value="Venta"  ${opUsuario.area == 'Venta' ? 'selected' : ''}>Venta</option>
                                          <option value="Almacen"  ${opUsuario.area == 'Almacen' ? 'selected' : ''}>Almacen</option>
                                    </select>
                                  </div>

                                </div>

                                <!--NOMBRE / APELLIDO-->
                                <div class="form-row">
                                    <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Nombre</label>
                                    <input type="text" class="form-control" name="txtNombre" value="${opUsuario.nombre}" id="inputAddress" placeholder="Nombre" required>
                                  </div>


                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Apellidos</label>
                                    <input type="text" name="txtApellido" value="${opUsuario.apellido}"  class="form-control" id="inputAddress" placeholder="Apellidos" required>
                                  </div> 

                                </div>

                                 <!--SEXO  / CELULAR-->
                                <div class="form-row">
                                  <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Sexo</label>
                                    <select class="form-control"  name="txtSexo" id="Especialidad" required>
                                        <option value="" disabled selected >Seleccione</option>
                                        <option value="Masculino"  ${opUsuario.sexo == 'Masculino' ? 'selected' : ''}>Masculino</option>
                                        <option value="Femenino"  ${opUsuario.sexo == 'Femenino' ? 'selected' : ''}>Femenino</option>
                                    </select>

                                  </div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Celular</label>
                                    <input type="text" class="form-control" name="txtCelular" value="${opUsuario.celular}" id="inputAddress" placeholder="Celular" required>
                                  </div>
                                </div> 

                                <!-- CORREO / CLAVE-->
                                <div class="form-row">
                                    <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">E-Mail</label>
                                    <input type="email" class="form-control" name="txtCorreo" value="${opUsuario.usuario.logUsuario}" id="inputAddress" placeholder="E-Mail" required>
                                  </div>


                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Contraseña</label>
                                    <input type="password" name="txtClave" value="${opUsuario.usuario.pwdUsuario}"  class="form-control" id="inputAddress" placeholder="Contraseña" required>
                                  </div> 

                                </div>

                                 <!--NIVEL-->
                                <div class="form-row">
                                  <div class="form-group col-md-2"></div>
                                  <div class="form-group col-md-4">
                                    <label for="inputEmail4">Nivel</label>
                                    <select class="form-control"  name="txtNivel" id="Especialidad" required>
                                        <option value="" disabled selected >Seleccione</option>
                                        <option value="3"  ${opUsuario.usuario.idNivelUsuario == '3' ? 'selected' : ''}>Administrador</option>
                                        <option value="1"  ${opUsuario.usuario.idNivelUsuario == '1' ? 'selected' : ''}>Personal</option>
                                    </select>

                                  </div>
                                </div> 
                                  
                                <!--OPCION DE REGISTRO-->
                                <div class="dropdown-divider"></div> <!--DIVISOR LINEA--> 
                                <div class="form-row">

                                 <div class="form-group col-md-2"></div>
                                 <div class="form-group col-md-6">
                                     <a href="EmpleadoController?action=listar" class="small" >Volver</a>
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
    


</html>>