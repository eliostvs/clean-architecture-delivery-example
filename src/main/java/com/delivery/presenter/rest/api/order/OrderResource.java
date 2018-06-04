package com.delivery.presenter.rest.api.order;

import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.CustomerResponse;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.rest.api.entities.OrderResponse;
import com.delivery.presenter.usecases.security.CurrentUser;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    CompletableFuture<ResponseEntity<ApiResponse>> create(@CurrentUser UserPrincipal userPrincipal,
                                                          HttpServletRequest httpServletRequest,
                                                          @Valid @RequestBody OrderRequest orderRequest);

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    CompletableFuture<OrderResponse> getById(@PathVariable Long id);

    @GetMapping("/{id}/customer")
    @PreAuthorize("hasRole('USER')")
    CompletableFuture<CustomerResponse> getCustomerById(@PathVariable Long id);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    CompletableFuture<ApiResponse> delete(@PathVariable Long id);

    @PostMapping("/{id}/payment")
    @PreAuthorize("hasRole('USER')")
    CompletableFuture<ApiResponse> pay(@PathVariable Long id);

    @PostMapping("/{id}/delivery")
    @PreAuthorize("hasRole('USER')")
    CompletableFuture<ApiResponse> delivery(@PathVariable Long id);
}
