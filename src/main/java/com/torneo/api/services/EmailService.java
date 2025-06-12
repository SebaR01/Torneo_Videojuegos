/**
 * Servicio que encapsula la lógica de envío de correos electrónicos.
 *
 * ✔ Usa JavaMailSender para enviar mails SMTP.
 * ✔ Permite enviar mensajes simples con asunto, destinatario y cuerpo.
 * ✔ Se puede invocar desde cualquier parte del sistema (inscripciones, alertas, etc.).
 */

package com.torneo.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("torneos@tuapp.com"); // Podés personalizarlo
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
