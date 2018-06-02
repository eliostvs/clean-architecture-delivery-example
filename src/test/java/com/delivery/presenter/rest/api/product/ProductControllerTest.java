package com.delivery.presenter.rest.api.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.core.usecases.product.GetProductByIdUseCase;
import com.delivery.core.usecases.product.SearchProductsByNameOrDescriptionUseCase;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {"com.delivery.presenter.rest.api.product", "com.delivery.presenter.rest.api.common"})
    static class Config {
    }

    @MockBean
    private GetProductByIdUseCase getProductByIdUseCase;

    @MockBean
    private GetAllProductsUseCase getAllProductsUseCase;

    @MockBean
    private SearchProductsByNameOrDescriptionUseCase searchProductsByNameOrDescriptionUseCase;

    @SpyBean
    private UseCaseExecutorImp useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void getProductByIdentityReturnsNotFound() throws Exception {
        // given
        Identity id = TestCoreEntityGenerator.randomId();

        // and
        doThrow(new NotFoundException("Error"))
                .when(getProductByIdUseCase)
                .execute(eq(id));

        // when
        RequestBuilder payload = asyncRequest("/Product/" + id.getNumber());

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void getByMatchingNameReturnsProducts() throws Exception {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Store store = product.getStore();

        // and
        doReturn(Collections.singletonList(product))
                .when(searchProductsByNameOrDescriptionUseCase)
                .execute(eq("abc"));

        // when
        RequestBuilder payload = asyncRequest("/Product/search/abc");

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

    @Test
    public void getProductByIdentityReturnsOk() throws Exception {
        // given
        Product product = TestCoreEntityGenerator.randomProduct();
        Store store = product.getStore();

        // and
        doReturn(product)
                .when(getProductByIdUseCase)
                .execute(eq(product.getId()));

        // when
        RequestBuilder payload = asyncRequest("/Product/" + product.getId().getNumber());

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(product.getId().getNumber().intValue())))
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())))
                .andExpect(jsonPath("$.storeId", is(store.getId().getNumber().intValue())));

    }
}