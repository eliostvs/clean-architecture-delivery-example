package com.delivery.core.usecases.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;

import java.util.Optional;

public interface OrderRepository {

    Order persist(Order order);

    Optional<Order> getById(Identity id);
}
