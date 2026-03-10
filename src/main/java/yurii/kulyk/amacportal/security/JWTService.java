package yurii.kulyk.amacportal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.enterprise.context.ApplicationScoped;
import yurii.kulyk.amacportal.entity.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@ApplicationScoped
public class JWTService {

    private final String secret = "U2IixOb4LIdOne3Bz7V9BIjTtSX/Aj4IRf5+/1kFERE=";

    public String generate(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("role", user.getRoles())
                .claim("tenant", user.getTenant().getId().toString())
                .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Claims validate(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

