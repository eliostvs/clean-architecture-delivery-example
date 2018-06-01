package com.delivery.core.domain;

import lombok.Value;

@Value
public class Customer {
    private final Identity id;
    private final String name;
    private final String email;
    private final String address;
    private final String password;
}
