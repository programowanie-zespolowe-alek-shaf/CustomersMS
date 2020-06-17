package pl.agh.customers.application.rest.token;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.agh.customers.application.config.JwtConfig;

import static java.util.Collections.singletonList;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final JwtConfig jwtConfig;
    private final TokenHolder tokenHolder;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(singletonList(new HeaderRequestInterceptor(jwtConfig, tokenHolder)));
        return restTemplate;
    }
}
