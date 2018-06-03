package com.delivery.core.usecases;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Order;
import com.delivery.core.usecases.order.OrderRepository;
import lombok.Value;

public class GetOrderByIdUseCase extends UseCase<GetOrderByIdUseCase.InputValues, GetOrderByIdUseCase.OutputValues> {
    private OrderRepository repository;

    public GetOrderByIdUseCase(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        final Identity id = input.getId();

        return repository.getById(id)
                .map(OutputValues::new)
                .orElseThrow(() -> new NotFoundException("Order %s not found", id.getNumber()));
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
