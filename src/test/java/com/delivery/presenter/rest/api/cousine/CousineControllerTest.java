package com.delivery.presenter.rest.api.cousine;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetCousineByIdentityUserCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.presenter.UseCaseExecutorImp;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CousineController.class)
public class CousineControllerTest {

    @SpyBean
    private UseCaseExecutorImp useCaseExecutor;

    @MockBean
    private GetCousineByIdentityUserCase getCousineByIdentityUserCase;

    @MockBean
    private GetAllCousinesUseCase getAllCousinesUseCase;

    @MockBean
    private SearchCousineByNameUseCase searchCousineByNameUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    @ComponentScan("com.delivery.presenter.rest.api")
    static class Config {
    }

    @Test
    public void getCousineByIdReturnsNotFound() throws Exception {
        // given
        Identity id = TestCoreEntityGenerator.randomIdentity();

        // and
        doThrow(new NotFoundException("error"))
                .when(getCousineByIdentityUserCase)
                .execute(eq(id));

        // when
        final RequestBuilder payload = asyncRequest("/Cousine/" + id.getNumber());

        // then
        mockMvc.perform(payload)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("true")))
                .andExpect(jsonPath("$.message", is("Resource not found")));
    }

    @Test
    public void getCousineByIdReturnsOk() throws Exception {
        // given
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        final Identity id = cousine.getId();

        // and
        doReturn(cousine)
                .when(getCousineByIdentityUserCase)
                .execute(eq(id));

        // when
        final RequestBuilder payload = asyncRequest("/Cousine/" + id.getNumber());

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id.getNumber().intValue())))
                .andExpect(jsonPath("$.name", is(cousine.getName())));
    }

    @Test
    public void getAllCousinesReturnsOk() throws Exception {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        Cousine firstCousine = cousines.get(0);

        // and
        doReturn(cousines)
                .when(getAllCousinesUseCase)
                .execute(null);

        // when
        final RequestBuilder payload = asyncRequest("/Cousine");

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }

    @Test
    public void searchCousineByNameReturnsOk() throws Exception {
        // given
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        Cousine firstCousine = cousines.get(0);
        String text = "abc";

        // and
        doReturn(cousines)
                .when(searchCousineByNameUseCase)
                .execute(text);
        // when
        final RequestBuilder payload = asyncRequest("/Cousine/search/" + text);

        // then
        mockMvc.perform(payload)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }

    private RequestBuilder asyncRequest(String url) throws Exception {
        return asyncDispatch(mockMvc.perform(get(url)).andExpect(MockMvcResultMatchers.request().asyncStarted()).andReturn());
    }
}