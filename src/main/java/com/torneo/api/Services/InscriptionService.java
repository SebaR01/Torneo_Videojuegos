package com.torneo.api.Services;

import com.torneo.api.Models.Inscription;
import com.torneo.api.Models.Team;
import com.torneo.api.Models.Tournament;
import com.torneo.api.Repository.InscriptionRepository;
import com.torneo.api.Repository.TeamRepository;
import com.torneo.api.Repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/*
 * Reseña:
 * Este servicio maneja la lógica de negocio relacionada con las inscripciones de equipos a torneos.
 * Permite registrar una nueva inscripción, listar todas y eliminar inscripciones existentes.
 * Utiliza los repositorios de inscripción, equipo y torneo para operar sobre la base de datos.
 */
@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    public List<Inscription> getAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    public Inscription createInscription(Long teamId, Long tournamentId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        Inscription inscription = new Inscription();
        inscription.setTeam(team);
        inscription.setTournament(tournament);
        inscription.setFechaInscripcion(LocalDate.now());
        inscription.setCostoInscripcion(tournament.getCost());

        return inscriptionRepository.save(inscription);
    }

    public void deleteInscription(Long id) {
        inscriptionRepository.deleteById(id);
    }
}
