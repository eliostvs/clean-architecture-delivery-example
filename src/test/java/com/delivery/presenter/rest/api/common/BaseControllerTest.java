package com.delivery.presenter.rest.api.common;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

public abstract class BaseControllerTest {

    protected abstract MockMvc getMockMvc();

    protected RequestBuilder asyncDeleteRequest(String url, String token) throws Exception {
        return methodRequestBuilder(token, delete(url));
    }

    protected RequestBuilder asyncGetRequest(String url, String token) throws Exception {
        return methodRequestBuilder(token, get(url));
    }

    protected RequestBuilder asyncGetRequest(String url) throws Exception {
        return asyncDispatch(getMockMvc().perform(get(url))
                .andExpect(request().asyncStarted())
                .andReturn());
    }

    private RequestBuilder methodRequestBuilder(String token, MockHttpServletRequestBuilder method) throws Exception {
        return asyncDispatch(getMockMvc().perform(method.header("Authorization", "Bearer " + token))
                .andExpect(request().asyncStarted())
                .andReturn());
    }

    protected RequestBuilder asyncPostRequest(String url, String payload, String token) throws Exception {
        // @formatter:off
        MockHttpServletRequestBuilder content = post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(payload)
                .header("Authorization", "Bearer " + token);

        return asyncDispatch(
                getMockMvc().perform(content)
                .andExpect(request().asyncStarted())
                .andReturn());
        // @formatter:on
    }

    protected RequestBuilder asyncPostRequest(String url, String payload) throws Exception {
        // @formatter:off
        return asyncDispatch(
                getMockMvc().perform(
                        post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                .andExpect(request().asyncStarted())
                .andReturn());
        // @formatter:on
    }
}
