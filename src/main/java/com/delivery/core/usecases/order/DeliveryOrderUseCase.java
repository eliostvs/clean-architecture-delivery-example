package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;

public class DeliveryOrderUseCase extends UpdateOrderUseCase {
    public DeliveryOrderUseCase(OrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        order.delivery();

        return repository.persist(order.delivery());
    }
}
