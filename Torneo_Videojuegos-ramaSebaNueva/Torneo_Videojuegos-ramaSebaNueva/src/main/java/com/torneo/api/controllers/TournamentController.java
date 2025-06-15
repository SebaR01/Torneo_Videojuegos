package com.torneo.api.controllers;

import com.torneo.api.dto.TournamentRequestDTO;
import com.torneo.api.dto.TournamentResponseDTO;
import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.services.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de torneos.
 * Permite crear, consultar, filtrar, actualizar y eliminar torneos.
 * Solo ADMIN puede modificar o crear torneos.
 */
@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @PreAuthorize("hasRole('ADMIN','ORGANIZER' )")
    @PostMapping
    public ResponseEntity<TournamentResponseDTO> createTournament(@Valid @RequestBody TournamentRequestDTO dto) {
        return ResponseEntity.ok(tournamentService.createTournament(dto));
    }

    @GetMapping
    public ResponseEntity<List<TournamentResponseDTO>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentResponseDTO> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @GetMapping("/state")
    public ResponseEntity<List<TournamentResponseDTO>> getTournamentsByState(@RequestParam GamesState state) {
        return ResponseEntity.ok(tournamentService.getByState(state));
    }

    @GetMapping("/category")
    public ResponseEntity<List<TournamentResponseDTO>> getTournamentsByCategory(@RequestParam GamesCategory category) {
        return ResponseEntity.ok(tournamentService.getByCategory(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TournamentResponseDTO> updateTournament(@PathVariable Long id, @Valid @RequestBody TournamentRequestDTO dto) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}
