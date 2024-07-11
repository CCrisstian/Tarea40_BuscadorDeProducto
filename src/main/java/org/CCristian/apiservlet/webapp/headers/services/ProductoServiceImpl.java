package org.CCristian.apiservlet.webapp.headers.services;

import org.CCristian.apiservlet.webapp.headers.models.Producto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements ProductoService{
    @Override
    public List<Producto> listar() {
        return Arrays.asList(new Producto(1L, "notebook","computación",175000),
                new Producto(2L, "mesa escritorio","oficina",100000),
                new Producto(3L, "teclado mecánico","computación",40000),
                new Producto(4L, "TAREA 40","Curso Java",123456));
    }

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return Optional.empty();
        }

        ProductoService service = new ProductoServiceImpl();

        return service.listar().stream()
                .filter(p -> p.getNombre().contains(nombre))
                .findFirst();
    }
}
