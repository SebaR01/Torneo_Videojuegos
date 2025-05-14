package com.torneo.api.services;

import com.torneo.api.entities.EquipoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repositories.EquipoRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class EquipoService{
    private final EquipoRepository equipoRepository;

    public EquipoEntity crearEquipo(EquipoEntity equipoEntity){//se tiene que verificar que el torneo este en estado valido
       return equipoRepository.save(equipoEntity);//solo organizador o jugador pueden crear
    }

    public void eliminarEquipo(Integer id){
        equipoRepository.deleteById(id);
    }

    public List<EquipoEntity> listarEquipos(){
        return equipoRepository.findAll();
    }

    public EquipoEntity buscarEquipoPorId(Integer id){
        return equipoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Equipo con ID " + id + " no encontrado")) ;
    }

    public List<EquipoEntity> filtrarEquiposPorTorneoId(Integer torneoId){
        List<EquipoEntity> equiposFiltrados = equipoRepository.findAll()
                .stream()
                .filter(e -> e.getTorneoId().equals(torneoId))
                .toList();
        return equiposFiltrados;
    }

    public void actualizarEquipo(EquipoEntity equipoEntity){//si el torneo esta en estado valido
        equipoRepository.save(equipoEntity);//solo organizador o admin pueden actualizar
    }
}


