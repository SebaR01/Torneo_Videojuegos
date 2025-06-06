package com.torneo.api.services;

import com.torneo.api.models.Inscription;
import com.torneo.api.repository.InscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

/**
 * Servicio encargado de enviar recordatorios automáticos por mail
 * a los capitanes de equipos inscritos cuyo torneo empieza al día siguiente.
 */
@Service
@RequiredArgsConstructor
public class ReminderService {

    private final InscriptionRepository inscriptionRepository;
    private final EmailService emailService;

    /**
     * Esta tarea se ejecuta todos los días a las 8:00 AM
     * y busca torneos que empiezan al día siguiente para notificar a los equipos.
     */
    @Scheduled(cron = "0 0 8 * * *") // todos los días a las 08:00 AM
    public void enviarRecordatorios() {
        LocalDate manana = LocalDate.now().plusDays(1);

        List<Inscription> inscripciones = inscriptionRepository.findAll();

        for (Inscription inscripcion : inscripciones) {
            LocalDate fechaTorneo = inscripcion.getTournament()
                    .getStartDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (fechaTorneo.equals(manana)) {
                String email = inscripcion.getTeam().getCaptain().getEmail();
                String nombreTorneo = inscripcion.getTournament().getName();

                String body = """
                        <h3>¡Recordatorio!</h3>
                        <p>Tu equipo está inscripto en el torneo <strong>%s</strong>, que comienza mañana.</p>
                        <p>¡No olvides revisar el cronograma y preparar a tu equipo!</p>
                        """.formatted(nombreTorneo);

                emailService.sendEmail(email, "Recordatorio: Torneo mañana", body);
            }
        }
    }
}
