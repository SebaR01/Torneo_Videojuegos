package com.torneo.api.services;

import com.torneo.api.dto.InscriptionRequestDTO;
import com.torneo.api.dto.InscriptionResponseDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.Inscription;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.Tournament;
import com.torneo.api.models.TeamXPlayer;
import com.torneo.api.repository.InscriptionRepository;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona las inscripciones de equipos a torneos.
 *
 * ✔ Valida que el equipo y el torneo existan.
 * ✔ Valida que no se repita una inscripción.
 * ✔ Verifica el cupo antes de aceptar.
 * ✔ Si se completa el cupo, genera partidos y envía mails.
 */
@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final EmailService emailService;
    private final PhaseService phaseService;
    private final TeamXPlayerService teamXPlayerService;

    public InscriptionResponseDTO registerInscription(InscriptionRequestDTO dto) {
        TeamEntity team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        // Validar si ya estaba inscripto
        if (inscriptionRepository.findByTeam_IdAndTournament_Id(team.getId(), tournament.getId()).isPresent()) {
            throw new IllegalArgumentException("Este equipo ya está inscripto en este torneo.");
        }

        // Validar cupo
        List<Inscription> inscripcionesExistentes = inscriptionRepository.findByTournamentId(tournament.getId());
        if (inscripcionesExistentes.size() >= tournament.getMaxTeams()) {
            throw new IllegalStateException("El cupo del torneo ya está completo.");
        }

        // Guardar la inscripción
        Inscription nueva = Inscription.builder()
                .team(team)
                .tournament(tournament)
                .build();

        inscriptionRepository.save(nueva);

        // Verificar si se completó el cupo con esta inscripción
        List<Inscription> inscripcionesTotales = inscriptionRepository.findByTournamentId(tournament.getId());
        if (inscripcionesTotales.size() == tournament.getMaxTeams()) {

            // Obtener los equipos
            List<TeamEntity> equipos = inscripcionesTotales.stream()
                    .map(Inscription::getTeam)
                    .collect(Collectors.toList());

            // Generar partidos iniciales
            phaseService.generateInitialPhase(tournament, equipos);

            // Enviar mail a todos los jugadores
            for (TeamEntity equipo : equipos) {
                List<TeamXPlayer> jugadores = teamXPlayerService.getByTeamId(equipo.getId());
                for (TeamXPlayer txp : jugadores) {
                    emailService.sendEmail(
                            txp.getUser().getEmail(),
                            "¡Comienzan los partidos!",
                            "Ya están definidos los partidos del torneo: " + tournament.getName()
                    );
                }
            }
        }

        return mapToDTO(nueva);
    }

    public List<InscriptionResponseDTO> getAll() {
        return inscriptionRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<InscriptionResponseDTO> getByTournament(Long tournamentId) {
        return inscriptionRepository.findByTournamentId(tournamentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<InscriptionResponseDTO> getByTeam(Long teamId) {
        return inscriptionRepository.findByTeamId(teamId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Inscription getInscriptionByTeamAndTournament(Long teamId, Long tournamentId) {
        return inscriptionRepository.findByTeam_IdAndTournament_Id(teamId, tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Inscripción no encontrada"));
    }

    public void delete(Long id) {
        if (!inscriptionRepository.existsById(id)) {
            throw new NotFoundException("Inscripción no encontrada");
        }
        inscriptionRepository.deleteById(id);
    }

    private InscriptionResponseDTO mapToDTO(Inscription i) {
        return InscriptionResponseDTO.builder()
                .id(i.getId())
                .teamName(i.getTeam().getName())
                .tournamentName(i.getTournament().getName())
                .build();
    }

}
