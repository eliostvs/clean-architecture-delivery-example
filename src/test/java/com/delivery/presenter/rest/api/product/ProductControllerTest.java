package com.delivery.presenter.rest.api.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.core.usecases.product.GetProductUseCase;
import com.delivery.core.usecases.product.SearchProductsByNameOrDescriptionUseCase;
import com.delivery.presenter.rest.api.common.BaseControllerTest;
import com.delivery.presenter.usecases.UseCaseExecutorImpl;
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

import static java.util.Collections.singletonList;
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
    private GetProductUseCase getProductUseCase;

    @MockBean
    private GetAllProductsUseCase getAllProductsUseCase;

    @MockBean
    private SearchProductsByNameOrDescriptionUseCase searchProductsByNameOrDescriptionUseCase;

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

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
        GetProductUseCase.InputValues input = new GetProductUseCase.InputValues(id);

        // and
        doThrow(new NotFoundException("Error"))
                .when(getProductUseCase)
                .execute(eq(input));

        // when
        RequestBuilder payload = asyncGetRequest("/Product/" + id.getNumber());

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
        SearchProductsByNameOrDescriptionUseCase.InputValues input =
                new SearchProductsByNameOrDescriptionUseCase.InputValues("abc");
        SearchProductsByNameOrDescriptionUseCase.OutputValues output = new SearchProductsByNameOrDescriptionUseCase.OutputValues(singletonList(product));

        // and
        doReturn(output)
                .when(searchProductsByNameOrDescriptionUseCase)
                .execute(eq(input));

        // when
        RequestBuilder payload = asyncGetRequest("/Product/search/abc");

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

        GetAllProductsUseCase.InputValues input = new GetAllProductsUseCase.InputValues();
        GetAllProductsUseCase.OutputValues output =
                new GetAllProductsUseCase.OutputValues(singletonList(product));

        // and
        doReturn(output)
                .when(getAllProductsUseCase)
                .execute(input);

        // when
        RequestBuilder payload = asyncGetRequest("/Product");

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
        GetProductUseCase.InputValues input = new GetProductUseCase.InputValues(product.getId());
        GetProductUseCase.OutputValues output = new GetProductUseCase.OutputValues(product);

        // and
        doReturn(output)
                .when(getProductUseCase)
                .execute(eq(input));

        // when
        RequestBuilder payload = asyncGetRequest("/Product/" + product.getId().getNumber());

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