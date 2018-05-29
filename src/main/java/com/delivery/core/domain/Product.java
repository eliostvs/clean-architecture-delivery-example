package com.delivery.core.domain;

import lombok.Value;

@Value
public class Product {
    private final Identity id;
    private final String name;
    private final String description;
    private final Double price;
    private final Store store;
}
