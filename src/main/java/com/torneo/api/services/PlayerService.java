/**
 * Servicio que gestiona la lógica de negocio relacionada a jugadores.
 *
 * ✔ Permite crear, actualizar, eliminar y buscar jugadores.
 * ✔ Filtra jugadores por equipo y por torneo.
 * ✔ Se apoya en DTOs y en validaciones.
 */

package com.torneo.api.services;

import com.torneo.api.dto.PlayerRequestDTO;
import com.torneo.api.dto.PlayerResponseDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.PlayerEntity;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.repository.PlayerRepository;
import com.torneo.api.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerResponseDTO createPlayer(PlayerRequestDTO dto) {
        TeamEntity team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        PlayerEntity player = PlayerEntity.builder()
                .name(dto.getName())
                .nickname(dto.getNickname())
                .team(team)
                .build();

        return mapToDTO(playerRepository.save(player));
    }

    public PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO dto) {
        PlayerEntity player = playerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Jugador no encontrado"));

        TeamEntity team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        player.setName(dto.getName());
        player.setNickname(dto.getNickname());
        player.setTeam(team);

        return mapToDTO(playerRepository.save(player));
    }

    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new NotFoundException("Jugador no encontrado");
        }
        playerRepository.deleteById(id);
    }

    public PlayerResponseDTO findPlayerById(Long id) {
        return playerRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NotFoundException("Jugador no encontrado"));
    }

    public List<PlayerResponseDTO> listPlayers() {
        return playerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PlayerResponseDTO> listPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<PlayerResponseDTO> listPlayersByTournament(Long tournamentId) {
        return playerRepository.findByTeam_Tournament_Id(tournamentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PlayerResponseDTO mapToDTO(PlayerEntity player) {
        return PlayerResponseDTO.builder()
                .id(player.getId())
                .name(player.getName())
                .nickname(player.getNickname())
                .teamName(player.getTeam().getName())
                .build();
    }
}
