package com.whitbread.search;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public String search() {
        return "Success";
    }
}
