package com.mar.back.modules.usuario.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.mar.back.modules.usuario.exceptions.UserAlreadyCreated;
import com.mar.back.modules.usuario.models.Usuario;
import com.mar.back.modules.usuario.repositories.UsuarioRepository;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Autowired
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should be return all usuarios successfully")
    void testFindAllCase1() {
        Usuario usuario1 = new Usuario("usuario1@gmail.com");
        Usuario usuario2 = new Usuario("usuario2@gmail.com");
        List<Usuario> usuarios = new ArrayList<Usuario>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        
        when(usuarioService.findAll()).thenReturn(usuarios);
        
        List<Usuario> result = usuarioService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("usuario1@gmail.com", result.get(0).getEmail());
        assertEquals("usuario2@gmail.com", result.get(1).getEmail());
    }

    @Test
    @DisplayName("Should not get all usuarios when DB is empty")
    void testFindAllCase2() {
        List<Usuario> result = usuarioService.findAll();

        when(result).thenReturn(new ArrayList<Usuario>());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should be create a usuario when insert")
    void testCreateCase1() throws UserAlreadyCreated {
        Usuario usuario = new Usuario("usuario@gmail.com", "12345678");
        List<Usuario> usuarios = new ArrayList<Usuario>();
        
        usuarios.add(usuario);
        when(usuarioService.create(usuario)).thenReturn(new Usuario());
        
        Usuario result = usuarioService.create(usuario);


        assertNotNull(result);
        assertEquals("usuario@gmail.com", usuario.getEmail());
        assertNotNull(usuario.getPassword());
    }

    @Test
    void testCreateCase2() {
        
    }

    @Test
    void testDelete() {

    }

    @Test
    void testFindAll() {

    }

    @Test
    void testUpdate() {

    }
}
