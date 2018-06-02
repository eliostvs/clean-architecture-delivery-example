package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Customer;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.presenter.usecases.security.CreateCustomerUseCaseOutputMapper;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateCustomerUseCaseOutputMapperTest {

    @Test
    public void mapReturnsResponseEntityWithCustomerPath() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();
        HttpServletRequest httpServletRequest = new MockHttpServletRequest("", "");

        // given
        ResponseEntity<ApiResponse> actual = CreateCustomerUseCaseOutputMapper.map(customer, httpServletRequest);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getBody().getSuccess()).isTrue();
        assertThat(actual.getBody().getMessage()).isEqualTo("registered successfully");
        assertThat(actual.getHeaders().getLocation().toString()).isEqualTo("http://localhost/Customer/" + customer.getId().getNumber());
    }
}