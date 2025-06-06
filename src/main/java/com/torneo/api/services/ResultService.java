package com.torneo.api.services;

import com.torneo.api.dto.ResultCreateDTO;
import com.torneo.api.dto.ResultDTO;
import com.torneo.api.models.Result;
import com.torneo.api.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        result.setWinnerTeamId(resultCreateDTO.getWinerTeamId());
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
        result.setWinnerTeamId(resultCreateDTO.getWinerTeamId());
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
        dto.setWinerTeamId(result.getWinnerTeamId());

        return dto;
    }
}
