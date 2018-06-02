package com.delivery.presenter.rest.api.order;

import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.CreateOrderRequest;
import com.delivery.presenter.rest.api.entities.PartialOrderRequest;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Component
public class OrderController implements OrderResource {
    private UseCaseExecutor useCaseExecutor;
    private CreateOrderUseCase createOrderUseCase;

    public OrderController(UseCaseExecutor useCaseExecutor, CreateOrderUseCase createOrderUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public CompletableFuture<ResponseEntity<ApiResponse>> createOrder(@AuthenticationPrincipal UserPrincipal userDetails,
                                                                      HttpServletRequest httpServletRequest,
                                                                      @Valid @RequestBody PartialOrderRequest partialOrderRequest) {
        return useCaseExecutor.execute(
                createOrderUseCase,
                new CreateOrderRequest(userDetails, partialOrderRequest),
                CreateOrderInputMapper::map,
                (order) -> CreateOrderOutputMapper.map(order, httpServletRequest));
    }
}
