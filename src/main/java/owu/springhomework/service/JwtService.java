package owu.springhomework.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey key;

    @PostConstruct
    public void setupKey() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UserDetails userDetails, long expSec) {
        List<String> roles = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .claims(Map.of("roles", roles))
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expSec * 1000 ))
                .signWith(key)
//                .addClaims(Map.of("roles", roles))
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expSec * 1000 ))
//                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails, long expSec) {
        return Jwts.builder()
                .claim("type", "refresh")
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expSec * 1000 ))
                .signWith(key)
//                .addClaims(Map.of("type", "refresh"))
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expSec * 1000 ))
//                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isAccessTokenExpired(String token) {
        return resolveClaim(token, Claims::getExpiration).before(new Date());
    }

    public boolean isRefreshTokenExpired(String token) {
        return resolveClaim(token, claims -> Objects.equals(claims.get("type", String.class), "refresh"));
    }

    public String extractUsername(String token) {
        return resolveClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return resolveClaim(token, Claims::getExpiration);
    }

    private <T> T resolveClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return resolver.apply(claims);
    }
}
