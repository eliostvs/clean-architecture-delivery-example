package com.delivery.presenter.rest.api.entities;

import com.delivery.presenter.usecases.security.UserPrincipal;
import lombok.Value;

@Value
public class CreateOrderRequest {
    private final UserPrincipal userDetails;
    private final PartialOrderRequest partialOrderRequest;
}
