package com.delivery.core.usecases.customer;

import com.delivery.core.domain.Identity;
import lombok.Value;

@Value
public class Customer {
    private final Identity id;
    private final String name;
    private final String email;
    private final String address;
    private final String password;
}
