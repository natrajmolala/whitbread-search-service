package com.whitbread.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Test;

public class SearchServiceTest {

    private SearchService searchService;

    @Before
    public void setup() {
        searchService = new SearchService();
    }

    @Test
    public void shouldReturnResponseViewWithSearchedName() {
        String placeToSearch = "Harrow";
        SearchResponseView responseView = searchService.getPlacesByName(placeToSearch);
        assertThat(responseView, is(notNullValue()));
        assertThat(responseView.getName(), is(placeToSearch));
    }

    @Test
    public void shouldReturnResponseViewWithSearchedNameAndRecommendedVenues() {
        String placeToSearch = "Harrow";
        SearchResponseView responseView = searchService.getPlacesByName(placeToSearch);
        assertThat(responseView, is(notNullValue()));
        assertThat(responseView.getName(), is(placeToSearch));
        assertThat(responseView.getRecommendedVenues().size(), is(2));
    }

}