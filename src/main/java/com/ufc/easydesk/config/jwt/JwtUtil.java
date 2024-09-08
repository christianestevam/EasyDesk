package com.ufc.easydesk.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final String SECRET_KEY;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.SECRET_KEY = secretKey;
    }

    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("roles").asList(String.class);
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return JWT.create()
                .withSubject(username)
                .withClaim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())) // Deixe as roles como estão, sem adicionar "ROLE_"
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token válido por 10 horas
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }


    public Boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }
}
