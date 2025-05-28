package com.torneo.api.services;

import com.torneo.api.dto.EquipoRequestDTO;
import com.torneo.api.dto.EquipoResponseDTO;
import com.torneo.api.models.EquipoEntity;
import com.torneo.api.models.JugadorEntity;
import com.torneo.api.repository.EquipoRepository;
import com.torneo.api.repository.JugadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Servicio que maneja toda la lógica relacionada con equipos.
 * Permite crear, actualizar, eliminar, buscar y filtrar equipos,
 * siempre trabajando con DTOs para desacoplar la lógica.
 */
@Service
@RequiredArgsConstructor
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private JugadorRepository jugadorRepository;

    /**
     * Registra un nuevo equipo usando un DTO.
     * Verifica que los jugadores existan.
     */
    public EquipoResponseDTO crearEquipo(EquipoRequestDTO dto) {
        Set<JugadorEntity> jugadores = obtenerJugadoresDesdeIds(dto.getJugadoresIds());

        EquipoEntity equipo = EquipoEntity.builder()
                .nombre(dto.getNombre())
                .jugadores(jugadores)
                .torneoId(dto.getTorneoId())
                .build();

        return convertirADTO(equipoRepository.save(equipo));
    }

    /**
     * Actualiza un equipo existente.
     * Solo deberían poder hacerlo ORGANIZADOR o ADMIN (verificar en controller).
     */
    public EquipoResponseDTO actualizarEquipo(Integer id, EquipoRequestDTO dto) {
        EquipoEntity equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado"));

        equipo.setNombre(dto.getNombre());
        equipo.setJugadores(obtenerJugadoresDesdeIds(dto.getJugadoresIds()));
        equipo.setTorneoId(dto.getTorneoId());

        return convertirADTO(equipoRepository.save(equipo));
    }

    /**
     * Elimina un equipo por su ID.
     */
    public void eliminarEquipo(Integer id) {
        equipoRepository.deleteById(id);
    }

    /**
     * Busca un equipo por ID y lo devuelve como DTO.
     */
    public EquipoResponseDTO buscarEquipoPorId(Integer id) {
        return equipoRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado"));
    }

    /**
     * Devuelve todos los equipos registrados.
     */
    public List<EquipoResponseDTO> listarEquipos() {
        return equipoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Filtra equipos por el ID del torneo.
     */
    public List<EquipoResponseDTO> filtrarEquiposPorTorneoId(Integer torneoId) {
        return equipoRepository.findAll()
                .stream()
                .filter(e -> e.getTorneoId().equals(Long.valueOf(torneoId)))
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ----------------- Métodos auxiliares --------------------

    private Set<JugadorEntity> obtenerJugadoresDesdeIds(Set<Integer> ids) {
        return ids.stream()
                .map(id -> jugadorRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado")))
                .collect(Collectors.toSet());
    }

    private EquipoResponseDTO convertirADTO(EquipoEntity equipo) {
        return EquipoResponseDTO.builder()
                .id(equipo.getId())
                .nombre(equipo.getNombre())
                .torneoId(equipo.getTorneoId())
                .jugadoresIds(
                        equipo.getJugadores().stream()
                                .map(JugadorEntity::getId)
                                .collect(Collectors.toSet()))
                .build();
    }
}
