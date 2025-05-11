package com.torneo.api.Services;

import com.torneo.api.DTO.TournamentCreateDTO;
import com.torneo.api.DTO.TournamentDTO;
import com.torneo.api.Models.Tournament;
import com.torneo.api.Repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentService
{
    @Autowired //Inyecto la dependencia.
    private TournamentRepository tournamentRepository;

    public TournamentDTO createTournament(TournamentCreateDTO tournamentCreateDTO)
    {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentCreateDTO.getName());
        tournament.setGame(tournamentCreateDTO.getGame());
        tournament.setGamesCategory(tournamentCreateDTO.getGamesCategory());
        tournament.setGamesState(tournamentCreateDTO.getGamesState());
        tournament.setOrganizerId(tournamentCreateDTO.getOrganizerId());

        Tournament savedTournament = tournamentRepository.save(tournament);

        return mapToDTO(savedTournament);
    }

    public List<TournamentDTO> getAll() {
        return tournamentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TournamentDTO> getById(Long id) {
        return tournamentRepository.findById(id).map(this::mapToDTO);
    }

    public TournamentDTO updateTournament(Long id, TournamentCreateDTO tournamentCreateDTO) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));
        tournament.setGame(tournamentCreateDTO.getGame());
        tournament.setName(tournamentCreateDTO.getName());
        tournament.setGamesCategory(tournamentCreateDTO.getGamesCategory());
        tournament.setGamesState(tournamentCreateDTO.getGamesState());

        Tournament updatedTournament = tournamentRepository.save(tournament);
        return mapToDTO(updatedTournament);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    private TournamentDTO mapToDTO(Tournament tournament) {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(tournament.getId());
        dto.setGame(tournament.getGame());
        dto.setName(tournament.getName());
        dto.setOrganizerId(tournament.getOrganizerId());
        dto.setGamesCategory(tournament.getGamesCategory());
        dto.setGamesState(tournament.getGamesState());
        return dto;
    }
}
