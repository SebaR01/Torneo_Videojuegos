package com.torneo.api.services;


import com.torneo.api.Models.Tournament;
import com.torneo.api.dto.TournamentCreateDTO;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar operaciones relacionadas con torneos.
 * Proporciona métodos para crear, consultar, actualizar y eliminar torneos,
 * así como para mapear entidades de torneo a DTOs.
 */
@Service
public class TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    /**
     * Crea un nuevo torneo a partir de los datos proporcionados en un DTO.
     *
     * @param tournamentCreateDTO Objeto con los datos del torneo a crear (nombre, juego, categoría, organizador).
     * @return TournamentDTO con los datos del torneo creado.
     * @throws IllegalArgumentException si los datos del DTO no cumplen con las validaciones.
     */
    public TournamentDTO createTournament(TournamentCreateDTO tournamentCreateDTO) {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentCreateDTO.getName());
        tournament.setGame(tournamentCreateDTO.getGame());
        tournament.setCategory(tournamentCreateDTO.getCategory());
        tournament.setStartDate(tournamentCreateDTO.getStartDate());
        tournament.setEndDate(tournamentCreateDTO.getEndDate());
        tournament.setOrganizerID(tournamentCreateDTO.getOrganizerId());

        Tournament savedTournament = tournamentRepository.save(tournament);
        return mapToDTO(savedTournament);
    }

    /**
     * Obtiene una lista de torneos filtrados por estado.
     *
     * @param gamesState Estado del torneo (por ejemplo, PLANIFICADO, EN_CURSO, COMPLETADO, CANCELADO).
     * @return Lista de TournamentDTO con los torneos que coinciden con el estado especificado.
     */
    public List<TournamentDTO> getAllByState(GamesState gamesState) {
        return tournamentRepository.findByState(gamesState).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene una lista de torneos filtrados por categoría de juego.
     *
     * @param gamesCategory Categoría del juego (por ejemplo, FPS, MOBA, ESTRATEGIA).
     * @return Lista de TournamentDTO con los torneos que coinciden con la categoría especificada.
     */
    public List<TournamentDTO> getAllByCategory(GamesCategory gamesCategory) {
        return tournamentRepository.findByCategory(gamesCategory).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los torneos disponibles en el sistema.
     *
     * @return Lista de TournamentDTO con todos los torneos.
     */
    public List<TournamentDTO> getAll() {
        return tournamentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un torneo por su ID.
     *
     * @param id ID del torneo a buscar.
     * @return Optional con el TournamentDTO si se encuentra, o vacío si no existe.
     */
    public Optional<TournamentDTO> getById(Long id) {
        return tournamentRepository.findById(id).map(this::mapToDTO);
    }

    /**
     * Actualiza un torneo existente identificado por su ID.
     *
     * @param id ID del torneo a actualizar.
     * @param tournamentCreateDTO Objeto con los datos actualizados del torneo.
     * @return TournamentDTO con los datos del torneo actualizado.
     * @throws RuntimeException si el torneo con el ID especificado no existe.
     */
    public TournamentDTO updateTournament(Long id, TournamentCreateDTO tournamentCreateDTO) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));
        tournament.setGame(tournamentCreateDTO.getGame());
        tournament.setName(tournamentCreateDTO.getName());
        tournament.setCategory(tournamentCreateDTO.getCategory());
        tournament.setState(tournamentCreateDTO.getState());
        tournament.setStartDate(tournamentCreateDTO.getStartDate()); // Corrección: añadido setStartDate
        tournament.setEndDate(tournamentCreateDTO.getEndDate()); // Corrección: añadido setEndDate

        Tournament updatedTournament = tournamentRepository.save(tournament);
        return mapToDTO(updatedTournament);
    }

    /**
     * Elimina un torneo por su ID.
     *
     * @param id ID del torneo a eliminar.
     * @throws RuntimeException si el torneo con el ID especificado no existe.
     */
    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    /**
     * Mapea una entidad Tournament a un TournamentDTO.
     *
     * @param tournament Entidad Tournament a mapear.
     * @return TournamentDTO con los datos mapeados.
     */
    private TournamentDTO mapToDTO(Tournament tournament) {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(tournament.getId());
        dto.setGame(tournament.getGame());
        dto.setName(tournament.getName());
        dto.setOrganizerId(tournament.getOrganizerID());
        dto.setCategory(tournament.getCategory());
        dto.setState(tournament.getState());
        return dto;
    }
}