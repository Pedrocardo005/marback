package com.mar.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Configurations {

    @Autowired
    private FilterToken filterToken;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Criado no vídeo

        // return http.csrf().disable()
        // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and().authorizeHttpRequests()
        // .antMatchers(HttpMethod.POST, "/login") // URL permitida sem autenticação
        // .permiteAll()
        // .antMatchers(HttpMethod.GET, "/home") // URL permitida sem autenticação
        // .permiteAll()
        // .anyRequest().authenticated().and().build(); // Todas as outras URL
        // precisarão de autenticação

        // Eu tive de refazer
        return http.csrf((csrf) -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login")
                        .permitAll()
                        .requestMatchers("/usuarios")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(filterToken, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Necessário para a classe AuthenticationManager lá do AuthController saber
    // injetar
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
