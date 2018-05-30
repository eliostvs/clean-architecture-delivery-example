package com.delivery.presenter.rest.api.common;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

public abstract class BaseControllerTest {

    protected abstract MockMvc getMockMvc();

    protected RequestBuilder asyncRequest(String url) throws Exception {
        return asyncDispatch(getMockMvc().perform(get(url))
                .andExpect(request().asyncStarted())
                .andReturn());
    }
}
