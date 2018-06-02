package com.delivery.presenter.rest.api.order;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.presenter.rest.api.entities.OrderRequestItem;
import com.delivery.presenter.rest.api.entities.OrderRequest;
import com.delivery.presenter.usecases.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public final class CreateOrderInputMapper {

    public static CreateOrderUseCase.InputValues map(OrderRequest orderRequest, UserDetails userDetails) {
        return new CreateOrderUseCase.InputValues(
                map(userDetails),
                new Identity(orderRequest.getStoreId()),
                map(orderRequest.getOrderItems())
        );
    }

    public static Customer map(UserDetails userDetails) {
        UserPrincipal userPrincipal = (UserPrincipal) userDetails;

        return new Customer(
                new Identity(userPrincipal.getId()),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                userPrincipal.getAddress(),
                userPrincipal.getPassword()
        );
    }

    private static List<CreateOrderUseCase.InputItem> map(List<OrderRequestItem> orderItems) {
        return orderItems
                .parallelStream()
                .map(CreateOrderInputMapper::map)
                .collect(Collectors.toList());
    }

    private static CreateOrderUseCase.InputItem map(OrderRequestItem orderRequestItem) {
        return new CreateOrderUseCase.InputItem(new Identity(orderRequestItem.getId()), orderRequestItem.getQuantity());
    }
}
