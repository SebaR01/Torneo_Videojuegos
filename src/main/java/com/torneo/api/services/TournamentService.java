package com.torneo.api.services;


import com.torneo.api.dto.TournamentCreateDTO;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.models.Tournament;
import com.torneo.api.repository.TournamentRepository;
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
        tournament.setCategory(tournamentCreateDTO.getCategory());
        tournament.setGame(tournamentCreateDTO.getGame());
        tournament.setOrganizerId(tournamentCreateDTO.getOrganizerId());

        Tournament savedTournament = tournamentRepository.save(tournament);

        return mapToDTO(savedTournament);
    }

    public List<TournamentDTO> getAllByState(GamesState gamesState)
    {
        return tournamentRepository.findByState(gamesState).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public List<TournamentDTO> getAllByCategory(GamesCategory gamesCategory)
    {
        return tournamentRepository.findByCategory(gamesCategory).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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
        tournament.setCategory(tournamentCreateDTO.getCategory());
        tournament.setState(tournamentCreateDTO.getState());

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
        dto.setCategory(tournament.getCategory());
        dto.setState(tournament.getState());
        return dto;
    }
}