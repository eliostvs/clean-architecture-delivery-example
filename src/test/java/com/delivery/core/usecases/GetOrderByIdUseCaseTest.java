package com.delivery.core.usecases;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.entities.TestCoreEntityGenerator;
import com.delivery.core.usecases.order.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class GetOrderByIdUseCaseTest {

    @InjectMocks
    private GetOrderByIdUseCase useCase;

    @Mock
    private OrderRepository repository;

    @Test
    public void executeThrowExceptionWhenOrderIsNotFound() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        GetOrderByIdUseCase.InputValues input = new GetOrderByIdUseCase.InputValues(id);

        // and
        doReturn(Optional.empty())
                .when(repository)
                .getById(eq(id));

        // then
        assertThatThrownBy(() -> useCase.execute(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Order " + id.getNumber() + " not found");
    }

    @Test
    public void executeReturnsOrderWhenOrderIsFound() {
        // given
        Order expected = TestCoreEntityGenerator.randomOrder();
        GetOrderByIdUseCase.InputValues input = new GetOrderByIdUseCase.InputValues(expected.getId());

        // and
        doReturn(Optional.of(expected))
                .when(repository)
                .getById(eq(expected.getId()));

        // when
        Order actual = useCase.execute(input).getOrder();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}