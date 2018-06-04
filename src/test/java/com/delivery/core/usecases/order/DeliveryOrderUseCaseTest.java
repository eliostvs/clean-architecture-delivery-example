package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryOrderUseCaseTest {

    @InjectMocks
    private DeliveryOrderUseCase useCase;

    @Mock
    private OrderRepository repository;

    @Test
    public void executeShouldDeliveryOrder() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder().pay();
        Order expected = order.delivery();

        UpdateOrderUseCase.InputValues input =
                new DeliveryOrderUseCase.InputValues(order.getId());

        // and
        doReturn(Optional.of(order))
                .when(repository)
                .getById(order.getId());

        // and
        doReturn(expected)
                .when(repository)
                .persist(eq(expected));

        // when
        Order actual = useCase.execute(input).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}