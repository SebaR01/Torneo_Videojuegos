package com.torneo.api.controllers;

import com.torneo.api.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador temporal para probar el envío de correos electrónicos.
 * Se puede eliminar luego de verificar que funciona correctamente.
 */
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping("/test")
    public ResponseEntity<String> testEmail() {
        // Cambiá este correo por el tuyo para probarlo
        emailService.sendEmail("sebaruiz123456@gmail.com", "Prueba de correo", "<h1>¡Correo enviado correctamente!</h1>");
        return ResponseEntity.ok("Correo enviado");
    }
}
