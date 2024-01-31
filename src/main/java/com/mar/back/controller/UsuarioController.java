package com.mar.back.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody Usuario usuario) {
        Map<String, Object> object = new HashMap<>();
        try {
            usuarioService.create(usuario);
            object.put("message", "Usuário criado com sucesso!");
            return ResponseEntity.status(201).body(object);
        } catch (Exception e) {
            object.put("message", "Usuário já cadastrado");
            return ResponseEntity.status(500).body(object);
        }
        
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
