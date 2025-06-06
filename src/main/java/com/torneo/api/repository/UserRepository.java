package com.torneo.api.repository;

import com.torneo.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad `User`.
 * Permite realizar operaciones de acceso a datos relacionadas con usuarios.
 *
 * Extiende de `JpaRepository`, lo que provee métodos CRUD automáticos.
 * Además, define métodos personalizados para buscar usuarios por username
 * o por email, lo cual es útil para autenticación y validaciones únicas.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su username.
     * @param username el nombre de usuario
     * @return un Optional con el usuario encontrado o vacío si no existe
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su email.
     * @param email el email del usuario
     * @return un Optional con el usuario encontrado o vacío si no existe
     */
    Optional<User> findByEmail(String email);
}
