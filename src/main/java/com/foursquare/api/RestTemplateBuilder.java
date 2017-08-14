package com.foursquare.api;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class RestTemplateBuilder {

    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public RestTemplate getRestTemplate(int timeout) {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return new RestTemplate(clientHttpRequestFactory);
    }

    public HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity(headers);
    }

    public URI getUri(String baseUri, MultiValueMap<String, String> params) {
        return UriComponentsBuilder
                .fromHttpUrl(baseUri)
                .queryParams(params)
                .build().encode().toUri();
    }
}
