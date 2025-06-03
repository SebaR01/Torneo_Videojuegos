package com.torneo.api.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Esta clase se encarga de generar y validar los tokens JWT.
 * El token contiene el nombre de usuario y una fecha de expiración.
 * Se usa para autenticar usuarios de forma segura en la API.
 */

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;


    // Genera un token JWT con el username y una expiración
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // Extrae el username (subject) desde el token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Verifica que el token sea válido y no esté vencido
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extrae toda la información (claims) del token
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
