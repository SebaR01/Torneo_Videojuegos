package com.torneo.api.controllers;

import com.torneo.api.dto.LoginRequest;
import com.torneo.api.dto.LoginResponse;
import com.torneo.api.dto.RegisterRequest;
import com.torneo.api.services.AuthService;
import com.torneo.api.services.EmailService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que expone los endpoints públicos para autenticación.
 * <p>
 * - POST /api/auth/register → Registra un nuevo usuario
 * - POST /api/auth/login → Autentica y genera un token JWT
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Autowired
    private EmailService emailService;

    /**
     * Endpoint para registrar un nuevo usuario.
     *
     * @param request los datos del nuevo usuario a registrar
     * @return ResponseEntity con la respuesta de login (JWT)
     */
    @Operation(summary = "Registra un nuevo usuario",
            description = "Este endpoint permite registrar un nuevo usuario en el sistema. "
                    + "Después del registro, se envía un correo de bienvenida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor al enviar el correo de bienvenida")
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestBody @Parameter(description = "Datos para registrar un nuevo usuario") RegisterRequest request) {
        try {
            // Enviar correo de bienvenida
            emailService.enviarCorreoRegistroHtml(request.getEmail(), request.getUsername());
        } catch (MessagingException e) {
            System.err.println(e);
        }
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Endpoint para autenticar a un usuario y generar un token JWT.
     *
     * @param request las credenciales de usuario (username y password)
     * @return ResponseEntity con el token JWT generado
     */
    @Operation(summary = "Autentica un usuario",
            description = "Este endpoint autentica al usuario con su nombre de usuario y contraseña, "
                    + "y devuelve un token JWT para la autenticación futura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado con éxito"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody @Parameter(description = "Credenciales de acceso para login") LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
