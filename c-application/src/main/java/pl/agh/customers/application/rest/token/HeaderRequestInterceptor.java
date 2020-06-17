package pl.agh.customers.application.rest.token;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import pl.agh.customers.application.config.JwtConfig;

import java.io.IOException;

@RequiredArgsConstructor
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    private final JwtConfig jwtConfig;
    private final TokenHolder tokenHolder;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String token = tokenHolder.getToken();
        if (token != null) {
            request.getHeaders().set(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
        }
        return execution.execute(request, body);
    }
}
