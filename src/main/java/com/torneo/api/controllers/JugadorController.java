package com.torneo.api.controllers;

import com.torneo.api.dto.JugadorRequestDTO;
import com.torneo.api.dto.JugadorResponseDTO;
import com.torneo.api.services.JugadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que maneja las operaciones sobre jugadores.
 * Usa DTOs y protege endpoints según el rol del usuario.
 */
@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {
    @Autowired
    private final JugadorService jugadorService;

    /**
     * Lista todos los jugadores.
     * Requiere estar autenticado.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<JugadorResponseDTO>> listarJugadores() {
        return ResponseEntity.ok(jugadorService.listarJugadores());
    }

    /**
     * Busca un jugador por ID.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> buscarJugadorPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(jugadorService.buscarJugadorPorId(id));
    }

    /**
     * Crea un nuevo jugador. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<JugadorResponseDTO> crearJugador(@RequestBody JugadorRequestDTO jugadorDTO) {
        return ResponseEntity.ok(jugadorService.crearJugador(jugadorDTO));
    }

    /**
     * Actualiza un jugador existente. Solo ORGANIZADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<JugadorResponseDTO> actualizarJugador(@PathVariable Integer id,
                                                                @RequestBody JugadorRequestDTO jugadorDTO) {
        return ResponseEntity.ok(jugadorService.actualizarJugador(id, jugadorDTO));
    }

    /**
     * Elimina un jugador por ID. Solo ADMIN puede hacerlo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable Integer id) {
        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista los jugadores de un equipo específico.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<JugadorResponseDTO>> listarPorEquipo(@PathVariable Integer equipoId) {
        return ResponseEntity.ok(jugadorService.listarJugadoresPorEquipo(equipoId));
    }

    /**
     * Lista los jugadores que participan en un torneo (a través de equipos).
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<JugadorResponseDTO>> listarPorTorneo(@PathVariable Integer torneoId) {
        return ResponseEntity.ok(jugadorService.listarJugadoresPorTorneo(torneoId));
    }
}
