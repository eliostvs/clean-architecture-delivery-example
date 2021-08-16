package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.order.DeleteOrderUseCase;
import com.delivery.core.usecases.order.DeliveryOrderUseCase;
import com.delivery.core.usecases.order.GetOrderUseCase;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.core.usecases.order.GetCustomerOrderUseCase;
import com.delivery.core.usecases.order.PayOrderUseCase;
import com.delivery.presenter.rest.api.entities.ApiResponse;
import com.delivery.presenter.rest.api.entities.CustomerResponse;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.rest.api.entities.OrderResponse;
import com.delivery.presenter.usecases.security.CurrentUser;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@Controller
public class OrderController implements OrderResource {
    private UseCaseExecutor useCaseExecutor;
    private CreateOrderUseCase createOrderUseCase;
    private GetOrderUseCase getOrderUseCase;
    private GetCustomerOrderUseCase getCustomerOrderUseCase;
    private DeleteOrderUseCase deleteOrderUseCase;
    private PayOrderUseCase payOrderUseCase;
    private DeliveryOrderUseCase deliveryOrderUseCase;

    public OrderController(UseCaseExecutor useCaseExecutor,
                           CreateOrderUseCase createOrderUseCase,
                           GetOrderUseCase getOrderUseCase,
                           GetCustomerOrderUseCase getCustomerOrderUseCase,
                           DeleteOrderUseCase deleteOrderUseCase,
                           PayOrderUseCase payOrderUseCase,
                           DeliveryOrderUseCase deliveryOrderUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.getCustomerOrderUseCase = getCustomerOrderUseCase;
        this.deleteOrderUseCase = deleteOrderUseCase;
        this.payOrderUseCase = payOrderUseCase;
        this.deliveryOrderUseCase = deliveryOrderUseCase;
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
    public CompletableFuture<OrderResponse> getById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getOrderUseCase,
                new GetOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> OrderResponse.from(outputValues.getOrder())
        );
    }

    @Override
    public CompletableFuture<CustomerResponse> getCustomerById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getCustomerOrderUseCase,
                new GetCustomerOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> CustomerResponse.from(outputValues.getCustomer())
        );
    }

    @Override
    public CompletableFuture<ApiResponse> delete(@PathVariable Long id) {
        return useCaseExecutor.execute(
                deleteOrderUseCase,
                new DeleteOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully canceled")
        );
    }

    @Override
    public CompletableFuture<ApiResponse> pay(@PathVariable Long id) {
        return useCaseExecutor.execute(
                payOrderUseCase,
                new DeleteOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully paid")
        );
    }

    @Override
    public CompletableFuture<ApiResponse> delivery(@PathVariable Long id) {
        return useCaseExecutor.execute(
                deliveryOrderUseCase,
                new DeleteOrderUseCase.InputValues(new Identity(id)),
                (outputValues) -> new ApiResponse(true, "Order successfully delivered")
        );
    }
}
