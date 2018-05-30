package com.delivery.presenter.rest.api.customer;

import com.delivery.core.domain.EmailAlreadyUsedException;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.customer.CreateCustomerInput;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.core.usecases.customer.Customer;
import com.delivery.presenter.rest.api.common.BaseControllerTest;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import com.delivery.presenter.usecases.UseCaseExecutorImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerTest extends BaseControllerTest {

    @Configuration
    @ComponentScan(basePackages = {"com.delivery.presenter.rest.api.customer", "com.delivery.presenter.reset.api.common"})
    static class Config {
    }

    private JacksonTester<SignUpRequest> signInJson;

    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;

    @MockBean
    private CreateCustomerInputMapper createCustomerInputMapper;

    @SpyBean
    private UseCaseExecutorImp useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void signUpReturnsBadRequestWhenEmailIsAlreadyBeenUsed() throws Exception {
        // given
        final SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        String payload = signInJson.write(signUpRequest).getJson();
        CreateCustomerInput createCustomerInput = new CreateCustomerInput(null, null, null, null);

        // and
        doReturn(createCustomerInput)
                .when(createCustomerInputMapper)
                .map(eq(signUpRequest));

        // and
        doThrow(new EmailAlreadyUsedException("error"))
                .when(createCustomerUseCase)
                .execute(createCustomerInput);
        // when
        RequestBuilder request = asyncRequest("/Customer", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("registered successfully")));
    }

    @Test
    public void signUpReturnsCreatedWhenIsANewCustomer() throws Exception {
        // given
        final SignUpRequest signUpRequest = new SignUpRequest("name", "email@email.com", "address", "password");
        String payload = signInJson.write(signUpRequest).getJson();
        Customer customer = TestCoreEntityGenerator.randomCustomer();
        CreateCustomerInput createCustomerInput = new CreateCustomerInput(
                customer.getName(), customer.getEmail(),
                customer.getAddress(), customer.getPassword());

        // and
        doReturn(createCustomerInput)
                .when(createCustomerInputMapper)
                .map(eq(signUpRequest));

        // and
        doReturn(customer)
                .when(createCustomerUseCase)
                .execute(eq(createCustomerInput));

        // when
        RequestBuilder request = asyncRequest("/Customer", payload);

        // then
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(header().string("location", "http://localhost/Customer/" + customer.getId().getNumber()))
                .andExpect(jsonPath("$.message", is("registered successfully")));
    }
}