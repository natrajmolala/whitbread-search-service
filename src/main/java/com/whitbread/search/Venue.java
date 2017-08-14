package com.whitbread.search;

import java.io.Serializable;

public class Venue implements Serializable {

    private String name;

    public Venue(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
