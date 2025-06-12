/**
 * Servicio que gestiona las operaciones sobre equipos dentro del sistema.
 *
 * ✔ Crea, actualiza, elimina y consulta equipos.
 * ✔ Relaciona equipos con torneos si corresponde.
 * ✔ Maneja correctamente las conversiones de Set a List para los jugadores.
 * ✔ Convierte entidades a DTOs y viceversa.
 */

package com.torneo.api.services;

import com.torneo.api.dto.TeamRequestDTO;
import com.torneo.api.dto.TeamResponseDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.PlayerEntity;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.Tournament;
import com.torneo.api.repository.PlayerRepository;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    public TeamResponseDTO createTeam(TeamRequestDTO dto) {
        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        List<PlayerEntity> players = playerRepository.findAllById(dto.getPlayerIds());

        TeamEntity team = TeamEntity.builder()
                .name(dto.getName())
                .tournament(tournament)
                .players(new ArrayList<>(players))
                .build();

        return mapToDTO(teamRepository.save(team));
    }

    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO dto) {
        TeamEntity team = teamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        List<PlayerEntity> players = playerRepository.findAllById(dto.getPlayerIds());

        team.setName(dto.getName());
        team.setTournament(tournament);
        team.setPlayers(new ArrayList<>(players));

        return mapToDTO(teamRepository.save(team));
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new NotFoundException("Equipo no encontrado");
        }
        teamRepository.deleteById(id);
    }

    public List<TeamResponseDTO> listTeams() {
        return teamRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public TeamResponseDTO findTeamById(Long id) {
        return teamRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));
    }

    public List<TeamResponseDTO> filterTeamsByTournamentId(Long tournamentId) {
        return teamRepository.findByTournament_Id(tournamentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private TeamResponseDTO mapToDTO(TeamEntity team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .tournamentName(team.getTournament() != null ? team.getTournament().getName() : null)
                .build();
    }
}
