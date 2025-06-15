/**
 * Controlador REST para gestionar jugadores.
 * ✔ Permite crear, consultar, actualizar y eliminar jugadores.
 * ✔ Filtra jugadores por equipo o torneo.
 * ✔ Utiliza IDs de tipo Long para evitar conflictos.
 */

package com.torneo.api.controllers;

import com.torneo.api.dto.PlayerRequestDTO;
import com.torneo.api.dto.PlayerResponseDTO;
import com.torneo.api.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PlayerResponseDTO>> getAllPlayers() {
        return ResponseEntity.ok(playerService.listPlayers());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.findPlayerById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody PlayerRequestDTO dto) {
        return ResponseEntity.ok(playerService.createPlayer(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerRequestDTO dto) {
        return ResponseEntity.ok(playerService.updatePlayer(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<PlayerResponseDTO>> getByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(playerService.listPlayersByTeam(teamId));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tournament/{tournamentId}")
    public ResponseEntity<List<PlayerResponseDTO>> getByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(playerService.listPlayersByTournament(tournamentId));
    }
}
