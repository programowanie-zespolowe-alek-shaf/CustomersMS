package pl.agh.customers.application.rest;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.agh.customers.application.rest.url.URLProvider;

@Component
@RequiredArgsConstructor
public class RestClient {

    private final RestTemplate restTemplate;
    private final URLProvider urlProvider;
    private final Log logger = LogFactory.getLog(getClass());

    public <T> T get(MicroService ms, String url, Class<T> type) {
        String baseURL = urlProvider.getBaseURL(ms);
        String fullUrl = baseURL + url;
        try {
            logger.info(String.format("START GET: MS=[%s] URL=[%s] TYPE=[%s]", ms, fullUrl, type.getName()));
            return restTemplate.getForObject(fullUrl, type);
        } finally {
            logger.info(String.format("END   GET: MS=[%s] URL=[%s] TYPE=[%s]", ms, fullUrl, type.getName()));
        }
    }

    public void put(MicroService ms, String url, JSONObject jsonObject) {
        String baseURL = urlProvider.getBaseURL(ms);
        String fullUrl = baseURL + url;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        try {
            logger.info(String.format("START PUT: MS=[%s] URL=[%s]", ms, fullUrl));
            restTemplate.put(fullUrl, request);
        } finally {
            logger.info(String.format("END   PUT: MS=[%s] URL=[%s]", ms, fullUrl));
        }
    }
}
