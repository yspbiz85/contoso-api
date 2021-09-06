package com.contoso.api.utils;

import com.contoso.api.entities.UserAuth;
import com.contoso.api.model.TokenDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LogManager.getLogger(JwtTokenUtil.class);

    @Value("${app.token.secret}")
    private String appTokenSecret;

    @Value("${app.token.expiration}")
    private Integer appTokenExpiration;

    @Value("${app.token.issuer}")
    private String appTokenIssuer;

    @Value("${app.token.audience}")
    private String appTokenAudience;

    public TokenDetails parseJwtToken(Authentication authentication) {
        List<Object> authenticationDetails = (List<Object>) authentication.getDetails();
        String jwtToken = (String) authenticationDetails.get(0);
        return this.parseJwtToken(jwtToken);
    }

    public TokenDetails parseJwtToken(String token) {
        Claims claim = this.getClaims(token);
        return TokenDetails.builder()
                .userId(UUID.fromString((String) claim.get("userId")))
                .userName(claim.getSubject())
                .email((String) claim.get("email"))
                .isTokenExpired(claim.getExpiration().before(Date.from(Instant.now())))
                .build();
    }

    public String extractJwtTokenFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return headerAuth;
    }

    private Claims getClaims(String token){
        SecretKey secretKey = Keys.hmacShaKeyFor(appTokenSecret.getBytes(StandardCharsets.UTF_8));
        Claims claim = null;
        try {
            claim = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (SignatureException signatureException) {
            logger.error("Invalid JWT signature: {}", signatureException.getMessage());
        } catch (MalformedJwtException malformedJwtException) {
            logger.error("Invalid JWT token: {}", malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            logger.error("JWT token is expired: {}", expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", unsupportedJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("JWT claims string is empty: {}", illegalArgumentException.getMessage());
        }
        return claim;
    }

    public String generateToken(UserAuth userAuth){
        SecretKey secretKey = Keys.hmacShaKeyFor(appTokenSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setAudience(appTokenAudience)
                .claim("userId",userAuth.getUserId())
                .claim("email",userAuth.getEmail())
                .setSubject(userAuth.getUserName())
                .setIssuer(appTokenIssuer).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(appTokenExpiration, ChronoUnit.MINUTES)))
                .setId(UUID.randomUUID().toString())
                .signWith(secretKey).compact();
    }
}
