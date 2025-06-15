package com.torneo.api.services;

import com.torneo.api.dto.TeamXPlayerRequestDTO;
import com.torneo.api.dto.TeamXPlayerResponseDTO;
import com.torneo.api.dto.TournamentRequestDTO;
import com.torneo.api.dto.TournamentResponseDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.TeamXPlayer;
import com.torneo.api.models.Tournament;
import com.torneo.api.models.User;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TeamXPlayerRepository;
import com.torneo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamXPlayerService
{

    private TeamXPlayerRepository teamXPlayerRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public TeamXPlayerResponseDTO createTeamXPlayer(TeamXPlayerRequestDTO dto)
    {
        TeamEntity team = teamRepository.findById(dto.getTeamID()).orElseThrow(() -> new NotFoundException("No existe el team"));
        User user = userRepository.findById(dto.getUserID()).orElseThrow(() -> new NotFoundException("No existe el user"));

        TeamXPlayer teamXPlayer = TeamXPlayer.builder()
                .teamEntity(team)
                .user(user).build();
        return mapToResponseDTO(teamXPlayerRepository.save(teamXPlayer));
    }

    private TeamXPlayerResponseDTO mapToResponseDTO(TeamXPlayer txp)
    {
        return TeamXPlayerResponseDTO.builder()
                .id(txp.getId())
                .teamID(txp.getTeamEntity().getId())
                .userID(txp.getUser().getId()).build();
    }

}

