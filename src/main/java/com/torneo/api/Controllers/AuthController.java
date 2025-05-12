package com.torneo.api.Controllers;
import com.torneo.api.DTO.LoginRequest;
import com.torneo.api.Models.User;
import com.torneo.api.Repository.UserRepository;
import com.torneo.api.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String jwt = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok("Bearer " + jwt);
    }
}
/*
AuthController expone el endpoint /auth/login, el cual permite autenticar usuarios y devolver un
token JWT. Usa el AuthenticationManager para validar las credenciales, y luego genera un token
con JwtUtil. Si las credenciales son válidas, se retorna el token JWT en el formato Bearer.
Este controlador es fundamental para la seguridad del sistema, ya que permite a los usuarios autenticarse
y acceder al sistema mediante su token.
 */