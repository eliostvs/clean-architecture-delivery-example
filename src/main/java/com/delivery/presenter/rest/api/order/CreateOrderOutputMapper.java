package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Order;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public final class CreateOrderOutputMapper {

    public static ResponseEntity<ApiResponse> map(Order order, HttpServletRequest httpServletRequest) {
        URI location = ServletUriComponentsBuilder
                .fromContextPath(httpServletRequest)
                .path("/Order/{id}")
                .buildAndExpand(order.getId().getNumber())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "order created successfully"));
    }
}
