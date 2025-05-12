package com.torneo.api.Repository;

import com.torneo.api.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
/*
Implementé la interfaz UserRepository, que extiende de JpaRepository para poder acceder a la base de datos de usuarios sin escribir código SQL.
Incluye métodos personalizados como:
findByUsername(String username): para buscar usuarios al momento del login.
existsByUsername(String username) y existsByEmail(String email): para validar que no se registren usuarios repetidos.
Esto nos va a permitir manejar el registro y la autenticación de manera sencilla usando Spring Data JPA.
 */