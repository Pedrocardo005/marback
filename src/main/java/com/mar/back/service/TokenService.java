package com.mar.back.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mar.back.model.Usuario;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) {
        return JWT.create()
                .withIssuer("Produtos")
                .withSubject(usuario.getUsername())
                .withClaim("id", usuario.getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(10) // Coloca para expirar em 10 minutos
                        .toInstant(ZoneOffset.of("-03:00")) // Nosso fuso horário
                ).sign(Algorithm.HMAC256("secreta"));
    }

}
