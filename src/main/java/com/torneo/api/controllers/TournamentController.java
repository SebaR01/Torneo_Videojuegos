package com.torneo.api.controllers;

import com.torneo.api.dto.TournamentCreateDTO;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.services.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Este controlador expone los endpoints para gestionar torneos.
 * Permite crear torneos y listarlos, con restricciones según el rol del usuario.
 */
@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    /**
     * Crea un nuevo torneo.
     * Solo pueden acceder usuarios con rol ADMIN o ORGANIZADOR.
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<TournamentDTO> createTournament(@RequestBody @Valid TournamentCreateDTO dto) {
        return ResponseEntity.ok(tournamentService.createTournament(dto));
    }

    /**
     * Devuelve todos los torneos disponibles.
     * Accesible para cualquier usuario autenticado.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAll() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }
}
