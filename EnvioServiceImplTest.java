package com.smartlogix.servicioenvio;

import com.smartlogix.servicioenvio.model.Envio;
import com.smartlogix.servicioenvio.repository.EnvioRepository;
import com.smartlogix.servicioenvio.service.EnvioServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnvioServiceImplTest {

    @Mock
    private EnvioRepository repository;

    @InjectMocks
    private EnvioServiceImpl service;

    private Envio envio;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        envio = new Envio();

        envio.setPedidoId(1L);
        envio.setDireccion("Santiago");
        envio.setEstado("PENDIENTE");
    }

    @Test
    void listarEnviosTest() {

        
        List<Envio> lista =
                Arrays.asList(envio);

        when(repository.findAll())
                .thenReturn(lista);

        
        List<Envio> resultado =
                service.listar();

        
        assertEquals(1, resultado.size());

        assertEquals(
                "Santiago",
                resultado.get(0).getDireccion()
        );

        verify(repository, times(1))
                .findAll();
    }

    @Test
    void crearEnvioTest() {

        
        when(repository.save(any(Envio.class)))
                .thenReturn(envio);

        
        Envio resultado =
                service.crear(envio);

        
        assertNotNull(resultado);

        assertEquals(
                "PENDIENTE",
                resultado.getEstado()
        );

        verify(repository, times(1))
                .save(any(Envio.class));
    }
}