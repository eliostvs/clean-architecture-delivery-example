package com.delivery.presenter;

import com.delivery.presenter.rest.api.customer.CustomerController;
import com.delivery.presenter.rest.api.entities.OrderRequestItem;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.rest.api.entities.SignInRequest;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    private static String USER_EMAIL = "email@email.com";
    private static String USER_PASSWORD = "password";

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(IntegrationTest.class);

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private CustomerController customerController;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/v1");
    }

    @Test
    public void getAllCousines() {
        // given
        String url = base.toString() + "/Cousine";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStoresByCousineId() {
        // given
        String url = base.toString() + "/Cousine/1/stores";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void searchCousine() {
        // given
        String url = base.toString() + "/Cousine/search/abc";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStores() {
        // given
        String url = base.toString() + "/Store";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void searchStore() {
        // given
        String url = base.toString() + "/Store/search/pizza";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStoreById() {
        // given
        String url = base.toString() + "/Store/1/";

        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getProductsByStoreId() {
        // given
        String url = base.toString() + "/Store/1/products";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getAllProducts() {
        // given
        String url = base.toString() + "/Product/";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getProductById() {
        // given
        String url = base.toString() + "/Product/1";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void searchProductByNameOrDescription() {
        // given
        String url = base.toString() + "/Product/search/temp";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createCustomer() {
        // given
        String url = base.toString() + "/Customer";
        SignUpRequest request = new SignUpRequest("name", "create@USER_EMAIL.com", "address", "password");

        // when
        ResponseEntity<String> response = template.postForEntity(url, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void authenticateCustomer() {
        // given
        createTestCustomer();

        // and
        SignInRequest request = new SignInRequest(USER_EMAIL, USER_PASSWORD);
        String url = base.toString() + "/Customer/auth";

        // when
        ResponseEntity<String> response = template.postForEntity(url, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createOrder() {
        // given
        createTestCustomer();

        // and
        String url = base.toString() + "/Order";
        HttpEntity<OrderRequest> request = requestOneShrimpTempuraInHaiShangStore();

        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, request, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    private HttpEntity<OrderRequest> requestOneShrimpTempuraInHaiShangStore() {
        return new HttpEntity<>(createHaiShangOrder(), createAuthHeader());
    }

    private HttpHeaders createAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + getTokenTestCustomer());
        return headers;
    }

    private void createTestCustomer() {
        try {
            customerController
                    .signUp(new SignUpRequest("name", USER_EMAIL, "address", USER_PASSWORD), new MockHttpServletRequest())
                    .join();

        } catch (Exception ex) {
            log.error("Fail to create test customer", ex);
        }
    }

    private String getTokenTestCustomer() {
        return customerController.signIn(new SignInRequest(USER_EMAIL, USER_PASSWORD))
                .join()
                .getBody()
                .getToken();
    }

    private OrderRequest createHaiShangOrder() {
        Long storeId = 1L;
        return new OrderRequest(storeId, createOneShrimpTempura());
    }

    private List<OrderRequestItem> createOneShrimpTempura() {
        return Collections.singletonList(new OrderRequestItem(1L, 2));
    }
}
