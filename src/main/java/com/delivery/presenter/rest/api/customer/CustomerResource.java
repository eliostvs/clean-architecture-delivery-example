package com.delivery.presenter.rest.api.customer;

import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.AuthenticationResponse;
import com.delivery.presenter.rest.api.entities.SignInRequest;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/Customer")
public interface CustomerResource {

    @PostMapping
    CompletableFuture<ResponseEntity<ApiResponse>> signUp(
            @Valid @RequestBody SignUpRequest request, HttpServletRequest httpServletRequest);

    @PostMapping("/auth")
    CompletableFuture<ResponseEntity<AuthenticationResponse>> signIn(@Valid @RequestBody SignInRequest request);
}
