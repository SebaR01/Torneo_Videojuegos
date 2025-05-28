package com.torneo.api.services;

import com.torneo.api.dto.LoginRequest;
import com.torneo.api.dto.LoginResponse;
import com.torneo.api.dto.RegisterRequest;
import com.torneo.api.models.User;
import com.torneo.api.repository.UserRepository;
import com.torneo.api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Esta clase se encarga de registrar nuevos usuarios y autenticar los existentes.
 * Si el login es correcto, genera un token JWT.
 * Si es un registro, guarda el usuario con la contraseña encriptada.
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    private  PasswordEncoder passwordEncoder;
    private  JwtUtil jwtUtil;
    private  AuthenticationManager authenticationManager;
    private  UserDetailsServiceImpl userDetailsService;

    // Registra un nuevo usuario
    public void register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())) // Encripta la contraseña
                .role(request.getRole())
                .build();
        userRepository.save(user);
    }

    // Autentica un usuario y genera el token
    public LoginResponse login(LoginRequest request) {
        // Verifica las credenciales
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Si todo está bien, genera el token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponse(token);
    }
}
