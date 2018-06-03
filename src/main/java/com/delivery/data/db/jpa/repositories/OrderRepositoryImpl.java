package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.usecases.order.OrderRepository;
import com.delivery.data.db.jpa.entities.OrderData;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private JpaOrderRepository repository;

    public OrderRepositoryImpl(JpaOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order persist(Order order) {
        OrderData orderData = OrderData.from(order);

        return repository.save(orderData).fromThis();
    }

    @Override
    public Optional<Order> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(OrderData::fromThis);
    }
}
