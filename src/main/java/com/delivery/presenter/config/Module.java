package com.delivery.presenter.config;

import com.delivery.core.usecases.order.DeleteOrderUseCase;
import com.delivery.core.usecases.order.DeliveryOrderUseCase;
import com.delivery.core.usecases.order.GetOrderUseCase;
import com.delivery.core.usecases.cousine.CousineRepository;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetStoresByCousineUseCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.core.usecases.customer.CreateCustomerUseCase;
import com.delivery.core.usecases.customer.CustomerRepository;
import com.delivery.core.usecases.order.CreateOrderUseCase;
import com.delivery.core.usecases.order.GetCustomerOrderUseCase;
import com.delivery.core.usecases.order.OrderRepository;
import com.delivery.core.usecases.order.PayOrderUseCase;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.core.usecases.product.GetProductUseCase;
import com.delivery.core.usecases.product.GetProductsByStoreAndProductsIdUseCase;
import com.delivery.core.usecases.product.ProductRepository;
import com.delivery.core.usecases.product.SearchProductsByNameOrDescriptionUseCase;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetProductsByStoreUseCase;
import com.delivery.core.usecases.store.GetStoreUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
import com.delivery.core.usecases.store.StoreRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Module {

    @Bean
    public DeliveryOrderUseCase deliveryOrderUseCase(OrderRepository repository) {
        return new DeliveryOrderUseCase(repository);
    }

    @Bean
    public PayOrderUseCase payOrderUseCase(OrderRepository repository) {
        return new PayOrderUseCase(repository);
    }

    @Bean
    public DeleteOrderUseCase deleteOrderUseCase(OrderRepository repository) {
        return new DeleteOrderUseCase(repository);
    }

    @Bean
    public GetCustomerOrderUseCase getCustomerOrderUseCase(GetOrderUseCase getOrderUseCase) {
        return new GetCustomerOrderUseCase(getOrderUseCase);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderRepository repository) {
        return new GetOrderUseCase(repository);
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase,
                                                 OrderRepository repository) {
        return new CreateOrderUseCase(getProductsByStoreAndProductsIdUseCase, repository);
    }

    @Bean
    public GetProductsByStoreAndProductsIdUseCase getProductsByStoreAndProductsIdUseCase(ProductRepository repository) {
        return new GetProductsByStoreAndProductsIdUseCase(repository);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CustomerRepository repository) {
        return new CreateCustomerUseCase(repository);
    }

    @Bean
    public SearchProductsByNameOrDescriptionUseCase searchProductsByNameUseCase(ProductRepository repository) {
        return new SearchProductsByNameOrDescriptionUseCase(repository);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepository repository) {
        return new GetProductUseCase(repository);
    }

    @Bean
    public GetAllProductsUseCase getAllProductsUseCase(ProductRepository repository) {
        return new GetAllProductsUseCase(repository);
    }

    @Bean
    public GetProductsByStoreUseCase getProductsByStoreIdentityUseCase(StoreRepository repository) {
        return new GetProductsByStoreUseCase(repository);
    }

    @Bean
    public GetStoreUseCase getStoreUseCase(StoreRepository repository) {
        return new GetStoreUseCase(repository);
    }

    @Bean
    public SearchStoresByNameUseCase searchStoresByNameUseCase(StoreRepository repository) {
        return new SearchStoresByNameUseCase(repository);
    }

    @Bean
    public GetAllStoresUseCase getAllStoresUseCase(StoreRepository repository) {
        return new GetAllStoresUseCase(repository);
    }

    @Bean
    public GetStoresByCousineUseCase getStoresByCousineUseCase(CousineRepository repository) {
        return new GetStoresByCousineUseCase(repository);
    }

    @Bean
    public GetAllCousinesUseCase getAllCousinesUseCase(CousineRepository repository) {
        return new GetAllCousinesUseCase(repository);
    }

    @Bean
    public SearchCousineByNameUseCase searchCousineByNameUseCase(CousineRepository repository) {
        return new SearchCousineByNameUseCase(repository);
    }
}
