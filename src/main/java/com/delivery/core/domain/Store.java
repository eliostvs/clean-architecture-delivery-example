package com.delivery.core.domain;

import lombok.Value;

@Value
public class Store {
    private final Identity id;
    private final String name;
    private final String address;
    private final Cousine cousine;
}
