package com.delivery.presenter.rest.api.order;

import com.delivery.TestEntityGenerator;
import com.delivery.core.domain.Customer;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.core.usecases.order.DeleteOrderUseCase;
import com.delivery.core.usecases.order.DeliveryOrderUseCase;
import com.delivery.core.usecases.order.GetCustomerOrderUseCase;
import com.delivery.core.usecases.order.GetOrderUseCase;
import com.delivery.core.usecases.order.PayOrderUseCase;
import com.delivery.presenter.config.WebSecurityConfig;
import com.delivery.presenter.rest.api.common.BaseControllerTest;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.usecases.UseCaseExecutorImpl;
import com.delivery.presenter.usecases.security.CustomUserDetailsService;
import com.delivery.presenter.usecases.security.JwtProvider;
import com.delivery.presenter.usecases.security.UserPrincipal;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
public class OrderControllerTest extends BaseControllerTest {

    private static final String TOKEN = "token";
    private JacksonTester<OrderRequest> createOrderJson;

    @Configuration
    @ComponentScan(basePackages = {
            "com.delivery.presenter.rest.api.order",
            "com.delivery.presenter.usecases.security",
            "com.delivery.presenter.rest.api.common"
    })
    @Import(WebSecurityConfig.class)
    static class Config {
    }

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @MockBean
    private GetOrderUseCase getOrderUseCase;

    @MockBean
    private GetCustomerOrderUseCase getCustomerOrderUseCase;

    @MockBean
    private DeleteOrderUseCase deleteOrderUseCase;

    @MockBean
    private PayOrderUseCase payOrderUseCase;

    @MockBean
    private DeliveryOrderUseCase deliveryOrderUseCase;

    @MockBean
    private JwtProvider jwtProvider;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @SpyBean
    private UseCaseExecutorImpl useCaseExecutor;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());

        UserPrincipal userPrincipal = TestEntityGenerator.randomUserPrincipal();

        doReturn(userPrincipal)
                .when(userDetailsService)
                .loadUserById(userPrincipal.getId());

        doReturn(true)
                .when(jwtProvider)
                .validateToken(eq(TOKEN));

        doReturn(userPrincipal.getId())
                .when(jwtProvider)
                .getUserIdFromToken(eq(TOKEN));
    }

    @Override
    protected MockMvc getMockMvc() {
        return mockMvc;
    }

    @Test
    public void deliveryOrderReturnsOk() throws Exception {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        DeliveryOrderUseCase.InputValues input =
                new DeliveryOrderUseCase.InputValues(order.getId());

        DeliveryOrderUseCase.OutputValues output = new DeliveryOrderUseCase.OutputValues(order.delete());

        // and
        doReturn(output)
                .when(deliveryOrderUseCase)
                .execute(input);

        // and
        RequestBuilder request = asyncPostRequest("/Order/" + order.getId().getNumber() + "/delivery", "", TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Order successfully delivered")));
    }

    @Test
    public void payOrderReturnsOk() throws Exception {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        PayOrderUseCase.InputValues input =
                new PayOrderUseCase.InputValues(order.getId());

        PayOrderUseCase.OutputValues output = new PayOrderUseCase.OutputValues(order.delete());

        // and
        doReturn(output)
                .when(payOrderUseCase)
                .execute(input);

        // and
        RequestBuilder request = asyncPostRequest("/Order/" + order.getId().getNumber() + "/payment", "", TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Order successfully paid")));
    }

    @Test
    public void deleteOrderReturnsOk() throws Exception {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        DeleteOrderUseCase.InputValues input =
                new DeleteOrderUseCase.InputValues(order.getId());

        DeleteOrderUseCase.OutputValues output = new DeleteOrderUseCase.OutputValues(order.delete());

        // and
        doReturn(output)
                .when(deleteOrderUseCase)
                .execute(input);

        // and
        RequestBuilder request = asyncDeleteRequest("/Order/" + order.getId().getNumber(), TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Order successfully canceled")));
    }

    @Test
    public void getCustomerByOrderIdReturnsOk() throws Exception {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        Customer customer = order.getCustomer();

        GetCustomerOrderUseCase.InputValues input =
                new GetCustomerOrderUseCase.InputValues(order.getId());

        GetCustomerOrderUseCase.OutputValues output =
                new GetCustomerOrderUseCase.OutputValues(customer);

        // and
        doReturn(output)
                .when(getCustomerOrderUseCase)
                .execute(input);

        // and
        RequestBuilder request = asyncGetRequest("/Order/" + order.getId().getNumber() + "/customer", TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }

    @Test
    public void getOrderByIdReturnOk() throws Exception {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();

        GetOrderUseCase.InputValues input = new GetOrderUseCase.InputValues(order.getId());

        GetOrderUseCase.OutputValues output = new GetOrderUseCase.OutputValues(order);

        // and
        doReturn(output)
                .when(getOrderUseCase)
                .execute(eq(input));

        // and
        RequestBuilder request = asyncGetRequest("/Order/" + order.getId().getNumber(), TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(order.getId().getNumber().intValue())));
    }

    @Test
    public void createOrderReturnsNotAuthenticate() throws Exception {
        // given
        OrderRequest orderRequest = TestEntityGenerator.randomOrderRequest();

        String payload = createOrderJson.write(orderRequest).getJson();

        // then
        mockMvc
                .perform(post("/Order").contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createOrderReturnsResourceNotFoundWhenStoreOrProductIsNotFound() throws Exception {
        // given
        OrderRequest orderRequest = TestEntityGenerator.randomOrderRequest();

        String payload = createOrderJson.write(orderRequest).getJson();

        // and
        doThrow(new NotFoundException("Error"))
                .when(createOrderUseCase)
                .execute(any(CreateOrderUseCase.InputValues.class));

        // when
        RequestBuilder request = asyncPostRequest("/Order", payload, TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Error")));
    }

    @Test
    public void createOrderReturnsHttpCreated() throws Exception {
        // given
        OrderRequest orderRequest = TestEntityGenerator.randomOrderRequest();

        String payload = createOrderJson.write(orderRequest).getJson();
        Order order = TestCoreEntityGenerator.randomOrder();
        CreateOrderUseCase.OutputValues output = new CreateOrderUseCase.OutputValues(order);

        // and
        doReturn(output)
                .when(createOrderUseCase)
                // TODO: use a more specific match
                .execute(any(CreateOrderUseCase.InputValues.class));

        // when
        RequestBuilder request = asyncPostRequest("/Order", payload, TOKEN);

        // then
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(header().string("location", "http://localhost/Order/" + order.getId().getNumber()))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("order created successfully")));
    }
}