package com.torneo.api.services;

import com.torneo.api.dto.LoginRequest;
import com.torneo.api.dto.LoginResponse;
import com.torneo.api.dto.RegisterRequest;
import com.torneo.api.models.User;
import com.torneo.api.repository.UserRepository;
import com.torneo.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación y registro de usuarios.
 *
 * Esta clase encapsula la lógica necesaria para:
 * - Registrar nuevos usuarios con sus credenciales encriptadas.
 * - Verificar credenciales al momento de hacer login.
 * - Generar tokens JWT válidos para usuarios autenticados.
 *
 * Utiliza `AuthenticationManager` para validar credenciales,
 * `PasswordEncoder` para encriptar contraseñas, y `JwtService`
 * para generar y validar tokens seguros.
 *
 * Su propósito es abstraer la lógica de autenticación del resto
 * de la aplicación, brindando un punto central y reutilizable
 * para la gestión de sesiones.
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Registra un nuevo usuario y devuelve su token JWT.
     */
    public String register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        // Generamos el token usando UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtService.generateToken(userDetails);
    }

    /**
     * Autentica un usuario y devuelve un token JWT si las credenciales son válidas.
     */
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new LoginResponse(token);
    }
}
