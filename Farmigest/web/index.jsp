
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FARMIGEST</title>
        <script type="text/javascript"> window.history.forward(-1); </script>
        <link rel="stylesheet" type="text/css" href="recursos/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="recursos/fontawesome-free/css/fontawesome.css">
        <link rel="stylesheet" type="text/css" href="recursos/css/IndexStyle.css">

    </head>
    <body>
        
        
              <div class="sidenav">
                 <div class="login-main-text">
                    <h2>Aplicación<br> FARMIGEST</h2>
                    <p>Login or register from here to access.</p>
                 </div>
              </div>
              <div class="main">
                 <div class="col-md-6 col-sm-12">
                    <div class="login-form">
                       <form method="post"  action="UsuarioController?action=login" role="form" >
                          <div class="form-group">
                             <label>Correo electrónico</label>
                             <input type="text" autocomplete="off" name="txtUsuario" class="form-control" placeholder="Correo electrónico">
                          </div>
                          <div class="form-group">
                             <label>Password</label>
                             <input type="password" autocomplete="off" name="txtClave" class="form-control" placeholder="Password">
                          </div>
                          <button type="submit" class="btn btn-black">Login</button>
                           
                          <a href="registrarUsuario.jsp" class="btn btn-secondary" >Registrar</a>
                       </form>
                        
                    <div class="form-group" style="font-size:14px">
                      
                        <p style="color:#808080">${mensaje} </p>
                        <% session.setAttribute("mensaje", ""); %>
                    </div>
                        
                    </div>
                 </div>
              </div>

 
        
        
    </body>
</html>
