package com.delivery.presenter.rest.api.store;

import com.delivery.core.domain.Store;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest {

    @SpyBean
    private UseCaseExecutorImp useCaseExecutor;

    @MockBean
    private GetAllStoresUseCase getAllStoresUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    @ComponentScan("com.delivery.presenter.rest.api.store")
    static class Config {
    }

    @Test
    public void getAllStoresReturnsOk() throws Exception {
        // given
        List<Store> stores = TestCoreEntityGenerator.randomStores();
        Store firstStore = stores.get(0);

        // and
        doReturn(stores)
                .when(getAllStoresUseCase)
                .execute(null);

        // when
        final RequestBuilder payload = asyncRequest("/Store");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(stores.size())))
                .andExpect(jsonPath("$[0].id", is(firstStore.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstStore.getName())))
                .andExpect(jsonPath("$[0].address", is(firstStore.getAddress())))
                .andExpect(jsonPath("$[0].cousineId", is(firstStore.getCousine().getId().getNumber().intValue())));
    }

    private RequestBuilder asyncRequest(String url) throws Exception {
        return asyncDispatch(mockMvc.perform(get(url)).andExpect(MockMvcResultMatchers.request().asyncStarted()).andReturn());
    }
}