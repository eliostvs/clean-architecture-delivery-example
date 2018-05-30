package com.delivery.presenter.rest.api.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetProductsByStoreIdentityUseCase;
import com.delivery.core.usecases.store.GetStoreByIdentityUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
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
@WebMvcTest(StoreController.class)
public class StoreControllerTest extends BaseControllerTest {

    @SpyBean
    private UseCaseExecutorImp useCaseExecutor;

    @MockBean
    private GetAllStoresUseCase getAllStoresUseCase;

    @MockBean
    private SearchStoresByNameUseCase searchStoresByNameUseCase;

    @MockBean
    private GetStoreByIdentityUseCase getStoreByIdentityUseCase;

    @MockBean
    private GetProductsByStoreIdentityUseCase getProductsByStoreIdentityUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Configuration
    @ComponentScan(basePackages = {"com.delivery.presenter.rest.api.store", "com.delivery.presenter.rest.api.common"})
    static class Config {
    }

    @Test
    public void getAllStoresReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();

        // and
        doReturn(Collections.singletonList(store))
                .when(getAllStoresUseCase)
                .execute(null);

        // when
        final RequestBuilder payload = asyncRequest("/Store");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(store.getName())))
                .andExpect(jsonPath("$[0].address", is(store.getAddress())))
                .andExpect(jsonPath("$[0].cousineId", is(store.getCousine().getId().getNumber().intValue())));
    }

    @Test
    public void getStoreByIdentityReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();

        // and
        doReturn(store)
                .when(getStoreByIdentityUseCase)
                .execute(eq(store.getId()));

        // when
        final RequestBuilder payload = asyncRequest("/Store/" + store.getId().getNumber());

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$.name", is(store.getName())))
                .andExpect(jsonPath("$.address", is(store.getAddress())))
                .andExpect(jsonPath("$.cousineId", is(store.getCousine().getId().getNumber().intValue())));
    }

    @Test
    public void getAllStoresNameMatchingReturnsStores() throws Exception {
        //given
        Store store = TestCoreEntityGenerator.randomStore();

        // and
        doReturn(Collections.singletonList(store))
                .when(searchStoresByNameUseCase)
                .execute("abc");

        // when
        final RequestBuilder payload = asyncRequest("/Store/search/abc");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(store.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(store.getName())))
                .andExpect(jsonPath("$[0].address", is(store.getAddress())))
                .andExpect(jsonPath("$[0].cousineId", is(store.getCousine().getId().getNumber().intValue())));
    }

    @Test
    public void getProductsByStoreIdReturnsStoreProducts() throws Exception {
        //given
        Product product = TestCoreEntityGenerator.randomProduct();
        Identity id = product.getStore().getId();

        // and
        doReturn(Collections.singletonList(product))
                .when(getProductsByStoreIdentityUseCase)
                .execute(eq(id));

        // when
        final RequestBuilder payload = asyncRequest("/Store/" + id.getNumber() + "/products");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(product.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(product.getName())))
                .andExpect(jsonPath("$[0].description", is(product.getDescription())))
                .andExpect(jsonPath("$[0].price", is(product.getPrice())))
                .andExpect(jsonPath("$[0].storeId", is(id.getNumber().intValue())));
    }

    @Test
    public void getProductsByStoreIdReturnsNotFound() throws Exception {
        //given
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doThrow(new NotFoundException("error"))
                .when(getProductsByStoreIdentityUseCase)
                .execute(eq(id));

        // when
        final RequestBuilder payload = asyncRequest("/Store/" + id.getNumber() + "/products");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.error", is("true")))
                .andExpect(jsonPath("$.message", is("Resource not found")));
    }
}