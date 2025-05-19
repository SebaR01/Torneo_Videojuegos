package com.torneo.api.repository;


import com.torneo.api.models.JugadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository <JugadorEntity, Integer> {

    @Query("SELECT j FROM JugadorEntity j JOIN j.equipos e WHERE e.id = :equipoId")
    List<JugadorEntity> findJugadoresByEquipoId(@Param("equipoId") Integer equipoId);

    @Query("SELECT DISTINCT j FROM JugadorEntity j JOIN j.equipos e WHERE e.torneoId = :torneoId")
    List<JugadorEntity> findJugadoresByTorneoId(@Param("torneoId") Integer torneoId);


}