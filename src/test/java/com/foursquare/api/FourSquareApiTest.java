package com.foursquare.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.net.URI;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class FourSquareApiTest {

    private FourSquareApi fourSquareApi;
    private MockRestServiceServer mockServer;
    private String validUri = "https://api.foursquare.com/v2/venues/explore?near=Harrow&oauth_token=HDQQIKCNWX0PDP4LG2C5FWRQNN3O20WQEOTOYULRP1XMM5LR&v=20170813";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Before
    public void setUp() {
        fourSquareApi = new FourSquareApi(restTemplateBuilder);
    }

    @Test
    public void shouldAccessVenueExploreEndpoint() throws Exception {
        when(restTemplateBuilder.getRestTemplate()).thenReturn(new RestTemplate());
        when(restTemplateBuilder.getUri(anyString(), any())).thenReturn(new URI(validUri));

        mockServer = MockRestServiceServer.createServer(restTemplateBuilder.getRestTemplate());
        mockServer.expect(requestTo(validUri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("resultSuccess", MediaType.APPLICATION_JSON));

        String result = fourSquareApi.venuesExplore("Harrow");
        mockServer.verify();
        assertThat(result, containsString("resultSuccess"));
    }

    @Test
    public void shouldReceiveBadRequestWhenMissingParameters() throws Exception {

        thrown.expect(FourSquareApiAccessException.class);
        thrown.expectMessage("400 Bad Request");

        when(restTemplateBuilder.getRestTemplate()).thenReturn(new RestTemplate());
        String invalidUri = "https://api.foursquare.com/v2/venues/explore?near=Harrow&oauth_token=INVALID_TOKEN&v=000";
        when(restTemplateBuilder.getUri(anyString(), any())).thenReturn(new URI(invalidUri));

        mockServer = MockRestServiceServer.createServer(restTemplateBuilder.getRestTemplate());
        mockServer.expect(requestTo(invalidUri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        fourSquareApi.venuesExplore("Harrow");
    }

    @Test
    public void shouldReceiveServerError() throws Exception {

        thrown.expect(FourSquareApiAccessException.class);
        thrown.expectMessage("500 Internal Server Error");

        when(restTemplateBuilder.getRestTemplate()).thenReturn(new RestTemplate());
        when(restTemplateBuilder.getUri(anyString(), any())).thenReturn(new URI(validUri));

        mockServer = MockRestServiceServer.createServer(restTemplateBuilder.getRestTemplate());
        mockServer.expect(requestTo(validUri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        fourSquareApi.venuesExplore("Harrow");
    }
}