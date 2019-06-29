package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import Layer.ENTITY.Cliente;
import Layer.ENTITY.Usuario;
import Layer.ENTITY.Cliente;
import Layer.ENTITY.Usuario;

public final class compra_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(2);
    _jspx_dependants.add("/pagina_comun/header.jsp");
    _jspx_dependants.add("/pagina_comun/vertical.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>FARMIGEST</title>\n");
      out.write("     \n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"recursos/bootstrap/css/bootstrap.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"recursos/fontawesome-free/css/fontawesome.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"recursos/datatables/dataTables.bootstrap4.css\" >\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"recursos/css/style.css\">\n");
      out.write("        <link href=\"recursos/css/sb-admin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <link href=\"recursos/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("    \n");
      out.write("    <body id=\"page-top\">\n");
      out.write("        \n");
      out.write("        ");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/bootstrap/css/bootstrap.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/fontawesome-free/css/fontawesome.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/datatables/dataTables.bootstrap4.css\" >\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/css/style.css\">\n");
      out.write("        <link href=\"../recursos/css/sb-admin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <link href=\"../recursos/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("    \n");
      out.write("   <!-- MENU HORIZONTAL -->\n");
      out.write("    <nav class=\"navbar navbar-expand navbar-dark bg-dark static-top\">\n");
      out.write(" \n");
      out.write("      <button class=\"btn btn-link btn-sm text-white order-1 order-sm-0\" id=\"sidebarToggle\" href=\"#\">\n");
      out.write("        <i class=\"fas fa-bars\"></i>\n");
      out.write("      </button>\n");
      out.write("\n");
      out.write("      <!-- MENU HORIZONTAL -->\n");
      out.write("      <form class=\"d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0\">\n");
      out.write("      </form>\n");
      out.write("\n");
      out.write("      <!-- Navbar -->\n");
      out.write("      <ul class=\"navbar-nav ml-auto ml-md-0\">\n");
      out.write("        <li class=\"nav-item dropdown no-arrow\">\n");
      out.write("          <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"userDropdown\" role=\"button\" \n");
      out.write("             data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n");
      out.write("              Bienvenido(a),  ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${cliente.nombre}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write(" \n");
      out.write("            <i class=\"fas fa-user-circle fa-fw\"></i>\n");
      out.write("          </a>\n");
      out.write("          <div class=\"dropdown-menu dropdown-menu-right\" aria-labelledby=\"userDropdown\">\n");
      out.write("            <a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" >\n");
      out.write("               Mi Perfil\n");
      out.write("            </a>\n");
      out.write("              <div class=\"dropdown-divider\"></div>\n");
      out.write("            <a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" onclick=\"cerrarSesion()\">\n");
      out.write("                Cerrar Sesión\n");
      out.write("            </a>\n");
      out.write("          </div>\n");
      out.write("        </li>\n");
      out.write("      </ul>\n");
      out.write("\n");
      out.write("    </nav>\n");
      out.write("\n");
      out.write("    <script type=\"text/javascript\">\n");
      out.write("        function cerrarSesion(){\n");
      out.write("                window.location = \"../logoutSvt?operacion=usuario\";\n");
      out.write("           }\n");
      out.write("    </script>\n");
      out.write("    \n");
      out.write("     <script src=\"../recursos/jquery/jquery.min.js\" type=\"text/javascript\"></script>\n");
      out.write("     <script src=\"../recursos/bootstrap/js/bootstrap.bundle.min.js\" type=\"text/javascript\"></script> \n");
      out.write(" \n");
      out.write("     \n");
      out.write("</html>\n");
      out.write(" \n");
      out.write("        \n");
      out.write("        <div id=\"wrapper\">\n");
      out.write("            \n");
      out.write("            ");
      out.write(" \n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/bootstrap/css/bootstrap.min.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/fontawesome-free/css/fontawesome.css\">\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/datatables/dataTables.bootstrap4.css\" >\n");
      out.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"../recursos/css/style.css\">\n");
      out.write("        <link href=\"../recursos/css/sb-admin.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        <link href=\"../recursos/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
      out.write("        \n");
      out.write("    <body >\n");
      out.write(" \n");
      out.write("      <!-- MENU VERTICAL -->\n");
      out.write("      <ul class=\"sidebar navbar-nav\">\n");
      out.write("          \n");
      out.write("        <li class=\"nav-item\">\n");
      out.write("            <a class=\"nav-link\" href=\"../transacciones/miInformacion.jsp\">\n");
      out.write("            <i class=\"fas fa-user\"></i>\n");
      out.write("            <span>Mi Perfil</span></a>\n");
      out.write("        </li>\n");
      out.write("          \n");
      out.write("        <li class=\"nav-item\">\n");
      out.write("            <a class=\"nav-link\" href=\"../paginas/panel.jsp\">\n");
      out.write("            <i class=\"fa fa-shopping-cart\"></i>\n");
      out.write("            <span>Comprar</span>\n");
      out.write("          </a>\n");
      out.write("        </li>\n");
      out.write(" \n");
      out.write("        \n");
      out.write("        <li class=\"nav-item\">\n");
      out.write("            <a class=\"nav-link\" href=\"../transacciones/reservaCita.jsp\">\n");
      out.write("            <i class=\"fa fa-th-list\"></i>\n");
      out.write("            <span>Compras</span></a>\n");
      out.write("        </li>\n");
      out.write("        \n");
      out.write(" \n");
      out.write("        \n");
      out.write("        \n");
      out.write(" \n");
      out.write("        \n");
      out.write("      </ul>\n");
      out.write(" \n");
      out.write("    </body>  \n");
      out.write("    \n");
      out.write("     <script src=\"../recursos/jquery/jquery.min.js\" type=\"text/javascript\"></script>\n");
      out.write("     <script src=\"../recursos/bootstrap/js/bootstrap.bundle.min.js\" type=\"text/javascript\"></script> \n");
      out.write(" \n");
      out.write("    \n");
      out.write("</html>\n");
      out.write(" \n");
      out.write("            \n");
      out.write(" \n");
      out.write("     \n");
      out.write("            <div id=\"content-wrapper\">\n");
      out.write("                \n");
      out.write("                <!--CONTENIDO-->\n");
      out.write("                <div class=\"container-fluid\">\n");
      out.write("                     \n");
      out.write("                     <!--TITLE-->\n");
      out.write("                    <ol class=\"breadcrumb bg-warning text-white\">\n");
      out.write("                      <li class=\"breadcrumb-item text-secondary\">Próximos Eventos</li>\n");
      out.write("                    </ol>                    \n");
      out.write("                    \n");
      out.write(" \n");
      out.write("            </div>\n");
      out.write("            \n");
      out.write("        </div>\n");
      out.write("    \n");
      out.write("        \n");
      out.write(" \n");
      out.write("                                 \n");
      out.write(" \n");
      out.write("    </body>\n");
      out.write(" \n");
      out.write("     <script src=\"../recursos/jquery/jquery.min.js\" type=\"text/javascript\"></script>\n");
      out.write("     <script src=\"../recursos/bootstrap/js/bootstrap.bundle.min.js\" type=\"text/javascript\"></script> \n");
      out.write(" \n");
      out.write("    \n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
