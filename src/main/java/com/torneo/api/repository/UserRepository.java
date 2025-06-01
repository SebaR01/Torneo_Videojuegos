package com.torneo.api.repository;

import com.torneo.api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Esta interfaz se conecta con la base de datos para acceder a usuarios.
 * Permite buscar un usuario por su nombre de usuario, entre otras cosas.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
