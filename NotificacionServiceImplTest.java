package com.smartlogix.servicionotificacion;

import com.smartlogix.servicionotificacion.model.Notificacion;
import com.smartlogix.servicionotificacion.repository.NotificacionRepository;
import com.smartlogix.servicionotificacion.service.NotificacionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificacionServiceImplTest {

    @Mock
    private NotificacionRepository repository;

    @InjectMocks
    private NotificacionServiceImpl service;

    private Notificacion notificacion;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        notificacion = new Notificacion();

        notificacion.setMensaje(
                "Pedido creado correctamente"
        );

        notificacion.setTipo("PEDIDO");

        notificacion.setUsuario("Alejandro");

        notificacion.setReferenciaId(1L);
    }

    @Test
    void listarNotificacionesTest() {

        // Arrange
        List<Notificacion> lista =
                Arrays.asList(notificacion);

        when(repository.findAll())
                .thenReturn(lista);

        // Act
        List<Notificacion> resultado =
                service.listar();

        // Assert
        assertEquals(
                1,
                resultado.size()
        );

        assertEquals(
                "Pedido creado correctamente",
                resultado.get(0).getMensaje()
        );

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void crearNotificacionTest() {

        // Arrange
        when(repository.save(any(Notificacion.class)))
                .thenReturn(notificacion);

        // Act
        Notificacion resultado =
                service.crear(notificacion);

        // Assert
        assertNotNull(resultado);

        assertEquals(
                "PEDIDO",
                resultado.getTipo()
        );

        assertEquals(
                "Alejandro",
                resultado.getUsuario()
        );

        verify(repository, times(1))
                .save(any(Notificacion.class));
    }
}
