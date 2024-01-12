package com.mar.back.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mar.back.model.Usuario;
import com.mar.back.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ArrayList<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Usuario usuario) {
        usuarioService.create(usuario);
        return ResponseEntity.status(201).body("{'message': 'Usuário criado com sucesso!'}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuario2 = usuarioService.update(usuario, id);
            return new ResponseEntity<Usuario>(usuario2, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            usuarioService.delete(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }
}
