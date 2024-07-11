<h1 align="center">Tarea: Refactorización del buscador de producto</h1>
<h2>Refactorización del buscador de producto</h2>
<h3>Instrucciones de tareas</h3>
<p>El objetivo de la tarea consiste en modificar el proyecto de la sección para hacer una mejora. Los requerimientos consisten en refactorizar (mejorar optimizar) el servlet BuscarProductoServlet, para que la lógica de la búsqueda se encuentre en el service y no en el servlet.</p>
<h3>Requerimientos:</h3>

- La acción doPost debe obtener el parámetro nombre del request, realizar la búsqueda mediante el service y devolver el resultado como un Optional.
- La búsqueda se debe implementar en un nuevo método de la interface ProductoService e implementarlo en la clase ProductoServiceImpl usando el api stream de java 8, este nuevo método se debe llamar buscarProducto con la siguiente firma:
  - `Optional<Producto> buscarProducto(String nombre)`;

<p>Una vez terminada y probada la tarea deberán enviar el código fuente de todos los archivos involucrados, además de las imágenes screenshot (imprimir pantalla).</p>
<p></p>NO subir el proyecto completo, sólo los archivos involucrados, que son los siguientes:</p>

- Clase servlet BuscarProductoServlet.
- interface ProductoService y clase ProductoServiceImpl.
- Imágenes screenshot del programa funcionando en en el navegador.

<h1 align="center">Resolucion del Profesor</h1>

La clase interface ProductoService:
```java
package org.aguzman.apiservlet.webapp.headers.services;

import org.aguzman.apiservlet.webapp.headers.models.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listar();
    Optional<Producto> buscarProducto(String nombre);
}
```

La clase service ProductoServiceImpl:
```java
package org.aguzman.apiservlet.webapp.headers.services;

import org.aguzman.apiservlet.webapp.headers.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService{
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "notebook", "computacion", 175000),
                new Producto(2L, "mesa escritorio", "oficina", 100000),
                new Producto(3L, "teclado mecanico", "computacion", 40000));
    }

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return listar().stream().filter(p -> {
            if (nombre == null || nombre.isBlank()) {
                return false;
            }
            return p.getNombre().contains(nombre);
        }).findFirst();
    }
}
```

La clase servlet BuscarProductoServlet:
```java
package org.aguzman.apiservlet.webapp.headers.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aguzman.apiservlet.webapp.headers.models.Producto;
import org.aguzman.apiservlet.webapp.headers.services.ProductoService;
import org.aguzman.apiservlet.webapp.headers.services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/buscar-producto")
public class BuscarProductoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ProductoService service = new ProductoServiceImpl();
        String nombre = req.getParameter("producto");

        Optional<Producto> encontrado = service.buscarProducto(nombre);
        if (encontrado.isPresent()) {
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("    <head>");
                out.println("        <meta charset=\"UTF-8\">");
                out.println("        <title>Producto encontrado</title>");
                out.println("    </head>");
                out.println("    <body>");
                out.println("        <h1>Producto encontrado!</h1>");
                out.println("        <h3>Producto encontrado " +encontrado.get().getNombre()+
                       " el precio $" +encontrado.get().getPrecio()+ "</h3>");
                out.println("    </body>");
                out.println("</html>");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Lo sentimos no se encontró el producto " + nombre);
        }
    }
}
```
