package controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public final class GlobalVariables {
    public static final RestTemplate restTemplate = new RestTemplate();
    public static final HttpHeaders headers = new HttpHeaders();
    public static final HttpEntity<Object> headersEntity = new HttpEntity<>(headers);
}
