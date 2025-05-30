package com.torneo.api.Config;

import com.torneo.api.security.JwtAuthenticationFilter;
import com.torneo.api.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Esta clase configura toda la seguridad de la aplicación.
 * Define qué rutas son públicas, cómo se autentican los usuarios,
 * y cómo se procesan los tokens JWT en cada petición.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Añadido para habilitar la configuración de seguridad web

@EnableMethodSecurity // Habilita anotaciones como @PreAuthorize
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    // Define el encoder para encriptar contraseñas (usado en AuthService)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Permite acceder al AuthenticationManager (usado para login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para APIs REST
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) // Necesario para H2 console
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sesiones sin estado
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso sin autenticación a las rutas públicas
                        .requestMatchers("/api/auth/**", "/h2-console/**").permitAll()
                        // Todas las demás rutas requieren autenticación
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}