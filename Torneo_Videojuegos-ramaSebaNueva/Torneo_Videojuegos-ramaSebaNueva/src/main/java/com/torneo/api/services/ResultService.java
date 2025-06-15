package com.torneo.api.services;

import com.torneo.api.dto.ResultCreateDTO;
import com.torneo.api.dto.ResultDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.Result;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.Tournament;
import com.torneo.api.repository.ResultRepository;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la creación, edición, búsqueda y eliminación de resultados.
 * Cada resultado representa un enfrentamiento entre dos equipos dentro de un torneo.
 */
@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    public ResultDTO createResult(ResultCreateDTO dto) {
        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        TeamEntity winner = teamRepository.findById(dto.getWinerTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo ganador no encontrado"));

        TeamEntity loser = teamRepository.findById(dto.getLoserTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo perdedor no encontrado"));

        Result result = Result.builder()
                .tournament(tournament)
                .winnerTeam(winner)
                .loserTeam(loser)
                .scoreWinnerTeam(dto.getScoreWinnerTeam())
                .scoreLoserTeam(dto.getScoreLoserTeam())
                .build();

        return mapToDTO(resultRepository.save(result));
    }

    public ResultDTO updateResult(Long id, ResultCreateDTO dto) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resultado no encontrado"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        TeamEntity winner = teamRepository.findById(dto.getWinerTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo ganador no encontrado"));

        TeamEntity loser = teamRepository.findById(dto.getLoserTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo perdedor no encontrado"));

        result.setTournament(tournament);
        result.setWinnerTeam(winner);
        result.setLoserTeam(loser);
        result.setScoreWinnerTeam(dto.getScoreWinnerTeam());
        result.setScoreLoserTeam(dto.getScoreLoserTeam());

        return mapToDTO(resultRepository.save(result));
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    public List<ResultDTO> getAll() {
        return resultRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ResultDTO> getById(Long id) {
        return resultRepository.findById(id).map(this::mapToDTO);
    }

    public ResultDTO mapToDTO(Result result) {
        ResultDTO dto = new ResultDTO();
        dto.setId(result.getId());
        dto.setTournamentId(result.getTournament().getId());
        dto.setWinerTeamId(result.getWinnerTeam().getId());
        dto.setLoserTeamId(result.getLoserTeam().getId());
        dto.setScoreWinnerTeam(result.getScoreWinnerTeam());
        dto.setScoreLoserTeam(result.getScoreLoserTeam());
        return dto;
    }
}
