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
public class DeleteOrderUseCaseTest {

    @InjectMocks
    private DeleteOrderUseCase useCase;

    @Mock
    private OrderRepository repository;

    @Test
    public void executeShouldDeleteOrder() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        Order expected = order.delete();

        DeleteOrderUseCase.InputValues input =
                new DeleteOrderUseCase.InputValues(order.getId());

        // and
        doReturn(Optional.of(order))
                .when(repository)
                .getById(eq(order.getId()));

        doReturn(expected)
                .when(repository)
                .persist(eq(expected));

        // when
        Order actual = useCase.execute(input).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}