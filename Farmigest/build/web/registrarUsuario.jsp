
<%@page import="Layer.ENTITY.Cliente"%>
<%@page import="Layer.ENTITY.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FARMIGEST - Crear Cuenta</title>
        
        <link rel="stylesheet" type="text/css" href="recursos/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="recursos/fontawesome-free/css/fontawesome.css">
        <link rel="stylesheet" type="text/css" href="recursos/css/RegistrarStyle.css">
    </head>
    
              <div class="sidenav">
                 <div class="login-main-text">
                    <h2>Aplicación<br> FARMIGEST</h2>
                    <p>Login or register from here to access.</p>
                 </div>
              </div>
    
              <div class="main">
                 <div class="col-md-6 col-sm-12">
                    <div class="login-form">
                        <div><h5><b>CREAR CUENTA</b> <span class="badge badge-secondary">New</span></h5></div>
                        <hr style="color: #0056b2;" />
                        
                    <div class="form-group" style="font-size:14px">
                      
                        <p style="color:#FF0000">${mensaje} </p>
                        <% session.setAttribute("mensaje", ""); %>
                    </div>
                        
                       <form method="post"  action="UsuarioController?action=registrar" role="form" >

                                <div class="form-group">            
                                     <label>    Nombre</label>
                                     <input autocomplete="off" name="txtNombre" placeholder="Nombre" type="text" value="${cli.nombre}" class="form-control" required >
                                 </div>
                                <div class="form-group">            
                                    <label>Apellido Paterno</label>
                                    <input autocomplete="off" name="txtPaterno" placeholder="Apellido Paterno" type="text" value="${cli.apellidoPaterno}" class="form-control" required>
                                </div>
                                 <div class="form-group">            
                                     <label>Apellido Materno</label>
                                     <input autocomplete="off" name="txtMaterno" placeholder="Apellido Materno" type="text" value="${cli.apellidoMaterno}" class="form-control" required>
                                 </div>
                                 <div class="form-group">            
                                     <label>DNI</label>
                                     <input autocomplete="off" minlength=10 name="txtDni" placeholder="N° Documento" type="number"  value="${cli.dni}" class="form-control" required>
                                 </div>
                                 <div class="form-group">            
                                     <label>Sexo</label>         
                                     <select name="txtSexo"  class="form-control" required>
                                       <option value="" selected disabled></option>
                                       <option value="Masculino" ${cli.sexo == 'Masculino' ? 'selected' : ''} >Masculino</option>
                                       <option value="Femenino" ${cli.sexo == 'Femenino' ? 'selected' : ''}>Femenino</option>
                                     </select>
                                 </div>  
                                 <div class="form-group">            
                                     <label>N° Celular</label>
                                     <input autocomplete="off" name="txtCelular" placeholder="N° Celular" type="number" value="${cli.celular}" class="form-control" required>
                                 </div>
                                 <div class="form-group">            
                                     <label>Fecha Nacimiento</label>
                                     <input autocomplete="off" name="txtFechaNacimiento" value="${cli.fechaNacimiento}" type="date" class="form-control" required >
                                 </div>
                                 <div class="form-group">            
                                     <label>Correo electrónico</label>
                                     <input autocomplete="off" name="txtCorreo" placeholder="Correo Electronico" type="email" value="${cli.usuario.logUsuario}" class="form-control" required>
                                 </div>
                                 <div class="form-group">            
                                     <label>Contraseña</label>
                                     <input autocomplete="off" name="txtClave" placeholder="Contraseña" type="password" value="" class="form-control" required>
                                 </div> 
                           
                          <button type="submit" class="btn btn-black">Registrar</button> 

                            <div class="form-group" style="font-size:16px">
                                ¿ya tienes una cuenta?
                                <a href="index.jsp" >
                                    Inicie aquí.
                                </a>
                            </div>

                          
                       </form>
                        
                    </div>
                 </div>
              </div>
                  
    <body>
  
      
        
    </body>
</html>
