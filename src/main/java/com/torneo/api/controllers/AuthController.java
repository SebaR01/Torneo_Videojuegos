package com.torneo.api.controllers;

import com.torneo.api.dto.LoginRequest;
import com.torneo.api.dto.LoginResponse;
import com.torneo.api.dto.RegisterRequest;
import com.torneo.api.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Este controlador expone dos endpoints públicos:
 * - /api/auth/register → Para registrar nuevos usuarios
 * - /api/auth/login → Para autenticar usuarios y generar el token JWT
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController
{
    @Autowired
    private AuthService authService;

    // Registro de usuario nuevo
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }

    // Login → devuelve el token si es correcto
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
