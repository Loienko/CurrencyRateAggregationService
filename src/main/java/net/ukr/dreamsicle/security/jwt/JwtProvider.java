package net.ukr.dreamsicle.security.jwt;

import io.jsonwebtoken.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.exception.JwtAuthenticationException;
import net.ukr.dreamsicle.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

import static net.ukr.dreamsicle.util.Constants.BEARER;
import static net.ukr.dreamsicle.util.Constants.JWT_TOKEN_IS_EXPIRED_OR_INVALID;

/**
 * Util class that provides methods for create, resolve, etc. of JWT token.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Slf4j
@Component
public class JwtProvider {

    @Value("${app.jwtSecret}")
    private String secret;

    @Value("${app.jwtExpiration}")
    private int validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + validityInMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    String resolveToken(@NonNull HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.replace(BEARER, "");
        }
        return null;
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException | JwtAuthenticationException e) {
            log.info(JWT_TOKEN_IS_EXPIRED_OR_INVALID);
        }
        return false;
    }
}
