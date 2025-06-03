package com.torneo.api.services;

import com.torneo.api.dto.MatchRequestDTO;
import com.torneo.api.dto.MatchResponseDTO;
import com.torneo.api.models.MatchEntity;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.repository.MatchRepository;
import com.torneo.api.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de partidos.
 * Usa DTOs para recibir y devolver información, validando referencias a equipos.
 */
@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired
    private final MatchRepository matchRepository;

    @Autowired
    private final TeamRepository teamRepository;

    public MatchResponseDTO createMatch(MatchRequestDTO dto) {
        MatchEntity match = MatchEntity.builder()
                .matchDate(dto.getMatchDate())
                .status(dto.getStatus())
                .tournamentId(dto.getTournamentId())
                .firstTeam(getTeamById(dto.getFirstTeam().getId()))
                .secondTeam(getTeamById(dto.getSecondTeam().getId()))
                .firstTeamScore(dto.getFirstTeamScore())
                .secondTeamScore(dto.getSecondTeamScore())
                .build();

        return toDTO(matchRepository.save(match));
    }

    public MatchResponseDTO updateMatch(Integer id, MatchRequestDTO dto) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido con ID " + id + " no encontrado"));

        match.setMatchDate(dto.getMatchDate());
        match.setStatus(dto.getStatus()); // Asegúrate de que el DTO tenga este campo
        match.setTournamentId(dto.getTournamentId());
        match.setFirstTeam(getTeamById(dto.getFirstTeam().getId()));
        match.setSecondTeam(getTeamById(dto.getSecondTeam().getId()));
        match.setFirstTeamScore(dto.getFirstTeamScore());
        match.setSecondTeamScore(dto.getSecondTeamScore());

        return toDTO(matchRepository.save(match));
    }

    public void deleteMatch(Integer id) {
        matchRepository.deleteById(id);
    }

    public List<MatchResponseDTO> getMatchesByTournamentId(Integer tournamentId) {
        return matchRepository.findByTournamentId(tournamentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MatchResponseDTO getMatchById(Integer id) {
        return matchRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Partido con ID " + id + " no encontrado"));
    }

    // -------------------- Métodos auxiliares --------------------

    private TeamEntity getTeamById(Integer id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado"));
    }

    private MatchResponseDTO toDTO(MatchEntity match) {
        return MatchResponseDTO.builder()
                .id(match.getId())
                .tournamentId(match.getTournamentId())
                .firstTeam(match.getFirstTeam())
                .secondTeam(match.getSecondTeam())
                .matchDate(match.getMatchDate())
                .firstTeamScore(match.getFirstTeamScore())
                .secondTeamScore(match.getSecondTeamScore())
                .build();
    }
}
