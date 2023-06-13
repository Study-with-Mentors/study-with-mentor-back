package com.swm.studywithmentor.util;

import com.swm.studywithmentor.model.entity.user.User;
import com.swm.studywithmentor.model.exception.InvalidJwtException;
import com.swm.studywithmentor.model.exception.JwtExpiredException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.jwt-secret}")
    private String JWT_SECRET;
    private SecretKey JWT_SECRET_KEY;
    private final long JWT_EXPIRATION = 60000L * 60; // 1 hr

    @PostConstruct
    public void postConstruct() {
        JWT_SECRET_KEY = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("uid", ((User)userDetails).getId())
                .claim("rol", ((User)userDetails).getRole())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(JWT_SECRET_KEY)
                .compact();
    }

    public String getEmailFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Validate the token and throw an exception if it is not valid.
     *
     * @param token the token to validate
     * @throws InvalidJwtException if the token is not valid
     * @throws JwtExpiredException if the token is expired
     */
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(JWT_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new JwtExpiredException(token, ex);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidJwtException(token, ex);
        }
    }
}
