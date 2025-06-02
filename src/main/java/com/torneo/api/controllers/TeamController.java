package com.torneo.api.controllers;

import com.torneo.api.dto.TeamRequestDTO;
import com.torneo.api.dto.TeamResponseDTO;
import com.torneo.api.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que expone endpoints para gestionar equipos.
 * Usa DTOs y protege los endpoints según el rol del usuario.
 */
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class TeamController {

    @Autowired
    private final TeamService teamService;

    /**
     * Devuelve todos los equipos.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> listarEquipos() {
        return ResponseEntity.ok(teamService.listTeams());
    }

    /**
     * Busca un equipo por su ID.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> findTeamsById (@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.findTeamById(id));
    }

    /**
     * Crea un equipo nuevo. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'JUGADOR')")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> crearEquipo(@RequestBody TeamRequestDTO teamDTO) {
        return ResponseEntity.ok(teamService.createTeam(teamDTO));
    }

    /**
     * Actualiza los datos de un equipo. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable Integer id,
                                                              @RequestBody TeamRequestDTO teamDTO) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDTO));
    }

    /**
     * Elimina un equipo por su ID. Solo ADMIN puede hacerlo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam (@PathVariable Integer id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos los equipos asociados a un torneo específico.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<TeamResponseDTO>> listTeamsByTournament(@PathVariable Integer tournamentId) {
        return ResponseEntity.ok(teamService.filterTeamsByTournamentId(tournamentId));
    }
}
