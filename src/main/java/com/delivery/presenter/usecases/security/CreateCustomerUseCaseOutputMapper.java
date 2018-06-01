package com.delivery.presenter.usecases.security;

import com.delivery.core.domain.Customer;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public final class CreateCustomerUseCaseOutputMapper {
    public static ResponseEntity<ApiResponse> map(Customer customer, HttpServletRequest httpServletRequest) {
        URI location = ServletUriComponentsBuilder
                .fromContextPath(httpServletRequest)
                .path("/Customer/{id}")
                .buildAndExpand(customer.getId().getNumber())
                .toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "registered successfully"));
    }
}
