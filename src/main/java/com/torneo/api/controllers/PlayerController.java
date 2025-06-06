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
 * Proporciona endpoints protegidos que permiten crear, modificar, eliminar
 * y consultar jugadores, así como listarlos por equipo o torneo.
 */
@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class PlayerController {

    @Autowired
    private final PlayerService playerService;

    /**
     * Lista todos los jugadores registrados en el sistema.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<PlayerResponseDTO>> listPlayers() {
        return ResponseEntity.ok(playerService.listPlayers());
    }

    /**
     * Busca un jugador por su ID único.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> findPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.findPlayerById(id));
    }

    /**
     * Crea un nuevo jugador.
     * Solo usuarios con rol ADMIN o ORGANIZADOR pueden acceder.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<PlayerResponseDTO> createPlayer(@RequestBody PlayerRequestDTO playerDTO) {
        return ResponseEntity.ok(playerService.createPlayer(playerDTO));
    }

    /**
     * Actualiza un jugador existente.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(@PathVariable Long id,
                                                          @RequestBody PlayerRequestDTO playerDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDTO));
    }

    /**
     * Elimina un jugador por ID. Solo ADMIN puede hacerlo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista los jugadores que pertenecen a un equipo específico.
     * ⚠️ Corrección realizada: el nombre del parámetro ahora coincide con el PathVariable.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/equipo/{teamId}")
    public ResponseEntity<List<PlayerResponseDTO>> listByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(playerService.listPlayersByTeam(teamId));
    }

    /**
     * Lista los jugadores que participan en un torneo, a través de sus equipos.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{tournamentId}")
    public ResponseEntity<List<PlayerResponseDTO>> listByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(playerService.listPlayersByTournament(tournamentId));
    }
}
