package com.torneo.api.controllers;

import com.torneo.api.models.Inscription;
import com.torneo.api.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que gestiona las inscripciones de equipos a torneos.
 *
 * Este controlador expone endpoints protegidos para:
 * ✅ Registrar una nueva inscripción, validando fecha de inicio y cupo.
 * ✅ Cancelar una inscripción existente.
 *
 * Al registrar una inscripción:
 * 🔸 Se guarda la relación equipo/torneo en la base de datos.
 * 🔸 Se envía automáticamente un correo de confirmación al capitán del equipo.
 * 🔸 Se notifica por correo al organizador del torneo.
 *
 * Requiere autenticación JWT y roles con permisos adecuados (ej. ADMIN u ORGANIZADOR).
 */

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    /**
     * Endpoint para inscribir un equipo a un torneo.
     * Requiere rol ADMIN u ORGANIZADOR.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<Inscription> inscribirEquipo(@RequestBody Inscription inscripcion) {
        Inscription saved = inscriptionService.inscribir(inscripcion);
        return ResponseEntity.ok(saved);
    }

    /**
     * Endpoint para cancelar una inscripción por ID.
     * Requiere rol ADMIN u ORGANIZADOR.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarInscripcion(@PathVariable Long id) {
        inscriptionService.cancelarInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
