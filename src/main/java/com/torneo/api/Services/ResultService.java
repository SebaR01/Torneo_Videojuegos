package com.torneo.api.Services;

import com.torneo.api.DTO.ResultCreateDTO;
import com.torneo.api.DTO.ResultDTO;
import com.torneo.api.Models.Result;
import com.torneo.api.Repository.ResultRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultService
{
    @Autowired
    private ResultRepository resultRepository;

    public ResultDTO createResult(ResultCreateDTO resultCreateDTO)
    {
        Result result = new Result();

        result.setTournamentId(resultCreateDTO.getTournamentId());
        result.setLoserTeamId(resultCreateDTO.getLoserTeamId());
        result.setWinerTeamId(resultCreateDTO.getWinerTeamId());
        result.setScoreWinnerTeam(resultCreateDTO.getScoreWinnerTeam());
        result.setScoreLoserTeam(resultCreateDTO.getScoreLoserTeam());

        return mapToDTO(resultRepository.save(result));
    }

    public void deleteResult (Long id)
    {
        resultRepository.deleteById(id);
    }

    public ResultDTO updateResult(Long id, ResultCreateDTO resultCreateDTO)
    {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result does not exist"));

        result.setTournamentId(resultCreateDTO.getTournamentId());
        result.setLoserTeamId(resultCreateDTO.getLoserTeamId());
        result.setWinerTeamId(resultCreateDTO.getWinerTeamId());
        result.setScoreWinnerTeam(resultCreateDTO.getScoreWinnerTeam());
        result.setScoreLoserTeam(resultCreateDTO.getScoreLoserTeam());

        return mapToDTO(resultRepository.save(result));
    }

    public ResultDTO findById(Long id)
    {
        return mapToDTO(resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Does not exist a result with id " + id)));
    }

    public List<ResultDTO> getAll ()
    {
        return resultRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ResultDTO> getById(Long id)
    {
        return resultRepository.findById(id).map(this::mapToDTO);
    }

    public ResultDTO mapToDTO(Result result)
    {
        ResultDTO dto = new ResultDTO();
        dto.setId(result.getId());
        dto.setLoserTeamId(result.getLoserTeamId());
        dto.setScoreLoserTeam(result.getScoreLoserTeam());
        dto.setTournamentId(result.getTournamentId());
        dto.setScoreWinnerTeam(result.getScoreWinnerTeam());
        dto.setWinerTeamId(result.getWinerTeamId());

        return dto;
    }
}
