package com.delivery.core.usecases.order;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetCustomerByOrderIdUseCaseTest {

    @InjectMocks
    private GetCustomerByOrderIdUseCase useCase;

    @Mock
    private GetOrderByIdUseCase getOrderByIdUseCase;

    @Test
    public void executeReturnsOrderCustomer() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        Customer expected = order.getCustomer();

        GetCustomerByOrderIdUseCase.InputValues input =
                new GetCustomerByOrderIdUseCase.InputValues(order.getId());

        GetOrderByIdUseCase.InputValues orderByIdInput =
                new GetOrderByIdUseCase.InputValues(order.getId());

        GetOrderByIdUseCase.OutputValues orderByIdOutput =
                new GetOrderByIdUseCase.OutputValues(order);

        // and
        doReturn(orderByIdOutput)
                .when(getOrderByIdUseCase)
                .execute(eq(orderByIdInput));

        // when
        Customer actual = useCase.execute(input).getCustomer();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}