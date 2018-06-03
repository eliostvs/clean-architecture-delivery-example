package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.GetOrderByIdUseCase;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.rest.api.entities.OrderResponse;
import com.delivery.presenter.usecases.security.CurrentUser;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Component
public class OrderController implements OrderResource {
    private UseCaseExecutor useCaseExecutor;
    private CreateOrderUseCase createOrderUseCase;
    private GetOrderByIdUseCase getOrderByIdUseCase;

    public OrderController(UseCaseExecutor useCaseExecutor,
                           CreateOrderUseCase createOrderUseCase,
                           GetOrderByIdUseCase getOrderByIdUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
    }

    @Override
    public CompletableFuture<ResponseEntity<ApiResponse>> createOrder(@CurrentUser UserPrincipal userDetails,
                                                                      HttpServletRequest httpServletRequest,
                                                                      @Valid @RequestBody OrderRequest orderRequest) {
        return useCaseExecutor.execute(
                createOrderUseCase,
                CreateOrderInputMapper.map(orderRequest, userDetails),
                (outputValues) -> CreateOrderOutputMapper.map(outputValues.getOrder(), httpServletRequest)
        );
    }

    @Override
    public CompletableFuture<OrderResponse> getOrderById(@CurrentUser UserPrincipal userPrincipal,
                                                         @PathVariable Long id) {
        return useCaseExecutor.execute(
                getOrderByIdUseCase,
                new GetOrderByIdUseCase.InputValues(new Identity(id)),
                (outputValues) -> OrderResponse.from(outputValues.getOrder())
        );
    }
}
