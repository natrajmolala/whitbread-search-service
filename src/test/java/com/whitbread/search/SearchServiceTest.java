package com.whitbread.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import com.whitbread.foursquare.FourSquareApi;
import com.whitbread.foursquare.FourSquareApiAccessException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private FourSquareApi fourSquareApi;

    private static final String jsonResponse = "{\"response\": {\"groups\": [{\"type\": \"Recommended Places\", \"name\": \"recommended\", \"items\": [{\"venue\": {\"id\": \"4bd45bfaa8b3a593729a6b5f\", \"name\": \"Playgolf London\"}}, {\"venue\": {\"id\": \"4ad640c4f964a520170621e3\", \"name\": \"Kebab Land\"}}]}]}}";

    @Test
    public void shouldReturnResponseViewWithSearchedName() throws FourSquareApiAccessException {

        String placeToSearch = "Harrow";
        when(fourSquareApi.venuesExplore(placeToSearch)).thenReturn(jsonResponse);

        SearchResponseView responseView = searchService.getPlacesByName(placeToSearch);
        assertThat(responseView, is(notNullValue()));
        assertThat(responseView.getName(), is(placeToSearch));
    }

    @Test
    public void shouldReturnResponseViewWithSearchedNameAndRecommendedVenues() throws FourSquareApiAccessException {
        String placeToSearch = "Harrow";
        when(fourSquareApi.venuesExplore(placeToSearch)).thenReturn(jsonResponse);

        SearchResponseView responseView = searchService.getPlacesByName(placeToSearch);
        assertThat(responseView, is(notNullValue()));
        assertThat(responseView.getName(), is(placeToSearch));
        assertThat(responseView.getRecommendedVenues().size(), is(2));
    }

}