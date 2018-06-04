package com.delivery.core.domain;

import com.delivery.core.entities.TestCoreEntityGenerator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderItemTest {

    @Test
    public void newInstance() {
        // given
        Identity id = TestCoreEntityGenerator.randomId();
        Product product = TestCoreEntityGenerator.randomProduct();
        Integer quantity = TestCoreEntityGenerator.randomQuantity();

        // when
        OrderItem actual = OrderItem.newInstance(id, product, quantity);

        // then
        assertThat(actual.getId()).isEqualTo(id);
        assertThat(actual.getProduct()).isEqualTo(product);
        assertThat(actual.getTotal()).isEqualTo(product.getPrice() * quantity);
    }
}