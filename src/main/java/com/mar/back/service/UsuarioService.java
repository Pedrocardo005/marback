package com.mar.back.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mar.back.exceptions.UserAlreadyCreated;
import com.mar.back.model.Usuario;
import com.mar.back.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ArrayList<Usuario> findAll() {
        return (ArrayList<Usuario>) usuarioRepository.findAll();
    }

    public Usuario create(Usuario usuario) throws UserAlreadyCreated {
        String encodedPassword = new BCryptPasswordEncoder().encode(usuario.getPassword());
        Optional<Usuario> opUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (opUser.isEmpty()) {
            usuario.setPassword(encodedPassword);
            return usuarioRepository.save(usuario);
        } else {
            throw new UserAlreadyCreated("Usuário já cadastrado!");
        }
        
    }

    public Usuario update(Usuario usuario, Long id) throws Exception {
        Optional<Usuario> usuario2 = usuarioRepository.findById(id);

        if (usuario2.isPresent()) {
            return usuarioRepository.save(usuario);
        } else {
            throw new Exception("Não encontrado");
        }
    }

    public void delete(Long id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            throw new Exception("Não encontrado");
        }
    }
}
