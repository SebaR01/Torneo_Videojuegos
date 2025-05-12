package com.torneo.api.Services;

import com.torneo.api.Models.Player;
import com.torneo.api.Models.Team;
import com.torneo.api.Repository.PlayerRepository;
import com.torneo.api.Repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Reseña:
 * Esta clase maneja la lógica de negocio relacionada con los equipos.
 * Permite crear equipos, listar todos, obtener por ID y agregar jugadores a un equipo.
 * Se apoya en los repositorios TeamRepository y PlayerRepository para interactuar con la base de datos.
 */

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    public Team addPlayerToTeam(Long teamId, Long playerId) {
        Team team = getTeamById(teamId);
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));

        team.getPlayers().add(player);
        return teamRepository.save(team);
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
