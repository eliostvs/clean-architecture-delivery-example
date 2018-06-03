package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.OrderItem;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class OrderResponseItem {
    private final String name;
    private final Double price;
    private final Integer quantity;
    private final Double total;

    public static List<OrderResponseItem> from(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(OrderResponseItem::from)
                .collect(Collectors.toList());
    }

    private static OrderResponseItem from(OrderItem orderItem) {
        return new OrderResponseItem(
                orderItem.getProduct().getName(),
                orderItem.getProduct().getPrice(),
                orderItem.getQuantity(),
                orderItem.getTotal()
        );
    }
}
