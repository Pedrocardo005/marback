package com.mar.back.modules.usuario.repositories;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.mar.back.modules.usuario.models.Usuario;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class UsuarioRepositoryTest {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntityManager entityManager;
    
    @Test
    @DisplayName("Should get Usuario sucessfully from DB")
    void findUsuarioByEmailCase1() {
        Usuario usuario = new Usuario("pedro@pedro.com");
        this.createUsuario(usuario);
        this.usuarioRepository.save(usuario);

        Optional<Usuario> result = this.usuarioRepository.findByEmail("pedro@pedro.com");

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Usuario from DB when Usuario not exists")
    void findUsuarioByEmailCase2() {
        Optional<Usuario> result = this.usuarioRepository.findByEmail("pedro@pedro.com");

        assertThat(result.isEmpty()).isTrue();
    }

    private Usuario createUsuario(Usuario usuario) {
        this.entityManager.persist(usuario);
        return usuario;
    }
}
