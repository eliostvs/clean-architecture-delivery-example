package com.delivery.presenter.rest.api.cousine;

import com.delivery.core.TestCoreEntityGenerator;
import com.delivery.core.domain.Cousine;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetCousineByIdentityUserCase;
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
import org.springframework.test.web.servlet.MvcResult;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
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

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    @ComponentScan("com.delivery.presenter.rest.api")
    static class Config {
    }

    @Test
    public void returnsNotFoundWhenCousineIsNotFound() throws Exception {
        Identity id = TestCoreEntityGenerator.randomIdentity();

        doThrow(new NotFoundException("error"))
                .when(getCousineByIdentityUserCase)
                .execute(eq(id));

        mockMvc.perform(asyncDispatch(getMvcResult("/Cousine/" + id.getNumber())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("true")))
                .andExpect(jsonPath("$.message", is("Resource not found")));
    }

    @Test
    public void returnsCousineWhenCousineIsFoundById() throws Exception {
        Cousine cousine = TestCoreEntityGenerator.randomCousine();
        final Identity id = cousine.getId();

        doReturn(cousine)
                .when(getCousineByIdentityUserCase)
                .execute(eq(id));

        mockMvc.perform(asyncDispatch(getMvcResult("/Cousine/" + id.getNumber())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id.getNumber().intValue())))
                .andExpect(jsonPath("$.name", is(cousine.getName())));
    }

    @Test
    public void returnsAllCousines() throws Exception {
        List<Cousine> cousines = TestCoreEntityGenerator.randomCousines();
        Cousine firstCousine = cousines.get(0);

        doReturn(cousines)
                .when(getAllCousinesUseCase)
                .execute(null);

        mockMvc.perform(asyncDispatch(getMvcResult("/Cousine")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(cousines.size())))
                .andExpect(jsonPath("$[0].id", is(firstCousine.getId().getNumber().intValue())))
                .andExpect(jsonPath("$[0].name", is(firstCousine.getName())));
    }

    private MvcResult getMvcResult(String url) throws Exception {
        return mockMvc.perform(get(url))
                .andExpect(request().asyncStarted())
                .andReturn();
    }
}