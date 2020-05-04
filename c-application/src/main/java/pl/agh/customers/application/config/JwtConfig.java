package pl.agh.customers.application.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${security.jwt.uri:/login/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:hbGciOiJIUzUxMiJ9}")
    private String secret;
}
