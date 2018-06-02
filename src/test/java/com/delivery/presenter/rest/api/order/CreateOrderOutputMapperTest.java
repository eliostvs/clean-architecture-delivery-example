package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CreateOrderOutputMapperTest {

    @Test
    public void mapOrderToResponseCreated() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest("", "");

        // when
        ResponseEntity<ApiResponse> actual = CreateOrderOutputMapper.map(order, httpServletRequest);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody().getSuccess()).isTrue();
        assertThat(actual.getBody().getMessage()).isEqualTo("order created successfully");
        assertThat(actual.getHeaders().getLocation().toString()).isEqualTo("http://localhost/Order/" + order.getId().getNumber());
    }
}