package com.torneo.api.services;

import com.torneo.api.Models.Tournament;
import com.torneo.api.Models.TournamentXTeam;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.dto.TournamentXTeamCreateDTO;
import com.torneo.api.dto.TournamentXTeamDTO;
import com.torneo.api.repository.TournamentRepository;
import com.torneo.api.repository.TournamentXTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentXTeamService
{
    @Autowired
    private TournamentXTeamRepository tournamentXTeamRepository;

    public TournamentXTeamDTO createTournamentXteam(TournamentXTeamCreateDTO tournamentXTeamCreateDTO)
    {
        TournamentXTeam TXT = new TournamentXTeam();
        TXT.setTournamentId(tournamentXTeamCreateDTO.getTournamentId());
        TXT.setEquipoEntityId(tournamentXTeamCreateDTO.getEquipoEntityId()
        );

        TournamentXTeam savedTXT = tournamentXTeamRepository.save(TXT);
        return mapToDTO(savedTXT);
    }

    public List<TournamentXTeamDTO> getAllByTeamId(int teamId)
    {
        return tournamentXTeamRepository.findByequipoEntityId_id(teamId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<TournamentXTeamDTO> getAll()
    {
        return tournamentXTeamRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteTournamentxTeam (Integer id)
    {
        tournamentXTeamRepository.deleteById(id);
    }

    private TournamentXTeamDTO mapToDTO(TournamentXTeam TXT)
    {
        TournamentXTeamDTO dto = new TournamentXTeamDTO();
        dto.setId(TXT.getId());
        dto.setTournamentId(TXT.getTournamentId());
        dto.setEquipoEntityId(TXT.getEquipoEntityId());
        return dto;

    }
}
