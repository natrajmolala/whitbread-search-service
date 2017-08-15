package com.whitbread.search;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.whitbread.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SearchControllerTest {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnHttpResponseOKWhenSearchEndpointAccessed() throws Exception {

        mockMvc.perform(get("/places/search?name=Harrow"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(notNullValue()))
                .andReturn();
    }

    @Test
    public void shouldReturnHttpNotFoundWhenInvalidEndpointAccessed() throws Exception {
        mockMvc.perform(get("/places/search/INVALID?name=Harrow"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void shouldReturnHttpBadRequestWhenInvalidRequestParamUsed() throws Exception {
        mockMvc.perform(get("/places/search?INVALID=Harrow"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void shouldReturnHttpMethodNotAllowedWhenPostUsed() throws Exception {
        mockMvc.perform(post("/places/search?name=Harrow"))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    @Test
    public void shouldReturnSearchResponseWithRecommendedPlacesNearSearchedPlace() throws Exception {
        String expectedResult = "{\"name\":\"Harrow\",\"recommendedVenues\":[{\"name\":\"Playgolf London\"},{\"name\":\"Kebab Land\"}]}";
        mockMvc.perform(get("/places/search?name=Harrow"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(content().string(containsString("Harrow")))
                .andReturn();
    }

}
