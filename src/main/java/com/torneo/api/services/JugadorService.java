package com.torneo.api.services;

import com.torneo.api.entities.JugadorEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.JugadorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class JugadorService {
    private final JugadorRepository jugadorRepository;

    public JugadorEntity crearJugador(JugadorEntity jugadorEntity){
        return jugadorRepository.save(jugadorEntity);
    }
    public void eliminarJugador(Integer id){
        jugadorRepository.deleteById(id);
    }
    public List<JugadorEntity> listarJugadores() {
        return jugadorRepository.findAll();
    }

    public JugadorEntity buscarJugadorPorId(Integer id){
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Jugador con ID " + id + " no encontrado")) ;
    }
    
    public void actualizarJugador(JugadorEntity jugadorEntity){
        jugadorRepository.save(jugadorEntity);
    }

    public List<JugadorEntity> listarJugadoresPorEquipo(Integer equipoId) {
        return jugadorRepository.findJugadoresByEquipoId(equipoId);
    }

    public List<JugadorEntity> listarJugadoresPorTorneo(Integer torneoId) {
        return jugadorRepository.findJugadoresByTorneoId(torneoId);
    }

}
    