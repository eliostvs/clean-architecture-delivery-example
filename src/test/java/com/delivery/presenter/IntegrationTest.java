package com.delivery.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/v1");
    }

    @Test
    public void getAllCousines() {
        // given
        final String url = base.toString() + "/Cousine";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStoresByCousineId() {
        // given
        final String url = base.toString() + "/Cousine/1/stores";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void searchCousine() {
        // given
        final String url = base.toString() + "/Cousine/search/abc";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStores() {
        // given
        final String url = base.toString() + "/Store";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void searchStore() {
        // given
        final String url = base.toString() + "/Store/search/pizza";

        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getStoreById() {
        // given
        final String url = base.toString() + "/Store/1/";

        ResponseEntity<String> response = template.getForEntity(url, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
