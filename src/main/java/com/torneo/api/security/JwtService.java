package com.torneo.api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Servicio que maneja la generación y validación de tokens JWT para la autenticación de usuarios.
 * Utiliza firma HMAC-SHA256 con una clave segura. Permite:
 *
 * - Crear tokens con un usuario autenticado.
 * - Validar tokens y verificar su vigencia.
 * - Extraer información del token, como el nombre de usuario (subject).
 *
 * Inspirado en la implementación sugerida por el profesor, incluye prácticas de seguridad recomendadas.
 */
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Genera un token JWT para un usuario autenticado
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // El "dueño" del token (subject)
                .claim("roles", userDetails.getAuthorities()) // Podés incluir roles u otros claims
                .setIssuedAt(new Date()) // Fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Firma segura
                .compact();
    }

    // Extrae el username desde el token (campo "sub")
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Verifica si el token es válido para el usuario indicado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Verifica si el token expiró
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extrae todos los claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Convierte la clave secreta en un objeto Key seguro
    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
