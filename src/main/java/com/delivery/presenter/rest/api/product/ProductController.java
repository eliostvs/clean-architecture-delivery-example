package com.delivery.presenter.rest.api.product;

import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.presenter.rest.api.entities.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductController implements ProductResource {
    private UseCaseExecutor useCaseExecutor;
    private GetAllProductsUseCase getAllProductsUseCase;

    public ProductController(UseCaseExecutor useCaseExecutor,
                             GetAllProductsUseCase getAllProductsUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllProductsUseCase = getAllProductsUseCase;
    }

    @Override
    public CompletableFuture<List<ProductResponse>> getAllProducts() {
        return useCaseExecutor.execute(
                getAllProductsUseCase,
                null,
                (arg) -> null,
                ProductResponse::fromProduct);
    }
}
