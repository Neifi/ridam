package es.neifi.Ridam.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import es.neifi.Ridam.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;

@Log
@Component

public class JwtProvider {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE = "JWT";


    //TODO save secret in git repo or in path variable
    private final String jwtSecret  = "5v8y/B?E(H+KbPeShVmYq3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r4u7x!A%D*G-KaPdSgVkYp3s6v9y/B?E(H+MbQeThWmZq4t7w!z%C&F)J@NcRfUjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B&E)H@MbQeThWmZq4t7w!z%C*F-JaNdRfUjXn2r5u8x/A?D(G+KbPeShVkYp3s6v9y$B&E)H@McQfTjWnZq4t7w!z%C*F-JaNdRgUkXp2s5u8x/A?D(G+KbPeShVmYq3t6w9y$B&E)H@McQfTjWnZr4u7x!A%C*F-JaNdRgUkXp2s5v8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C&F)J@NcRfUjXnZr4u7x!A%D*G-KaPdSgVkYp3";
    @Value("${jwt.token-expiration: 3600}")
    private int jwtDurationTokenSecs;

    private  final Key secret = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    private  final byte[] secretBytes = secret.getEncoded();


    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDurationTokenSecs * 1000));

        return Jwts.builder()
                .setHeaderParam("typ", TOKEN_TYPE).setSubject(user.get_id().toString())
                .setIssuedAt(new Date())
                .setExpiration(tokenExpirationDate)
                .claim("fullname", user.getUsername())
                .signWith(secret,SignatureAlgorithm.HS512)
                .claim("roles", user.getRol().toString()).compact();
    }


    public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


    public boolean validateToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.info("Error en la firma del token JWT: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.info("Token malformado: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.info("El token ha expirado: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.info("Token JWT no soportado: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims vacío");
        }
        return false;

    }
}