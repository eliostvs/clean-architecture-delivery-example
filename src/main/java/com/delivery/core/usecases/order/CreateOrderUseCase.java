package com.delivery.core.usecases.order;

import com.delivery.core.domain.Customer;
import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Order;
import com.delivery.core.domain.OrderItem;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;
import com.delivery.core.usecases.product.GetProductsByStoreAndProductsIdUseCase;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CreateOrderUseCase extends UseCase<CreateOrderUseCase.InputValues, CreateOrderUseCase.OutputValues> {
    private GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase;
    private OrderRepository orderRepository;

    public CreateOrderUseCase(GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase,
                              OrderRepository orderRepository) {
        this.getProductsByStoreAndProductsIdUseCase = getProductsByStoreAndProductsIdUseCase;
        this.orderRepository = orderRepository;
    }

    @Override
    public OutputValues execute(InputValues input) {
        Order order = createOrder(input);

        return new OutputValues(orderRepository.persist(order));
    }

    private Order createOrder(InputValues input) {
        final List<OrderItem> orderItems = createOrderItems(input);

        return Order.newInstance(
                Identity.nothing(),
                input.getCustomer(),
                getFirstProductStore(orderItems),
                orderItems
        );
    }

    private Store getFirstProductStore(List<OrderItem> orderItems) {
        return orderItems.get(0).getProduct().getStore();
    }

    private List<OrderItem> createOrderItems(InputValues input) {
        Map<Identity, Product> productMap = getProducts(input);

        return input
                .getOrderItems()
                .stream()
                .map(inputItem -> createOrderItem(inputItem, productMap))
                .collect(Collectors.toList());
    }

    private OrderItem createOrderItem(InputItem inputItem, Map<Identity, Product> productMap) {
        Product product = productMap.get(inputItem.getId());
        return OrderItem.newInstance(Identity.nothing(), product, inputItem.getQuantity());
    }

    private Map<Identity, Product> getProducts(InputValues input) {
        GetProductsByStoreAndProductsIdUseCase.InputValues inputValues =
                new GetProductsByStoreAndProductsIdUseCase.InputValues(
                        input.getStoreId(), createListOfProductsId(input.getOrderItems()));

        return getProductsByStoreAndProductsIdUseCase.execute(inputValues)
                .getProducts()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    private List<Identity> createListOfProductsId(List<InputItem> inputItems) {
        return inputItems
                .stream()
                .map(InputItem::getId)
                .collect(Collectors.toList());
    }

    @Value
    public static class InputValues implements UseCase.InputValues {
        private final Customer customer;
        private final Identity storeId;
        private final List<InputItem> orderItems;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {
        private final Order order;
    }

    @Value
    public static class InputItem {
        private final Identity id;
        private final int quantity;
    }
}
