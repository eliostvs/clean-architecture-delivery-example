package com.delivery.presenter.rest.api.customer;

import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.SignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Component
public class CustomerController implements CustomerResource {
    private UseCaseExecutor useCaseExecutor;
    private CreateCustomerUseCase createCustomerUseCase;
    private CreateCustomerInputMapper createCustomerUseCaseInputMapper;

    public CustomerController(UseCaseExecutor useCaseExecutor,
                              CreateCustomerUseCase createCustomerUseCase,
                              CreateCustomerInputMapper createCustomerUseCaseInputMapper) {
        this.useCaseExecutor = useCaseExecutor;
        this.createCustomerUseCase = createCustomerUseCase;
        this.createCustomerUseCaseInputMapper = createCustomerUseCaseInputMapper;
    }

    @Override
    public CompletableFuture<ResponseEntity<ApiResponse>> signUp(@Valid @RequestBody SignUpRequest request,
                                                                 HttpServletRequest httpServletRequest) {
        return useCaseExecutor.execute(
                createCustomerUseCase,
                request,
                createCustomerUseCaseInputMapper::map,
                (customer) -> CreateCustomerUseCaseOutputMapper.map(customer, httpServletRequest));
    }
}
