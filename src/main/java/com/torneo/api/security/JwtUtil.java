package com.torneo.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "secreto123"; // Podés cambiarlo por una variable de entorno en producción

    /// Generar token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /// Obtener username desde el token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /// Verificar si el token expiró
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /// Validar token con username
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}

/*
Creamos una clase utilitaria llamada JwtUtil, ubicada dentro de un nuevo paquete llamado security, para mantener la parte de seguridad bien organizada.
Esta clase se encarga de generar y validar los tokens JWT que usaremos para autenticar a los usuarios en la API.
Cuando un usuario inicia sesión, se genera un token válido por 10 horas.
También permite extraer el nombre de usuario del token y verificar si está vencido.
El token se firma con una clave secreta (secreto123), que en producción se debería guardar como variable de entorno.
Esta clase será utilizada más adelante por el servicio de autenticación (AuthService) y el filtro de seguridad (JwtAuthenticationFilter) para proteger los endpoints.
 */