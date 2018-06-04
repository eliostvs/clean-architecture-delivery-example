package com.delivery.core.domain;

import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {

    @Test
    public void deliveryOrderThrowExceptionWhenStatusIsDifferentFromOpen() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();

        // when
        assertErrorMessage(order::delivery, "Order should be paid to be delivered");
    }

    @Test
    public void deliveryOrderUpdatesStatusAndUpdateAt() throws InterruptedException {
        // given
        Order order = TestCoreEntityGenerator.randomOrder().pay();

        // then
        assertOrderStatus(order, Order::delivery, Status.DELIVERED);
    }

    @Test
    public void payOrderThrowExceptionWhenStatusIsDifferentFromOpen() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder().pay();

        // when
        assertErrorMessage(order::pay, "Order should be open to be paid");
    }

    @Test
    public void payOrderUpdatesStatusAndUpdateAt() throws InterruptedException {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();

        // then
        assertOrderStatus(order, Order::pay, Status.PAID);
    }

    @Test
    public void deleteOrderUpdatesStatusAndUpdateAt() throws InterruptedException {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();

        // then
        assertOrderStatus(order, Order::delete, Status.CANCELLED);
    }

    @Test
    public void deleteOrderThrowExceptionWhenStatusIsDifferentFromOpen() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder().delete();

        // when
        assertErrorMessage(order::delete, "Order should be open to be cancelled");
    }

    private void assertErrorMessage(Supplier<Order> consumer, String message) {
        assertThatThrownBy(consumer::get)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(message);
    }

    private void assertOrderStatus(Order order, Function<Order, Order> func, Status paid) throws InterruptedException {
        Thread.sleep(200);

        Order actual = func.apply(order);

        assertThat(actual).isEqualToComparingOnlyGivenFields(
                order, "id", "customer", "store", "orderItems", "total", "createdAt");
        assertThat(actual.getStatus()).isEqualTo(paid);
        assertThat(actual.getUpdatedAt()).isAfter(order.getUpdatedAt());
    }

}