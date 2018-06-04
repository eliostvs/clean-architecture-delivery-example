package com.delivery.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderItem {
    private final Identity id;
    private final Integer quantity;
    private final Product product;
    private final Double total;

    public static OrderItem newInstance(Identity id, Product product, Integer quantity) {
        return new OrderItem(
                id,
                quantity,
                product,
                quantity * product.getPrice()
        );
    }
}
