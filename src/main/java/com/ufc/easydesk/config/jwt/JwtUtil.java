package com.ufc.easydesk.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String SECRET_KEY = "your_secret_key"; // Substitua por uma chave secreta segura

    public String extractUsername(String token) {
        return extractClaim(token, "sub");
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, "exp", Date.class);
    }

    public <T> T extractClaim(String token, String claimKey, Class<T> claimType) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(claimKey).as(claimType);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public String extractClaim(String token, String claimKey) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(claimKey).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validade
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public Boolean validateToken(String token, String username) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .withSubject(username)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
