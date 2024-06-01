package com.mar.back.modules.usuario.controllers;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

import com.mar.back.modules.usuario.exceptions.UserAlreadyCreated;
import com.mar.back.modules.usuario.models.Usuario;
import com.mar.back.modules.usuario.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @PostMapping
    @Operation(summary = "Cadastra um usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado",
            content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))
            }),
        @ApiResponse(responseCode = "500", description = "Usuário já cadastrado",
            content = {
                @Content
            })
    })
    public ResponseEntity<Object> create(@RequestBody Usuario usuario) {
        Map<String, Object> object = new HashMap<>();
        try {
            usuarioService.create(usuario);
            object.put("message", "Usuário criado com sucesso!");
            return ResponseEntity.status(201).body(object);
        } catch (UserAlreadyCreated e) {
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
