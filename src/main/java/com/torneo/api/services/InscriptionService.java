package com.torneo.api.services;

import com.torneo.api.models.Inscription;
import com.torneo.api.models.Tournament;
import com.torneo.api.repository.InscriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Servicio encargado de gestionar la lógica de negocio para las inscripciones a torneos.
 *
 * Funcionalidades:
 * ✅ Registra una inscripción en base de datos.
 * ✅ Valida si el torneo ya comenzó.
 * ✅ Valida si se alcanzó el máximo de inscripciones.
 * ✅ Envía mail de confirmación al capitán.
 * ✅ Notifica al organizador del torneo.
 * ✅ Permite cancelar inscripciones.
 */
@Service
@RequiredArgsConstructor
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EmailService emailService;

    /**
     * Registra una inscripción si es válida y envía notificaciones.
     */
    @Transactional
    public Inscription inscribir(Inscription inscripcion) {
        Tournament torneo = inscripcion.getTournament();

        // Validación 1: el torneo no debe haber empezado
        LocalDate hoy = LocalDate.now();
        LocalDate fechaInicio = torneo.getStartDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

        if (fechaInicio.isBefore(hoy)) {
            throw new IllegalStateException("El torneo ya ha comenzado. No se permiten nuevas inscripciones.");
        }

        // Validación 2: no debe exceder la cantidad máxima de equipos
        Integer maxEquipos = torneo.getMaxTeams(); // suponemos que este getter existe
        long equiposInscritos = inscriptionRepository.findAll().stream()
                .filter(i -> i.getTournament().getId().equals(torneo.getId()))
                .count();

        if (maxEquipos != null && equiposInscritos >= maxEquipos) {
            throw new IllegalStateException("El torneo alcanzó la cantidad máxima de equipos permitidos.");
        }

        // Guardamos la inscripción
        inscripcion.setFechaInscripcion(LocalDateTime.now());
        Inscription saved = inscriptionRepository.save(inscripcion);

        // Mail al capitán
        String emailCapitan = saved.getTeam().getCaptain().getEmail();
        String nombreTorneo = torneo.getName();
        String fechaTorneo = torneo.getStartDate().toString();

        String bodyCapitan = """
                <h3>¡Inscripción Confirmada!</h3>
                <p>Tu equipo fue inscrito exitosamente en el torneo <strong>%s</strong>.</p>
                <p>Fecha de inicio: <strong>%s</strong>.</p>
                <p>¡Te deseamos mucha suerte!</p>
                """.formatted(nombreTorneo, fechaTorneo);

        emailService.sendEmail(emailCapitan, "Confirmación de Inscripción", bodyCapitan);

        // Mail al organizador
        String emailOrganizador = torneo.getOrganizerID().getEmail();
        String nombreEquipo = saved.getTeam().getName();

        String bodyOrganizador = """
                <h3>Nuevo equipo inscripto</h3>
                <p>El equipo <strong>%s</strong> se ha inscripto en tu torneo <strong>%s</strong>.</p>
                <p>Fecha de inicio: <strong>%s</strong>.</p>
                """.formatted(nombreEquipo, nombreTorneo, fechaTorneo);

        emailService.sendEmail(emailOrganizador, "Nuevo equipo inscripto", bodyOrganizador);

        return saved;
    }

    /**
     * Cancela una inscripción y notifica al organizador.
     */
    @Transactional
    public void cancelarInscripcion(Long inscripcionId) {
        Inscription inscripcion = inscriptionRepository.findById(inscripcionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));

        inscriptionRepository.delete(inscripcion);

        String emailOrganizador = inscripcion.getTournament().getOrganizerID().getEmail();
        String nombreEquipo = inscripcion.getTeam().getName();
        String nombreTorneo = inscripcion.getTournament().getName();

        String body = """
                <h3>Cancelación de inscripción</h3>
                <p>El equipo <strong>%s</strong> ha cancelado su inscripción al torneo <strong>%s</strong>.</p>
                """.formatted(nombreEquipo, nombreTorneo);

        emailService.sendEmail(emailOrganizador, "Cancelación de inscripción", body);
    }
}
