package com.mar.back.modules.usuario.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("Should be return an UserAlreadyCreated exception when create two equals users with same email")
    void testCreateCase2() throws UserAlreadyCreated {
        Usuario usuario = new Usuario("usuario1@gmail.com", "12345678");
        Usuario usuario2 = new Usuario("usuario@gmail.com", "12345678");
        
        when(usuarioRepository.findByEmail("usuario1@gmail.com")).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail("usuario@gmail.com")).thenReturn(Optional.of(usuario2));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(new Usuario());
        
        usuarioService.create(usuario);
        Exception exception = assertThrows(UserAlreadyCreated.class, () ->  {
            usuarioService.create(usuario2);
        });

        String expectedMessage = "Usuário já cadastrado!";
        String currentMessage = exception.getMessage();

        assertTrue(currentMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Should be deleted an usuario when pass your id")
    void testDeleteCase1() throws Exception {
        Usuario usuario = new Usuario("usuario1@gmail.com", "12345678");
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertDoesNotThrow(() -> usuarioService.delete(1L));
    }

    @Test
    void testUpdate() {

    }
}
