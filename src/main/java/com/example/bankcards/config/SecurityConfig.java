package com.example.bankcards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF — REST API не нуждается
                .csrf(csrf -> csrf.disable())
                // Stateless-сессии: без хранителя session на сервере
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Настройка авторизации для разных URL
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()          // эндпоинты логина/регистрации
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")    // ADMIN только к /admin/**
                        .requestMatchers("/api/user/**").hasAnyRole("USER","ADMIN") // USER + ADMIN
                        .anyRequest().authenticated()
                )
                // Включаем поддержку JWT как resource-server
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}
