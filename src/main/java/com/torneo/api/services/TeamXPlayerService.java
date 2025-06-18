package com.torneo.api.services;

import com.torneo.api.dto.TeamXPlayerRequestDTO;
import com.torneo.api.dto.TeamXPlayerResponseDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.TeamXPlayer;
import com.torneo.api.models.User;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TeamXPlayerRepository;
import com.torneo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamXPlayerService {

    @Autowired
    private TeamXPlayerRepository teamXPlayerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    public TeamXPlayerResponseDTO createTeamXPlayer(TeamXPlayerRequestDTO dto) {
        TeamEntity team = teamRepository.findById(dto.getTeamID())
                .orElseThrow(() -> new NotFoundException("No existe el team"));

        User user = userRepository.findById(dto.getUserID())
                .orElseThrow(() -> new NotFoundException("No existe el user"));

        TeamXPlayer teamXPlayer = TeamXPlayer.builder()
                .teamEntity(team)
                .user(user)
                .isCaptain(dto.isCaptain()) // ✅ NUEVA línea para setear el capitán
                .build();

        return mapToResponseDTO(teamXPlayerRepository.save(teamXPlayer));
    }

    public List<TeamXPlayer> getByTeamId(Long teamID) {
        return teamXPlayerRepository.findByTeamEntity_Id(teamID);
    }

    private TeamXPlayerResponseDTO mapToResponseDTO(TeamXPlayer txp) {
        return TeamXPlayerResponseDTO.builder()
                .id(txp.getId())
                .teamID(txp.getTeamEntity().getId())
                .userID(txp.getUser().getId())
                .isCaptain(txp.isCaptain())//si lo queremos mostrar, sería esta opción
                .build();
    }
}
