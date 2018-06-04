package com.delivery.core.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class Product {
    private Identity id;
    private String name;
    private String description;
    private Double price;
    private Store store;
}
