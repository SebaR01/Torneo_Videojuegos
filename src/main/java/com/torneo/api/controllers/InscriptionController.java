package com.torneo.api.controllers;

import com.torneo.api.dto.InscriptionRequestDTO;
import com.torneo.api.dto.InscriptionResponseDTO;
import com.torneo.api.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar inscripciones de equipos en torneos.
 * Permite registrar, listar y eliminar inscripciones.
 */
@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {

    private final InscriptionService inscriptionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping
    public ResponseEntity<InscriptionResponseDTO> register(@RequestBody InscriptionRequestDTO dto) {
        return ResponseEntity.ok(inscriptionService.registerInscription(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<InscriptionResponseDTO>> getAll() {
        return ResponseEntity.ok(inscriptionService.getAll());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<InscriptionResponseDTO>> getByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(inscriptionService.getByTournament(tournamentId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<InscriptionResponseDTO>> getByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(inscriptionService.getByTeam(teamId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
