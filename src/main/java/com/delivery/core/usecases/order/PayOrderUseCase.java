package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;

public class PayOrderUseCase extends UpdateOrderUseCase {
    public PayOrderUseCase(OrderRepository repository) {
        super(repository);
    }

    @Override
    protected Order updateStatus(Order order) {
        return repository.persist(order.pay());
    }
}
