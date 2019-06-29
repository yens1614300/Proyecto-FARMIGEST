<%@page import="Layer.ENTITY.Cliente"%> 
<%@page import="Layer.ENTITY.Usuario"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>

    <body >
 
      <!-- MENU VERTICAL -->
      <ul class="sidebar navbar-nav">
          

      <!--
         <li class="nav-item">
            <a class="nav-link" href="miperfil.jsp">
            <i class="fas fa-user"></i>
            <span>Mi Perfil</span></a>
        </li>     
      -->    
        <li class="nav-item"  ${(usuario.idNivelUsuario == 2 || usuario.idNivelUsuario == 1  || usuario.idNivelUsuario ==3) ? '' : 'hidden'} >
            <a class="nav-link" href="compra.jsp">
            <i class="fa fa-shopping-cart"></i>
            <span>Comprar</span>
          </a>
        </li>
 
        <li class="nav-item"  ${(usuario.idNivelUsuario == 2 || usuario.idNivelUsuario == 1  || usuario.idNivelUsuario ==3) ? '' : 'hidden'} >
            <a class="nav-link" href="DespachoController?action=ListaCompras">
            <i class="fa fa-th-list"></i>
            <span>Compras</span></a>
        </li>
        
        <li class="nav-item"  ${( usuario.idNivelUsuario == 1  || usuario.idNivelUsuario ==3) ? '' : 'hidden'} >
            <a class="nav-link" href="IngresoController?action=listar">
            <i class="fa fa-truck"></i>
            <span>Ingreso</span></a>
        </li> 
 
        <li class="nav-item"  ${( usuario.idNivelUsuario == 1  || usuario.idNivelUsuario ==3) ? '' : 'hidden'}  >
            <a class="nav-link" href="DespachoController?action=ListaOrdenes">
            <i class="fa fa-truck"></i>
            <span>Despachar</span></a>
        </li> 
        
        <li class="nav-item"  ${( usuario.idNivelUsuario ==3) ? '' : 'hidden'} >
            <a class="nav-link" href="EmpleadoController?action=listar">
            <i class="fa fa-users"></i>
            <span>Empleado</span></a>
        </li> 
        
        <li class="nav-item" ${( usuario.idNivelUsuario ==3) ? '' : 'hidden'}>
            <a class="nav-link" href="ProductoController?action=listar">
            <i class="fa fa-plus-square"></i>
            <span>Medicamento</span></a>
        </li> 
        
        <li class="nav-item" ${( usuario.idNivelUsuario ==3) ? '' : 'hidden'}>
            <a class="nav-link" href="ReporteController?action=">
            <i class="fa fa-list-alt"></i>
            <span>Reporte</span></a>
        </li> 
        
      </ul>
 
    </body>  
 
</html>
