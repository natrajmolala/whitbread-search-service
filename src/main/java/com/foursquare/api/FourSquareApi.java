package com.foursquare.api;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class FourSquareApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(FourSquareApi.class.getName());

    @Value("${fourSquare.api.venuesUri}")
    private String venuesUri;

    @Value("${fourSquare.api.oauthToken}")
    private String oauthToken;

    @Value("${fourSquare.api.version}")
    private String version;

    private RestTemplateBuilder restClientBuilder;

    public FourSquareApi(RestTemplateBuilder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    public String venuesExplore(String locationName) throws FourSquareApiAccessException {
        URI uri = getUriWithParams(locationName);
        HttpEntity<String> response;
        try {
            response = restClientBuilder.getRestTemplate().exchange(uri, HttpMethod.GET,
                    restClientBuilder.getHttpEntity(), String.class);
        } catch (Exception e) {
            LOGGER.error("Error!! accessing FourSquare endpoint {}", uri, e);
            throw new FourSquareApiAccessException(e.getMessage(), e);
        }

        return response.getBody();
    }

    public String getExploreUri() {
        return venuesUri + "/explore";
    }

    private URI getUriWithParams(String locationName) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap();
        params.add("near", locationName);
        params.add("oauth_token", oauthToken);
        params.add("v", version);
        return restClientBuilder.getUri(getExploreUri(), params);
    }
}
