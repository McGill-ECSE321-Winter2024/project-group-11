package ca.mcgill.ecse321.SportsCenterApp.services.utilities;

import java.util.Base64;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Date;

/** Code inspired by this source:
 * https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/
 */
@Component
public class TokenProvider {

    @Value("${secretKey}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();
        // valid for 24 hrs
        long validityInMilliseconds = 3600000 * 24;
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

}