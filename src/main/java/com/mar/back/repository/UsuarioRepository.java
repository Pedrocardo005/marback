package com.mar.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mar.back.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

}
