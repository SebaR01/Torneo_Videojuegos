package com.torneo.api.services;

import com.torneo.api.Models.EquipoEntity;
import com.torneo.api.Models.JugadorEntity;
import com.torneo.api.dto.JugadorRequestDTO;
import com.torneo.api.dto.JugadorResponseDTO;

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
 * Servicio que gestiona la lógica de jugadores.
 * Usa DTOs para recibir y devolver información, validando referencias a equipos.
 */
@Service
@RequiredArgsConstructor
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private EquipoRepository equipoRepository;

    public JugadorResponseDTO crearJugador(JugadorRequestDTO dto) {
        Set<EquipoEntity> equipos = obtenerEquiposDesdeIds(dto.getEquiposIds());

        JugadorEntity jugador = JugadorEntity.builder()
                .nickname(dto.getNickname())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .edad(dto.getEdad())
                .equipos(equipos)
                .build();

        return convertirADTO(jugadorRepository.save(jugador));
    }

    /**
     * Actualiza los datos de un jugador existente.
     */
    public JugadorResponseDTO actualizarJugador(Integer id, JugadorRequestDTO dto) {
        JugadorEntity jugador = jugadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado"));

        jugador.setNickname(dto.getNickname());
        jugador.setNombre(dto.getNombre());
        jugador.setApellido(dto.getApellido());
        jugador.setEmail(dto.getEmail());
        jugador.setEdad(dto.getEdad());
        jugador.setEquipos(obtenerEquiposDesdeIds(dto.getEquiposIds()));

        return convertirADTO(jugadorRepository.save(jugador));
    }

    /**
     * Elimina un jugador por ID.
     */
    public void eliminarJugador(Integer id) {
        jugadorRepository.deleteById(id);
    }

    /**
     * Devuelve todos los jugadores como DTOs.
     */
    public List<JugadorResponseDTO> listarJugadores() {
        return jugadorRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un jugador por ID.
     */
    public JugadorResponseDTO buscarJugadorPorId(Integer id) {
        return jugadorRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new EntityNotFoundException("Jugador con ID " + id + " no encontrado"));
    }

    /**
     * Lista los jugadores que pertenecen a un equipo específico.
     */
    public List<JugadorResponseDTO> listarJugadoresPorEquipo(Integer equipoId) {
        return jugadorRepository.findJugadoresByEquipoId(equipoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista los jugadores que están asociados a un torneo (a través de sus equipos).
     */
    public List<JugadorResponseDTO> listarJugadoresPorTorneo(Integer torneoId) {
        return jugadorRepository.findJugadoresByTorneoId(torneoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // -------------------- Métodos auxiliares --------------------

    private Set<EquipoEntity> obtenerEquiposDesdeIds(Set<Integer> ids) {
        return ids == null ? Set.of() : ids.stream()
                .map(id -> equipoRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Equipo con ID " + id + " no encontrado")))
                .collect(Collectors.toSet());
    }

    private JugadorResponseDTO convertirADTO(JugadorEntity jugador) {
        return JugadorResponseDTO.builder()
                .id(jugador.getId())
                .nickname(jugador.getNickname())
                .nombre(jugador.getNombre())
                .apellido(jugador.getApellido())
                .email(jugador.getEmail())
                .edad(jugador.getEdad())
                .equiposIds(jugador.getEquipos().stream()
                        .map(EquipoEntity::getId)
                        .collect(Collectors.toSet()))
                .build();
    }
}
