package com.whitbread.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class.getName());

    @RequestMapping(value = "/places/search", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String search(@RequestParam("name") String name) {
        LOGGER.info("Searching for popular places near {}", name);
        return "Success";
    }
}
