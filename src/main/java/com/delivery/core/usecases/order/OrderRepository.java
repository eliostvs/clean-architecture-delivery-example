package com.delivery.core.usecases.order;

import com.delivery.core.domain.Order;

public interface OrderRepository {
    Order persist(Order order);
}
