package com.delivery.core.domain;

import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {

    @Test
    public void deleteOrderShouldUpdateStatusAndUpdateAd() throws InterruptedException {
        // given
        Order order = TestCoreEntityGenerator.randomOrder();

        // when
        Thread.sleep(1000);

        // when
        Order actual = order.delete();

        // then
        assertThat(actual).isEqualToComparingOnlyGivenFields(
                order, "id", "customer", "store", "orderItems", "total", "createdAt");
        assertThat(actual.getStatus()).isEqualTo(Status.CANCELLED);
        assertThat(actual.getUpdatedAt()).isAfter(order.getUpdatedAt());
    }

    @Test
    public void deleteOrderThrowExceptionWhenStatusIsDifferentFromOpen() {
        // given
        Order order = TestCoreEntityGenerator.randomOrder().delete();

        // when
        assertThatThrownBy(order::delete)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Order should be open to be cancelled");
    }
}