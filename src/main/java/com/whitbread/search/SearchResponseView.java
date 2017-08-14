package com.whitbread.search;

import java.util.List;

public class SearchResponseView {

    private String name;
    private List<Venue> recommendedVenues;

    public SearchResponseView(String name, List<Venue> recommendedVenues) {
        this.name = name;
        this.recommendedVenues = recommendedVenues;
    }

    public String getName() {
        return name;
    }

    public List<Venue> getRecommendedVenues() {
        return recommendedVenues;
    }
}
