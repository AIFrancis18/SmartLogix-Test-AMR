package com.smartlogix.serviciopedido;

import com.smartlogix.serviciopedido.client.EnvioClient;
import com.smartlogix.serviciopedido.client.InventarioClient;
import com.smartlogix.serviciopedido.model.Pedido;
import com.smartlogix.serviciopedido.repository.PedidoRepository;
import com.smartlogix.serviciopedido.service.PedidoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PedidoServiceImplTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private EnvioClient envioClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private PedidoServiceImpl service;

    private Pedido pedido;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        pedido = new Pedido();

        pedido.setId(1L);
        pedido.setCliente("Alejandro");
        pedido.setProducto("Teclado");
        pedido.setCantidad(2);
        pedido.setDireccion("Santiago");
        pedido.setEstado("PENDIENTE");
    }

    @Test
    void listarPedidosTest() {

      
        List<Pedido> lista =
                Arrays.asList(pedido);

        when(repository.findAll())
                .thenReturn(lista);

       
        List<Pedido> resultado =
                service.listar();

        
        assertEquals(1, resultado.size());

        assertEquals(
                "Alejandro",
                resultado.get(0).getCliente()
        );

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void guardarPedidoTest() {

        
        when(inventarioClient.hayStock(
                "Teclado",
                2
        )).thenReturn(true);

        doNothing().when(inventarioClient)
                .descontarStock(
                        "Teclado",
                        2
                );

        when(repository.save(any(Pedido.class)))
                .thenReturn(pedido);

        when(envioClient.crearEnvio(any()))
                .thenReturn(new Object());

        
        Pedido resultado =
                service.guardar(pedido);

        
        assertNotNull(resultado);

        assertEquals(
                "Teclado",
                resultado.getProducto()
        );

        verify(inventarioClient, times(1))
                .hayStock("Teclado", 2);

        verify(inventarioClient, times(1))
                .descontarStock("Teclado", 2);

        verify(repository, times(1))
                .save(any(Pedido.class));

        verify(envioClient, times(1))
                .crearEnvio(any());
    }
}