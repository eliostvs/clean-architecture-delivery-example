package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;

public class DeleteOrderUseCase extends UpdateOrderUseCase {

    public DeleteOrderUseCase(OrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        return order.delete();
    }
}
