package com.delivery.presenter.rest.api.order;

import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Order")
public interface OrderResource {

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    CompletableFuture<ResponseEntity<ApiResponse>> createOrder(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody OrderRequest orderRequest);
}
