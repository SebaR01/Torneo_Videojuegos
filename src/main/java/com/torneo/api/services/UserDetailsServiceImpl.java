package com.torneo.api.services;

import com.torneo.api.models.User;
import com.torneo.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio que implementa `UserDetailsService` para integrarse con Spring Security.
 * Permite cargar un usuario desde la base de datos a partir de su username.
 *
 * Este servicio es utilizado automáticamente por Spring Security en el proceso
 * de autenticación (por ejemplo, en el filtro JWT o al usar `AuthenticationManager`).
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Carga un usuario por su username.
     * Lanza una excepción si no existe.
     *
     * @param username el nombre de usuario (puede ser también un email si lo adaptás)
     * @return un objeto User que implementa UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
