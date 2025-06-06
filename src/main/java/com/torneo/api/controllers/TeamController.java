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
 * Controlador que maneja las operaciones sobre equipos.
 * Protege los endpoints y delega la lógica al servicio correspondiente.
 */
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class TeamController {

    @Autowired
    private final TeamService teamService;

    /**
     * Crea un nuevo equipo. Solo ADMIN o ORGANIZADOR pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> createTeam(@RequestBody TeamRequestDTO teamDTO) {
        return ResponseEntity.status(201).body(teamService.createTeam(teamDTO));
    }

    /**
     * Actualiza un equipo existente.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(@PathVariable Long id,
                                                      @RequestBody TeamRequestDTO teamDTO) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDTO));
    }

    /**
     * Elimina un equipo por ID.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Devuelve un equipo por su ID.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> findTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.findTeamById(id));
    }

    /**
     * Devuelve todos los equipos registrados.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> listTeams() {
        return ResponseEntity.ok(teamService.listTeams());
    }

    /**
     * Filtra los equipos por ID del torneo.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{tournamentId}")
    public ResponseEntity<List<TeamResponseDTO>> filterTeamsByTournamentId(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(teamService.filterTeamsByTournamentId(tournamentId));
    }
}
