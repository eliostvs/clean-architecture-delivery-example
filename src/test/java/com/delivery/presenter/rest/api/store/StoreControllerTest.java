package com.delivery.presenter.rest.api.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetProductsByStoreUseCase;
import com.delivery.core.usecases.store.GetStoreUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
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
@WebMvcTest(value = StoreController.class, secure = false)
public class StoreControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {"com.delivery.presenter.rest.api.store", "com.delivery.presenter.rest.api.common"})
    static class Config {
    }

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @MockBean
    private GetAllStoresUseCase getAllStoresUseCase;

    @MockBean
    private SearchStoresByNameUseCase searchStoresByNameUseCase;

    @MockBean
    private GetStoreUseCase getStoreUseCase;

    @MockBean
    private GetProductsByStoreUseCase getProductsByStoreUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void getAllStoresReturnsOk() throws Exception {
        // given
        Store store = TestCoreEntityGenerator.randomStore();
        GetAllStoresUseCase.InputValues input = new GetAllStoresUseCase.InputValues();
        GetAllStoresUseCase.OutputValues output = new GetAllStoresUseCase.OutputValues(singletonList(store));

        // and
        doReturn(output)
                .when(getAllStoresUseCase)
                .execute(input);

        // when
        final RequestBuilder payload = asyncGetRequest("/Store");

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
        GetStoreUseCase.OutputValues output = new GetStoreUseCase.OutputValues(store);
        GetStoreUseCase.InputValues input = new GetStoreUseCase.InputValues(store.getId());

        // and
        doReturn(output)
                .when(getStoreUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/Store/" + store.getId().getNumber());

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
        SearchStoresByNameUseCase.InputValues input = new SearchStoresByNameUseCase.InputValues("abc");
        SearchStoresByNameUseCase.OutputValues output =
                new SearchStoresByNameUseCase.OutputValues(singletonList(store));

        // and
        doReturn(output)
                .when(searchStoresByNameUseCase)
                .execute(input);

        // when
        final RequestBuilder payload = asyncGetRequest("/Store/search/abc");

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
        GetProductsByStoreUseCase.InputValues input = new GetProductsByStoreUseCase.InputValues(id);
        GetProductsByStoreUseCase.OutputValues output = new GetProductsByStoreUseCase.OutputValues(singletonList(product));

        // and
        doReturn(output)
                .when(getProductsByStoreUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/Store/" + id.getNumber() + "/products");

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
        Identity id = TestCoreEntityGenerator.randomId();
        GetProductsByStoreUseCase.InputValues input = new GetProductsByStoreUseCase.InputValues(id);

        // and
        doThrow(new NotFoundException("Error"))
                .when(getProductsByStoreUseCase)
                .execute(eq(input));

        // when
        final RequestBuilder payload = asyncGetRequest("/Store/" + id.getNumber() + "/products");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }
}