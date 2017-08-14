package com.foursquare.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

public class FourSquareApiTest {

    private FourSquareApi fourSquareApi;

    @Before
    public void setup() {
        fourSquareApi = new FourSquareApi();
    }

    @Test
    public void shouldAccessVenueExploreEndpoint() {
        String responseBody = fourSquareApi.venuesExplore("Harrow");
        assertThat(responseBody, is(notNullValue()));
    }
}