package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.order.DeleteOrderByIdUseCase;
import com.delivery.core.usecases.order.GetOrderByIdUseCase;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.core.usecases.order.GetCustomerByOrderIdUseCase;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.CustomerResponse;
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
    private GetCustomerByOrderIdUseCase getCustomerByOrderIdUseCase;
    private DeleteOrderByIdUseCase deleteOrderByIdUseCase;

    public OrderController(UseCaseExecutor useCaseExecutor,
                           CreateOrderUseCase createOrderUseCase,
                           GetOrderByIdUseCase getOrderByIdUseCase,
                           GetCustomerByOrderIdUseCase getCustomerByOrderIdUseCase,
                           DeleteOrderByIdUseCase deleteOrderByIdUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.getCustomerByOrderIdUseCase = getCustomerByOrderIdUseCase;
        this.deleteOrderByIdUseCase = deleteOrderByIdUseCase;
    }

    @Override
    public CompletableFuture<ResponseEntity<ApiResponse>> create(@CurrentUser UserPrincipal userDetails,
                                                                 HttpServletRequest httpServletRequest,
                                                                 @Valid @RequestBody OrderRequest orderRequest) {
        return useCaseExecutor.execute(
                createOrderUseCase,
                CreateOrderInputMapper.map(orderRequest, userDetails),
                (outputValues) -> CreateOrderOutputMapper.map(outputValues.getOrder(), httpServletRequest)
        );
    }

    @Override
    public CompletableFuture<OrderResponse> getById(@CurrentUser UserPrincipal userPrincipal,
                                                    @PathVariable Long id) {
        return useCaseExecutor.execute(
                getOrderByIdUseCase,
                new GetOrderByIdUseCase.InputValues(new Identity(id)),
                (outputValues) -> OrderResponse.from(outputValues.getOrder())
        );
    }

    @Override
    public CompletableFuture<CustomerResponse> getCustomerById(@CurrentUser UserPrincipal userPrincipal,
                                                               @PathVariable Long id) {
        return useCaseExecutor.execute(
                getCustomerByOrderIdUseCase,
                new GetCustomerByOrderIdUseCase.InputValues(new Identity(id)),
                (outputValues) -> CustomerResponse.from(outputValues.getCustomer())
        );
    }

    @Override
    public CompletableFuture<ApiResponse> delete(@CurrentUser UserPrincipal userPrincipal,
                                                 @PathVariable Long id) {
        return useCaseExecutor.execute(
                deleteOrderByIdUseCase,
                new DeleteOrderByIdUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully canceled")
        );
    }
}
