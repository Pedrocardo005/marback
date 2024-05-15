package com.mar.back.modules.usuario.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mar.back.modules.usuario.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
