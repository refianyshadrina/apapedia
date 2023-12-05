package com.apapedia.catalog.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.UUID;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);


    // @Value("${apapedia.app.jwtSecret}")
    private static final String jwtSecret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // @Value("${apapedia.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // public String extractUsername(String token) {
    //     return extractClaim(token, Claims::getSubject);
    // }

    // public Date extractExpiration(String token) {
    //     return extractClaim(token, Claims::getExpiration);
    // }

    // public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    //     final Claims claims = extractAllClaims(token);
    //     return claimsResolver.apply(claims);
    // }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // private Boolean isTokenExpired(String token) {
    //     return extractExpiration(token).before(new Date());
    // }

    public boolean validateJwtToken(String authToken) {
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    // public Boolean validateToken(String token, UserDetails userDetails) {
    //     final String username = extractUsername(token);
    //     return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    // }




    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public UUID getIdFromJwtToken(String token) {
        Claims claims =  Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.get("userId", String.class));
    }
}

