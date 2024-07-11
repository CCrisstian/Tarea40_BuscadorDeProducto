package org.CCristian.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.headers.models.Producto;
import org.CCristian.apiservlet.webapp.headers.services.ProductoService;
import org.CCristian.apiservlet.webapp.headers.services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/buscar-productos")
public class BuscarProductosServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String nombre = req.getParameter("producto");

        ProductoService service = new ProductoServiceImpl();

        Optional<Producto> encontrado = service.buscarProducto(nombre);

        if (encontrado.isPresent()){
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Tarea 40: Refactorización del buscador de producto</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Tarea 40: Refactorización del buscador de producto</h1>");
                out.println("        <h3>Producto Encontrado "+encontrado.get().getNombre()+" el precio $"+encontrado.get().getPrecio()+"</h3>");
                out.println("    </body>");
                out.println("</html>");
            }
        }else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Lo sentimos no se encontró el producto "+nombre);
        }
    }
}
