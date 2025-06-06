package com.torneo.api.services;

import com.torneo.api.dto.TeamRequestDTO;
import com.torneo.api.dto.TeamResponseDTO;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.PlayerEntity;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio que maneja toda la lógica relacionada con equipos.
 * Permite crear, actualizar, eliminar, buscar y filtrar equipos,
 * siempre trabajando con DTOs para desacoplar la lógica.
 */
@Service
@RequiredArgsConstructor
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        Set<PlayerEntity> players = getPlayersByIds(dto.getPlayersIds());

        TeamEntity team = TeamEntity.builder()
                .name(dto.getName())
                .players(players)
                .tournamentId(dto.getTournamentId())
                .build();

        return toDTO(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO dto) {
        TeamEntity team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado"));

        team.setName(dto.getName());
        team.setPlayers(getPlayersByIds(dto.getPlayersIds()));
        team.setTournamentId(dto.getTournamentId());

        return toDTO(teamRepository.save(team));
    }

    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    public TeamResponseDTO findTeamById(Long id) {
        return teamRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado"));
    }

    public List<TeamResponseDTO> listTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TeamResponseDTO> filterTeamsByTournamentId(Long tournamentId) {
        return teamRepository.findAll()
                .stream()
                .filter(e -> e.getTournamentId().equals(tournamentId))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------- Auxiliares ------------

    private Set<PlayerEntity> getPlayersByIds(Set<Long> ids) {
        return ids.stream()
                .map(id -> playerRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado")))
                .collect(Collectors.toSet());
    }

    private TeamResponseDTO toDTO(TeamEntity team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .tournamentId(team.getTournamentId())
                .playersIds(
                        team.getPlayers().stream()
                                .map(PlayerEntity::getId)
                                .collect(Collectors.toSet()))
                .build();
    }
}
