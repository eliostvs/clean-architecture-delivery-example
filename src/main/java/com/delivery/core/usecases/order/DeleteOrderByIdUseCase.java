package com.delivery.core.usecases.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

public class DeleteOrderByIdUseCase extends UseCase<DeleteOrderByIdUseCase.InputValues, DeleteOrderByIdUseCase.OutputValues> {
    private OrderRepository repository;

    public DeleteOrderByIdUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        final Identity id = input.getId();

        return this.repository
                .getById(id)
                .map(this::deleteOrder)
                .map(OutputValues::new)
                .orElseThrow(() -> new NotFoundException("Order %s not found", id));
    }

    private Order deleteOrder(Order order) {
        order.delete();

        return this.repository.persist(order);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Order order;
    }
}
