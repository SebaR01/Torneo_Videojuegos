package com.torneo.api.services;

import com.torneo.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Esta clase le indica a Spring Security cómo cargar un usuario desde la base de datos.
 * A partir del username, busca el usuario, y lo convierte en un UserDetails.
 * Esto es necesario para validar el login usando JWT.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Este método se llama automáticamente cuando Spring quiere autenticar a un usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.torneo.api.Models.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Construye un UserDetails con los datos de nuestro User
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name()) // Convierte el rol enum a un rol de Spring
                .build();
    }
}
