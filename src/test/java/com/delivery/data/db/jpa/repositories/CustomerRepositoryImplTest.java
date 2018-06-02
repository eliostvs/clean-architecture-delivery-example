package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Customer;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.data.db.jpa.entities.CustomerData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.delivery.TestUtils.newInstanceWithProperties;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRepositoryImplTest {

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Mock
    private JpaCustomerRepository jpaCustomerRepository;

    @Test
    public void saveShouldPersistCustomerDataAndReturnsCustomer() throws Exception {
        // given
        Customer customer = TestCoreEntityGenerator.randomCustomer();
        CreateCustomerUseCase.InputValues customerInput = new CreateCustomerUseCase.InputValues(
                customer.getName(), customer.getEmail(),
                customer.getAddress(), customer.getPassword());

        CustomerData customerData = newInstanceWithProperties(CustomerData.class, customerInput);

        //and
        CustomerData toBeReturned = newInstanceWithProperties(CustomerData.class, customerData);
        toBeReturned.setId(customer.getId().getNumber());

        // and
        doReturn(toBeReturned)
                .when(jpaCustomerRepository)
                .save(eq(customerData));

        // when
        Customer actual = customerRepository.persist(customerInput);

        // then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    public void existsByEmailReturnsFalse() {
        //given
        String email = "email";

        // and
        doReturn(false)
                .when(jpaCustomerRepository)
                .existsByEmail(eq(email));

        // when
        boolean actual = customerRepository.existsByEmail(email);

        // then
        assertThat(actual).isFalse();
    }

    @Test
    public void existsByEmailReturnsTrue() {
        //given
        String email = "email";

        // and
        doReturn(true)
                .when(jpaCustomerRepository)
                .existsByEmail(eq(email));

        // when
        boolean actual = customerRepository.existsByEmail(email);

        // then
        assertThat(actual).isTrue();
    }
}