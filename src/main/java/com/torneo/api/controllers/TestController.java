package com.torneo.api.controllers;

import com.torneo.api.dto.TestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de prueba para verificar que el proyecto responda a peticiones.
 * Endpoint: POST /api/test
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody TestDto dto) {
        return ResponseEntity.ok("Hola " + dto.getNombre());
    }
}
