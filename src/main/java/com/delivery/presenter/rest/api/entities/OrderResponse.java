package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Order;
import com.delivery.core.domain.Status;
import lombok.Value;

import java.time.Instant;
import java.util.List;

import static com.delivery.data.db.jpa.entities.IdConverter.convertId;

@Value
public class OrderResponse {
    private final Long id;
    private final Instant date;
    private final Long customerId;
    private final String contact;
    private final Long storeId;
    private final Double total;
    private final Status status;
    private final Instant lastUpdate;
    private final List<OrderResponseItem> orderItems;

    public static OrderResponse from (Order order) {
        return new OrderResponse(
                convertId(order.getId()),
                order.getCreatedAt(),
                convertId(order.getCustomer().getId()),
                order.getCustomer().getName(),
                convertId(order.getStore().getId()),
                order.getTotal(),
                order.getStatus(),
                order.getUpdatedAt(),
                OrderResponseItem.from(order.getOrderItems())
        );
    }
}
