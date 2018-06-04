package com.delivery.core.usecases.order;

import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UpdateOrderUseCaseTest {
    private UpdateOrderUseCase useCase;

    @Mock
    private OrderRepository repository;

    @Before
    public void setUp() {
        useCase = new UpdateOrderUseCase(repository) {
            @Override
            protected Order updateStatus(Order order) {
                return order.delete();
            }
        };
    }

    @Test
    public void executeThrowExceptionWhenOrderIsNotFound() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        UpdateOrderUseCase.InputValues input =
                new UpdateOrderUseCase.InputValues(order.getId());

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(eq(order.getId()));

        // given
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order " + order.getId() + " not found");
    }

    @Test
    public void executeCallUpdateMethodWhenOrderIsFound() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        Order expected = TestCoreEntityGenerator.randomOrder();

        UpdateOrderUseCase.InputValues input =
                new UpdateOrderUseCase.InputValues(order.getId());

        // and
        doReturn(Optional.of(order))
                .when(repository)
                .getById(eq(order.getId()));

        // and
        doReturn(expected)
                .when(repository)
                .persist(eq(order.delete()));

        // when
        Order actual = useCase.execute(input).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}