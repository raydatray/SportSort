package ca.mcgill.ecse321.sportsregistrationw24.utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TokenProvider {
  @Value("${secretKey}")
  private String salt;
  private SecretKey secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String username) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + (3600000 * 12));

    return Jwts.builder()
      .subject(username)
      .issuedAt(now)
      .expiration(expiry)
      .signWith(secretKey) //??
      .compact();
  }
}
