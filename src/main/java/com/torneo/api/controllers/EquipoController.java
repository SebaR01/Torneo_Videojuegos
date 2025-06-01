package com.torneo.api.controllers;

import com.torneo.api.dto.EquipoRequestDTO;
import com.torneo.api.dto.EquipoResponseDTO;
import com.torneo.api.services.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador que expone endpoints para gestionar equipos.
 * Usa DTOs y protege los endpoints según el rol del usuario.
 */
@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoController {

    @Autowired
    private final EquipoService equipoService;

    /**
     * Devuelve todos los equipos.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<EquipoResponseDTO>> listarEquipos() {
        return ResponseEntity.ok(equipoService.listarEquipos());
    }

    /**
     * Busca un equipo por su ID.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> buscarEquipoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(equipoService.buscarEquipoPorId(id));
    }

    /**
     * Crea un equipo nuevo. Solo JUGADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'JUGADOR')")
    @PostMapping
    public ResponseEntity<EquipoResponseDTO> crearEquipo(@RequestBody EquipoRequestDTO equipoDTO) {
        return ResponseEntity.ok(equipoService.crearEquipo(equipoDTO));
    }

    /**
     * Actualiza los datos de un equipo. Solo JUGADOR o ADMIN pueden hacerlo.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<EquipoResponseDTO> actualizarEquipo(@PathVariable Integer id,
                                                              @RequestBody EquipoRequestDTO equipoDTO) {
        return ResponseEntity.ok(equipoService.actualizarEquipo(id, equipoDTO));
    }

    /**
     * Elimina un equipo por su ID. Solo ADMIN puede hacerlo.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Integer id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos los equipos asociados a un torneo específico.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<EquipoResponseDTO>> listarEquiposPorTorneo(@PathVariable Integer torneoId) {
        return ResponseEntity.ok(equipoService.filtrarEquiposPorTorneoId(torneoId));
    }
}
