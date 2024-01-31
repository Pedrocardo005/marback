package com.mar.back;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mar.back.model.Usuario;
import com.mar.back.repository.UsuarioRepository;
import com.mar.back.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token;

        String authorizationHeader = request.getHeader("Authorization");

        // Filtro para saber se está logado
        if (authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer ", "");
            String subject = this.tokenService.getSubject(token);

            Optional<Usuario> opUser = this.usuarioRepository.findByEmail(subject);

            Usuario usuario = opUser.get();

            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Passa para o próximo filtro
        filterChain.doFilter(request, response);
    }

}
