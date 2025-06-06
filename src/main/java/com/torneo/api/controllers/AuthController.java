package com.torneo.api.controllers;

import com.torneo.api.dto.LoginRequest;
import com.torneo.api.dto.LoginResponse;
import com.torneo.api.dto.RegisterRequest;
import com.torneo.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST de autenticación.
 *
 * Este controlador expone dos endpoints principales:
 * - POST `/api/auth/register`: registra un nuevo usuario y retorna su token.
 * - POST `/api/auth/login`: autentica al usuario y retorna un token válido.
 *
 * Funciona como intermediario entre el cliente y el servicio de autenticación (`AuthService`),
 * delegando toda la lógica de negocio al servicio.
 *
 * Su objetivo es ofrecer una API limpia y clara para la autenticación de usuarios
 * mediante JWT (JSON Web Tokens).
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
