package com.delivery.presenter.rest.api.entities;

import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
public class OrderRequestItem {
    @NotNull
    private final Long id;

    @Min(1)
    @NotNull
    private final Integer quantity;
}
