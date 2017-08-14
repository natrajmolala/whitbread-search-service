package com.foursquare.api;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FourSquareApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(FourSquareApi.class.getName());

    public String venuesExplore(String locationName) {
        URI uri = getUriWithParams(locationName);
        HttpEntity<String> response;
        try {
            response = new RestTemplate().exchange(uri, HttpMethod.GET, getHttpEntity(), String.class);
        } catch (Exception e) {
            LOGGER.error("Error!! accessing FourSquare endpoint {}", uri, e);
            throw e;
        }
        return response.getBody();
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity(headers);
    }

    private URI getUriWithParams(String locationName) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.foursquare.com/v2/venues/explore")
                .queryParam("near", locationName)
                .queryParam("oauth_token", "HDQQIKCNWX0PDP4LG2C5FWRQNN3O20WQEOTOYULRP1XMM5LR")
                .queryParam("v", "20170813");

        return builder.build().encode().toUri();
    }
}
