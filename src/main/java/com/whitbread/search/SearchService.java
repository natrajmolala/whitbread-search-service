package com.whitbread.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.whitbread.foursquare.FourSquareApi;
import com.whitbread.foursquare.FourSquareApiAccessException;
import net.minidev.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchService.class.getName());
    private static final String RECOMMENDED_VENUES = "$.response.groups[?(@.name == 'recommended')].items[*].venue.name";

    @Autowired
    private FourSquareApi fourSquareApi;

    public SearchResponseView getPlacesByName(String name) {
        List<Venue> recommendedVenue = new ArrayList<>();

        try {
            String response = fourSquareApi.venuesExplore(name);
            DocumentContext context = JsonPath.parse(response);
            JSONArray venueNames = context.read(RECOMMENDED_VENUES);
            IntStream.range(0, venueNames.size())
                    .forEach(i -> {
                        recommendedVenue.add(new Venue((String) venueNames.get(i)));
                    });
        } catch (FourSquareApiAccessException accessException) {
            LOGGER.error("Failed to search recommended venues near {}", name, accessException);
        }

        return new SearchResponseView(name, recommendedVenue);
    }
}
