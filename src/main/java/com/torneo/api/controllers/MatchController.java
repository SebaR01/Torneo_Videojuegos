package com.torneo.api.controllers;

import com.torneo.api.dto.MatchRequestDTO;
import com.torneo.api.dto.MatchResponseDTO;
import com.torneo.api.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que maneja las operaciones sobre partidos.
 * Usa DTOs y protege endpoints según el rol del usuario.
 */
@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    @Autowired
    private final MatchService matchService;

    /**
     * Crea un nuevo partido. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<MatchResponseDTO> createMatch(@RequestBody MatchRequestDTO matchRequestDTO) {
        MatchResponseDTO createdMatch = matchService.createMatch(matchRequestDTO);
        return ResponseEntity.status(201).body(createdMatch);
    }

    /**
     * Lista todos los partidos de un torneo específico.
     * Requiere estar autenticado.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{tournamentId}")
    public ResponseEntity<List<MatchResponseDTO>> getMatchesByTournamentId(@PathVariable Integer tournamentId) {
        List<MatchResponseDTO> matches = matchService.getMatchesByTournamentId(tournamentId);
        return ResponseEntity.ok(matches);
    }

    /**
     * Busca un partido por ID.
     * Requiere estar autenticado.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> getMatchById(@PathVariable Integer id) {
        MatchResponseDTO match = matchService.getMatchById(id);
        return ResponseEntity.ok(match);
    }
}
