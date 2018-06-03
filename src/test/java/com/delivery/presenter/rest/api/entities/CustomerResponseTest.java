package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Customer;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerResponseTest {

    @Test
    public void fromCustomer() {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();

        // when
        CustomerResponse actual = CustomerResponse.from(customer);

        // then
        assertThat(actual.getName()).isEqualTo(customer.getName());
        assertThat(actual.getEmail()).isEqualTo(customer.getEmail());
        assertThat(actual.getAddress()).isEqualTo(customer.getAddress());
    }
}