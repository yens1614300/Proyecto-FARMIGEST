<%@page import="Layer.ENTITY.Cliente"%>
<%@page import="Layer.ENTITY.Usuario"%>
<%@page import="Layer.ENTITY.Empleado"%>
  



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript"> window.history.forward(-1); </script>
    </head>

        <link rel="stylesheet" type="text/css" href="../recursos/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../recursos/fontawesome-free/css/fontawesome.css">
        <link rel="stylesheet" type="text/css" href="../recursos/datatables/dataTables.bootstrap4.css" >
        <link rel="stylesheet" type="text/css" href="../recursos/css/style.css">
        <link href="../recursos/css/sb-admin.css" rel="stylesheet" type="text/css"/>
        <link href="../recursos/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css"/>
    
        <% //si el usuario no esta activo
            Usuario xusu =  (Usuario)session.getAttribute("usuario");
            if(xusu==null){response.sendRedirect("index.jsp");}
        %>
        
   <!-- MENU HORIZONTAL -->
    <nav class="navbar navbar-expand navbar-dark bg-dark static-top">
 
      <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
        <i class="fas fa-bars"></i>
      </button>

      <!-- MENU HORIZONTAL -->
      <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
      </form>

      <!-- Navbar -->
      <ul class="navbar-nav ml-auto ml-md-0">
        <li class="nav-item dropdown no-arrow">
          <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" 
             data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Bienvenido(a),  ${usuario.cliente.nombre}  ${usuario.empleado.nombre} 
            <i class="fas fa-user-circle fa-fw"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
              <!-- 
              <a class="dropdown-item" href="miperfil.jsp"   >
               Mi Perfil
              </a>     
                <div class="dropdown-divider"></div>
              -->

              
              
            
                <a class="dropdown-item" href="UsuarioController?action=cerrarsesion">
                Cerrar Sesión
            </a>
          </div>
        </li>
      </ul>

    </nav>

    <script type="text/javascript">
        function cerrarSesion(){
                //window.location = "../logoutSvt?operacion=usuario";
           }
    </script>
    
     <script src="../recursos/jquery/jquery.min.js" type="text/javascript"></script>
     <script src="../recursos/bootstrap/js/bootstrap.bundle.min.js" type="text/javascript"></script> 
 
     
</html>
