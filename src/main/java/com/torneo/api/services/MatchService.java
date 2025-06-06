package com.torneo.api.services;

import com.torneo.api.dto.MatchRequestDTO;
import com.torneo.api.dto.MatchResponseDTO;
import com.torneo.api.enums.MatchStatus;
import com.torneo.api.models.Inscription;
import com.torneo.api.models.MatchEntity;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.repository.InscriptionRepository;
import com.torneo.api.repository.MatchRepository;
import com.torneo.api.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de partidos.
 * Usa DTOs para crear, consultar y actualizar partidos.
 * Además, genera automáticamente partidos tipo "todos contra todos".
 */
@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final InscriptionRepository inscriptionRepository;

    public MatchResponseDTO createMatch(MatchRequestDTO dto) {
        MatchEntity match = MatchEntity.builder()
                .matchDate(dto.getMatchDate())
                .status(dto.getStatus())
                .tournamentId(dto.getTournamentId())
                .firstTeam(getTeamById(dto.getFirstTeamId()))
                .secondTeam(getTeamById(dto.getSecondTeamId()))
                .firstTeamScore(dto.getFirstTeamScore())
                .secondTeamScore(dto.getSecondTeamScore())
                .build();

        return toDTO(matchRepository.save(match));
    }

    public MatchResponseDTO updateMatch(Long id, MatchRequestDTO dto) {
        MatchEntity match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido con ID " + id + " no encontrado"));

        match.setMatchDate(dto.getMatchDate());
        match.setStatus(dto.getStatus());
        match.setTournamentId(dto.getTournamentId());
        match.setFirstTeam(getTeamById(dto.getFirstTeamId()));
        match.setSecondTeam(getTeamById(dto.getSecondTeamId()));
        match.setFirstTeamScore(dto.getFirstTeamScore());
        match.setSecondTeamScore(dto.getSecondTeamScore());

        return toDTO(matchRepository.save(match));
    }

    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }

    public List<MatchResponseDTO> getMatchesByTournamentId(Long tournamentId) {
        return matchRepository.findByTournamentId(tournamentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MatchResponseDTO getMatchById(Long id) {
        return matchRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Partido con ID " + id + " no encontrado"));
    }

    /**
     * Genera todos los partidos posibles del tipo "todos contra todos"
     * para un torneo determinado.
     *
     * @param tournamentId ID del torneo
     */
    @Transactional
    public void generarPartidosRoundRobin(Long tournamentId) {
        List<Inscription> inscripciones = inscriptionRepository.findAll().stream()
                .filter(i -> i.getTournament().getId().equals(tournamentId))
                .toList();

        List<TeamEntity> equipos = inscripciones.stream()
                .map(Inscription::getTeam)
                .toList();

        for (int i = 0; i < equipos.size(); i++) {
            for (int j = i + 1; j < equipos.size(); j++) {
                MatchEntity match = MatchEntity.builder()
                        .tournamentId(tournamentId)
                        .firstTeam(equipos.get(i))
                        .secondTeam(equipos.get(j))
                        .matchDate(null)
                        .firstTeamScore(null)
                        .secondTeamScore(null)
                        .status(MatchStatus.ONGOING)
                        .build();

                matchRepository.save(match);
            }
        }
    }

    // -------------------- Auxiliares --------------------

    private TeamEntity getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado"));
    }

    private MatchResponseDTO toDTO(MatchEntity match) {
        return MatchResponseDTO.builder()
                .id(match.getId())
                .tournamentId(match.getTournamentId())
                .firstTeamId(match.getFirstTeam().getId())
                .secondTeamId(match.getSecondTeam().getId())
                .matchDate(match.getMatchDate())
                .firstTeamScore(match.getFirstTeamScore())
                .secondTeamScore(match.getSecondTeamScore())
                .status(match.getStatus())
                .build();
    }
}
