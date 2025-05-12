package com.torneo.api.Controllers;

import com.torneo.api.Models.Team;
import com.torneo.api.Services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Reseña:
 * Este controlador expone las rutas HTTP para gestionar los equipos.
 * Permite listar equipos, crear uno nuevo, asociar jugadores a un equipo y eliminarlo.
 * Utiliza TeamService para ejecutar la lógica de negocio.
 */

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        return ResponseEntity.ok(teamService.addPlayerToTeam(teamId, playerId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
