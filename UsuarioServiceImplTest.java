package com.smartlogix.serviciousuario;

import com.smartlogix.serviciousuario.model.Usuario;
import com.smartlogix.serviciousuario.repository.UsuarioRepository;
import com.smartlogix.serviciousuario.security.JwtUtil;
import com.smartlogix.serviciousuario.service.UsuarioServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UsuarioServiceImpl service;

    private Usuario usuario;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();

        usuario.setId(1L);
        usuario.setNombre("Alejandro");
        usuario.setCorreo("alejandro@gmail.com");
        usuario.setContrasena("12345678");
        usuario.setRol("ADMIN");
    }

    @Test
    void listarUsuarios() {

        
        List<Usuario> lista =
                Arrays.asList(usuario);

        when(repository.findAll())
                .thenReturn(lista);

       
        List<Usuario> resultado =
                service.listar();

        
        assertNotNull(resultado);

        assertEquals(
                1,
                resultado.size()
        );

        assertEquals(
                "Alejandro",
                resultado.get(0).getNombre()
        );

        
        verify(repository, times(1))
                .findAll();
    }

    @Test
    void guardarUsuarioTest() {

        
        when(passwordEncoder.encode("12345678"))
                .thenReturn("HASH");

        when(repository.save(any(Usuario.class)))
                .thenReturn(usuario);

        
        Usuario resultado =
                service.guardar(usuario);

        
        assertNotNull(resultado);

        assertEquals(
                "Alejandro",
                resultado.getNombre()
        );

        assertEquals(
                "ADMIN",
                resultado.getRol()
        );

        
        verify(passwordEncoder, times(1))
                .encode("12345678");

        verify(repository, times(1))
                .save(any(Usuario.class));
    }
}