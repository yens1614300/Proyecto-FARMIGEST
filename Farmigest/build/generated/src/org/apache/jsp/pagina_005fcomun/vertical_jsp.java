package org.apache.jsp.pagina_005fcomun;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import Layer.ENTITY.Cliente;
import Layer.ENTITY.Usuario;

public final class vertical_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

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
      out.write("            <a class=\"nav-link\" href=\"../paginas/panel.jsp\">\n");
      out.write("            <i class=\"fa fa-stethoscope\"></i>\n");
      out.write("            <span>Mis Citas</span>\n");
      out.write("          </a>\n");
      out.write("        </li>\n");
      out.write(" \n");
      out.write("        \n");
      out.write("        <li class=\"nav-item\">\n");
      out.write("            <a class=\"nav-link\" href=\"../transacciones/reservaCita.jsp\">\n");
      out.write("            <i class=\"far fa-calendar-alt\"></i>\n");
      out.write("            <span>Reservar</span></a>\n");
      out.write("        </li>\n");
      out.write("        \n");
      out.write("        <li class=\"nav-item\">\n");
      out.write("            <a class=\"nav-link\" href=\"../transacciones/miFamiliar.jsp\">\n");
      out.write("            <i class=\"fas fa-users\"></i>\n");
      out.write("            <span>Mis Familiares</span></a>\n");
      out.write("        </li>\n");
      out.write("        \n");
      out.write("        \n");
      out.write("        <li class=\"nav-item\">\n");
      out.write("            <a class=\"nav-link\" href=\"../transacciones/miInformacion.jsp\">\n");
      out.write("            <i class=\"fas fa-user\"></i>\n");
      out.write("            <span>Mi Perfil</span></a>\n");
      out.write("        </li>\n");
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
