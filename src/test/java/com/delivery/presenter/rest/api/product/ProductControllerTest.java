package com.delivery.presenter.rest.api.product;

import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.presenter.rest.api.common.BaseControllerTest;
import com.delivery.presenter.usecases.UseCaseExecutorImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest extends BaseControllerTest {

    @SpyBean
    private UseCaseExecutorImp useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAllProductsUseCase getAllProductsUseCase;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Configuration
    @ComponentScan("com.delivery.presenter.rest.api.product")
    static class Config {
    }

    @Test
    public void getAllProductsReturnsProducts() throws Exception {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Store store = product.getStore();

        // and
        doReturn(Collections.singletonList(product))
                .when(getAllProductsUseCase)
                .execute(null);

        // when
        RequestBuilder payload = asyncRequest("/Product");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(product.getId().getNumber().intValue())))
                .andExpect(jsonPath("$.[0].name", is(product.getName())))
                .andExpect(jsonPath("$.[0].description", is(product.getDescription())))
                .andExpect(jsonPath("$.[0].price", is(product.getPrice())))
                .andExpect(jsonPath("$.[0].storeId", is(store.getId().getNumber().intValue())));
    }
}