package com.delivery.core.domain;

import lombok.Value;

@Value
public class Store {
    private final Identity id;
    private final String name;
    private final String address;
    private final Cousine cousine;

    public static Store newInstance(Identity id, String name, String address, Cousine cousine) {
        return new Store(
                id,
                name,
                address,
                cousine
        );
    }
}
