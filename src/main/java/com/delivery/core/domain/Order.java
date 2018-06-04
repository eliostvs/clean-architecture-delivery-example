package com.delivery.core.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;
import java.util.List;

@Value
@EqualsAndHashCode(of = {"id", "status", "customer", "store", "total", "createdAt"})
public class Order {
    private final Identity id;
    private final Status status;
    private final Customer customer;
    private final Store store;
    private final List<OrderItem> orderItems;
    private final Double total;
    private final Instant createdAt;
    private final Instant updatedAt;

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

    public Order delete() {
        if (this.status != Status.OPEN) {
            throw new IllegalStateException("Order should be open to be cancelled");
        }

        return newInstanceWith(Status.CANCELLED);
    }

    public Order delivery() {
        if (this.status != Status.PAID) {
            throw new IllegalStateException("Order should be paid to be delivered");
        }

        return newInstanceWith(Status.DELIVERED);
    }

    public Order pay() {
        if (this.status != Status.OPEN) {
            throw new IllegalStateException("Order should be open to be paid");
        }

        return newInstanceWith(Status.PAID);
    }

    private Order newInstanceWith(Status status) {
        return new Order(
                id,
                status,
                customer,
                store,
                orderItems,
                total,
                createdAt,
                Instant.now()
        );
    }
}
