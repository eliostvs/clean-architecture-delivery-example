package com.delivery.presenter.rest.api.entities;

import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderResponseTest {

    @Test
    public void fromOrder() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();
        OrderItem orderItem = order.getOrderItems().get(0);

        // when
        OrderResponse actual = OrderResponse.from(order);

        // then
        assertThat(actual.getId()).isEqualTo(order.getId().getNumber());
        assertThat(actual.getDate()).isEqualTo(order.getCreatedAt());
        assertThat(actual.getCustomer()).isEqualTo(CustomerResponse.from(order.getCustomer()));
        assertThat(actual.getContact()).isEqualTo(order.getCustomer().getName());
        assertThat(actual.getStore()).isEqualTo(StoreResponse.from(order.getStore()));
        assertThat(actual.getTotal()).isEqualTo(order.getTotal());
        assertThat(actual.getStatus()).isEqualTo(order.getStatus());
        assertThat(actual.getLastUpdate()).isEqualTo(order.getUpdatedAt());

        // and
        OrderResponseItem item = actual.getOrderItems().get(0);
        assertThat(item.getName()).isEqualTo(orderItem.getProduct().getName());
        assertThat(item.getPrice()).isEqualTo(orderItem.getProduct().getPrice());
        assertThat(item.getQuantity()).isEqualTo(orderItem.getQuantity());
        assertThat(item.getTotal()).isEqualTo(orderItem.getTotal());
    }
}