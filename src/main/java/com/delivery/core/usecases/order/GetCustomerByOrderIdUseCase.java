package com.delivery.core.usecases.order;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.usecases.GetOrderByIdUseCase;
import com.delivery.core.usecases.UseCase;
import lombok.Value;

public class GetCustomerByOrderIdUseCase extends UseCase<GetCustomerByOrderIdUseCase.InputValues, GetCustomerByOrderIdUseCase.OutputValues> {
    private GetOrderByIdUseCase getOrderByIdUseCase;

    public GetCustomerByOrderIdUseCase(GetOrderByIdUseCase getOrderByIdUseCase) {
        this.getOrderByIdUseCase = getOrderByIdUseCase;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Order order = getOrderByIdUseCase
                .execute(new GetOrderByIdUseCase.InputValues(input.getId()))
                .getOrder();

        return new OutputValues(order.getCustomer());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Identity id;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Customer customer;
    }
}
