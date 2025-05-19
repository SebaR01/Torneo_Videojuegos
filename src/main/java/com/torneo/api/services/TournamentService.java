package com.torneo.api.services;

import com.torneo.api.dto.TournamentCreateDTO;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.models.Tournament;
import com.torneo.api.models.User;
import com.torneo.api.repository.TournamentRepository;
import com.torneo.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase contiene la lógica para gestionar torneos.
 * Permite crear nuevos torneos, obtener todos los torneos o buscar por filtros.
 * Trabaja con DTOs para desacoplar la lógica de la base de datos.
 */
@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;

    /**
     * Crea un nuevo torneo a partir de un DTO.
     * @param dto contiene los datos del torneo a registrar
     * @return el torneo registrado, convertido a DTO
     */
    public TournamentDTO createTournament(TournamentCreateDTO dto) {
        // Buscar el organizador por ID
        User organizer = userRepository.findById(dto.getOrganizerId())
                .orElseThrow(() -> new EntityNotFoundException("Organizador no encontrado con ID: " + dto.getOrganizerId()));

        Tournament tournament = Tournament.builder()
                .name(dto.getName())
                .game(dto.getGame())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate()) // si no lo usás, lo podés eliminar
                .state(dto.getState())
                .category(dto.getCategory())
                .organizerId(organizer)
                .build();

        return convertToDTO(tournamentRepository.save(tournament));
    }

    /**
     * Devuelve todos los torneos registrados en el sistema.
     * @return lista de torneos como DTOs
     */
    public List<TournamentDTO> getAllTournaments() {
        return tournamentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Tournament en un DTO para enviar al frontend.
     * Extrae solo el ID del organizador.
     */
    private TournamentDTO convertToDTO(Tournament tournament) {
        return TournamentDTO.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .game(tournament.getGame())
                .startDate(tournament.getStartDate())
                .endDate(tournament.getEndDate()) // si no lo usás, comentá esta línea
                .state(tournament.getState())
                .category(tournament.getCategory())
                .organizerId(tournament.getOrganizerId().getId()) // convertir User a Long
                .build();
    }
}
