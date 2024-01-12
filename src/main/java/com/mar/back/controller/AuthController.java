package com.mar.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mar.back.dto.Login;
import com.mar.back.model.Usuario;
import com.mar.back.service.TokenService;

@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody Login login) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                login.email(), login.password());

        try {
            Authentication authentication = this.authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);
            Usuario usuario = (Usuario) authentication.getPrincipal();
            return tokenService.gerarToken(usuario);
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return "123";
    }
}
