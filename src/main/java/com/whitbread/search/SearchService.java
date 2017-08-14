package com.whitbread.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SearchService {

    public SearchResponseView getPlacesByName(String name) {
        List<Venue> recommendedVenue = new ArrayList<>();
        recommendedVenue.add(new Venue("Playgolf London"));
        recommendedVenue.add(new Venue("Kebab Land"));
        return new SearchResponseView(name, recommendedVenue);
    }
}
