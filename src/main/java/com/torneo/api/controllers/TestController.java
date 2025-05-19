package com.torneo.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de prueba para verificar si la API está funcionando correctamente.
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String hello() {
        return "¡La API del Torneo está funcionando!";
    }
}
