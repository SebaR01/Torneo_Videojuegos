package com.torneo.api.controllers;

import com.torneo.api.dto.PlayerRequestDTO;
import com.torneo.api.dto.PlayerResponseDTO;
import com.torneo.api.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que maneja las operaciones sobre jugadores.
 * Usa DTOs y protege endpoints según el rol del usuario.
 */
@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class PlayerController {
    @Autowired
    private final PlayerService playerService;

    /**
     * Lista todos los jugadores.
     * Requiere estar autenticado.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PlayerResponseDTO>> listPlayers() {
        return ResponseEntity.ok(playerService.listPlayers());
    }

    /**
     * Busca un jugador por ID.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> findPlayerById(@PathVariable Integer id) {
        return ResponseEntity.ok(playerService.findPlayerById(id));
    }

    /**
     * Crea un nuevo jugador. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody PlayerRequestDTO playerDTO) {
        return ResponseEntity.ok(playerService.createPlayer(playerDTO));
    }

    /**
     * Actualiza un jugador existente. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer (@PathVariable Integer id,
                                                                @RequestBody PlayerRequestDTO playerDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDTO));
    }

    /**
     * Elimina un jugador por ID. Solo ADMIN puede hacerlo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer (@PathVariable Integer id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista los jugadores de un equipo específico.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<PlayerResponseDTO>> listByTeam(@PathVariable Integer teamId) {
        return ResponseEntity.ok(playerService.listPlayersByTeam(teamId));
    }

    /**
     * Lista los jugadores que participan en un torneo (a través de equipos).
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<PlayerResponseDTO>> listByTournament(@PathVariable Integer tournamentId) {
        return ResponseEntity.ok(playerService.listPlayersByTournament(tournamentId));
    }
}
