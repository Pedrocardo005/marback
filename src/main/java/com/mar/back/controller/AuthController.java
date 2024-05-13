package com.mar.back.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mar.back.dto.Login;
import com.mar.back.jwt.TokenService;
import com.mar.back.model.Usuario;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @CrossOrigin
    public ResponseEntity<Object> login(@RequestBody Login login) throws Exception {
        Map<String, Object> object = new HashMap<>();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                login.email(), login.password());
        
        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);
            Usuario usuario = (Usuario) authentication.getPrincipal();
            
            object.put("token", tokenService.gerarToken(usuario));
            return new ResponseEntity<Object>(object, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
            object.put("erro", "Ocorreu algum erro interno no servidor");
            return new ResponseEntity<Object>(object, HttpStatus.BAD_REQUEST);
        }
        
    }
}
