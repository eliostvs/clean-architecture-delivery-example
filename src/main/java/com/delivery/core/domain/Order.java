package com.delivery.core.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
@EqualsAndHashCode(of = {"id", "status", "customer", "store", "total", "createdAt", "updatedAt"})
public class Order {
    private final Identity id;
    private final Status status;
    private final Customer customer;
    private final Store store;
    private final List<OrderItem> orderItems;
    private final Double total;
    private final Instant createdAt;
    private final Instant updatedAt;

    // TODO: test
    public static Order newInstance(Identity id, Customer customer, Store store, List<OrderItem> orderItems) {
        return new Order(
                id,
                Status.OPEN,
                customer,
                store,
                orderItems,
                calculateTotal(orderItems),
                Instant.now(),
                Instant.now()
        );
    }

    private static Double calculateTotal(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();
    }
}
