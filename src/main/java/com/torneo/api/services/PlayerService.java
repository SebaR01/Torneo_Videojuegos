package com.torneo.api.services;

import com.torneo.api.dto.PlayerRequestDTO;
import com.torneo.api.dto.PlayerResponseDTO;
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
 * Servicio que gestiona la lógica de jugadores.
 * Usa DTOs para recibir y devolver información, validando referencias a equipos.
 */
@Service
@RequiredArgsConstructor
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    public PlayerResponseDTO createPlayer(PlayerRequestDTO dto) {
        Set<TeamEntity> teams = getTeamsById(dto.getTeamsIds());

        PlayerEntity player = PlayerEntity.builder()
                .nickname(dto.getNickname())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .teams(teams)
                .build();

        return toDTO(playerRepository.save(player));
    }

    public PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO dto) {
        PlayerEntity player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado"));

        player.setNickname(dto.getNickname());
        player.setName(dto.getName());
        player.setLastName(dto.getLastName());
        player.setEmail(dto.getEmail());
        player.setAge(dto.getAge());
        player.setTeams(getTeamsById(dto.getTeamsIds()));

        return toDTO(playerRepository.save(player));
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public PlayerResponseDTO findPlayerById(Long id) {
        return playerRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado"));
    }

    public List<PlayerResponseDTO> listPlayers() {
        return playerRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PlayerResponseDTO> listPlayersByTeam(Long teamId) {
        return playerRepository.findPlayersByTeamId(teamId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PlayerResponseDTO> listPlayersByTournament(Long tournamentId) {
        return playerRepository.findPlayerByTournamentId(tournamentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // -------------------- Métodos auxiliares --------------------

    private Set<TeamEntity> getTeamsById(Set<Long> ids) {
        return ids == null ? Set.of() : ids.stream()
                .map(id -> teamRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado")))
                .collect(Collectors.toSet());
    }

    private PlayerResponseDTO toDTO(PlayerEntity player) {
        return PlayerResponseDTO.builder()
                .id(player.getId())
                .nickname(player.getNickname())
                .name(player.getName())
                .lastName(player.getLastName())
                .email(player.getEmail())
                .age(player.getAge())
                .teamsIds(player.getTeams().stream()
                        .map(TeamEntity::getId)
                        .collect(Collectors.toSet()))
                .build();
    }
}
