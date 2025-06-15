package com.torneo.api.services;

import com.torneo.api.dto.InscriptionRequestDTO;
import com.torneo.api.dto.InscriptionResponseDTO;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.Inscription;
import com.torneo.api.models.TeamEntity;
import com.torneo.api.models.Tournament;
import com.torneo.api.repository.InscriptionRepository;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona las inscripciones de equipos a torneos.
 *
 * ✔ Valida que el equipo y el torneo existan.
 * ✔ Guarda la inscripción con fecha actual y costo recibido.
 * ✔ Envía un mail al inscribir al equipo.
 * ✔ Convierte entidades en DTOs para exponer al frontend.
 */
@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final EmailService emailService;

    public InscriptionResponseDTO registerInscription(InscriptionRequestDTO dto) {
        TeamEntity team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        Inscription inscription = Inscription.builder()
                .date(LocalDate.now())
                .cost(dto.getCost())
                .team(team)
                .tournament(tournament)
                .build();

        inscriptionRepository.save(inscription);

        // Enviar email al responsable del equipo (podés adaptar destinatario)
        emailService.sendEmail(
                "organizador@torneos.com", // O podrías usar team.getEmail() si tenés ese dato
                "Nueva inscripción al torneo: " + tournament.getName(),
                "El equipo '" + team.getName() + "' se inscribió al torneo '" + tournament.getName() + "'."
        );

        return mapToDTO(inscription);
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

    public void delete(Long id) {
        if (!inscriptionRepository.existsById(id)) {
            throw new NotFoundException("Inscripción no encontrada");
        }
        inscriptionRepository.deleteById(id);
    }

    private InscriptionResponseDTO mapToDTO(Inscription inscription) {
        return InscriptionResponseDTO.builder()
                .id(inscription.getId())
                .teamName(inscription.getTeam().getName())
                .tournamentName(inscription.getTournament().getName())
                .date(inscription.getDate())
                .cost(inscription.getCost())
                .build();
    }
}
