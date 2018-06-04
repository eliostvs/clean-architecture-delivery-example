package com.delivery.presenter.usecases.security;

import com.delivery.data.db.jpa.entities.CustomerData;
import org.junit.Test;

import static com.delivery.core.entities.TestCoreEntityGenerator.randomCustomer;
import static org.assertj.core.api.Assertions.assertThat;

public class UserPrincipalTest {

    @Test
    public void fromCustomer() {
        // given
        CustomerData customerData = CustomerData.from(randomCustomer());

        // when
        UserPrincipal actual = UserPrincipal.from(customerData);

        // then
        assertThat(actual).isEqualToComparingOnlyGivenFields(customerData, "name", "id", "password", "address");
        assertThat(actual.getAuthorities()).extracting("role").containsOnly("ROLE_USER");
    }
}