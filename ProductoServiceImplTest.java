package com.smartlogix.servicioinventario;

import com.smartlogix.servicioinventario.model.Producto;
import com.smartlogix.servicioinventario.repository.ProductoRepository;
import com.smartlogix.servicioinventario.service.ProductoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServiceImplTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoServiceImpl service;

    private Producto producto;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        producto = new Producto();

        producto.setId(1L);
        producto.setNombre("Teclado");
        producto.setCategoria("Periféricos");
        producto.setDescripcion("Teclado Gamer");
        producto.setStock(10);
        producto.setPrecio(25000);
    }

    @Test
    void listarProductosTest() {

        // Arrange
        List<Producto> lista =
                Arrays.asList(producto);

        when(repository.findAll())
                .thenReturn(lista);

        // Act
        List<Producto> resultado =
                service.listar();

        // Assert
        assertEquals(1, resultado.size());

        assertEquals(
                "Teclado",
                resultado.get(0).getNombre()
        );

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void crearProductoTest() {

        // Arrange
        when(repository.save(any(Producto.class)))
                .thenReturn(producto);

        // Act
        Producto resultado =
                service.crear(producto);

        // Assert
        assertNotNull(resultado);

        assertEquals(
                "Teclado",
                resultado.getNombre()
        );

        assertEquals(
                10,
                resultado.getStock()
        );

        verify(repository, times(1))
                .save(any(Producto.class));
    }
}