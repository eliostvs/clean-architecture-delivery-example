package com.delivery.presenter.rest.api.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.product.GetAllProductsUseCase;
import com.delivery.core.usecases.product.GetProductByIdentityUseCase;
import com.delivery.core.usecases.product.SearchProductsByNameUseCase;
import com.delivery.presenter.rest.api.entities.ProductResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ProductController implements ProductResource {
    private UseCaseExecutor useCaseExecutor;
    private GetAllProductsUseCase getAllProductsUseCase;
    private GetProductByIdentityUseCase getProductByIdentityUseCase;
    private SearchProductsByNameUseCase searchProductsByNameUseCase;

    public ProductController(UseCaseExecutor useCaseExecutor,
                             GetAllProductsUseCase getAllProductsUseCase,
                             GetProductByIdentityUseCase getProductByIdentityUseCase,
                             SearchProductsByNameUseCase searchProductsByNameUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductByIdentityUseCase = getProductByIdentityUseCase;
        this.searchProductsByNameUseCase = searchProductsByNameUseCase;
    }

    @Override
    public CompletableFuture<List<ProductResponse>> getAllProducts() {
        return useCaseExecutor.execute(
                getAllProductsUseCase,
                null,
                (arg) -> null,
                ProductResponse::fromDomain);
    }

    @Override
    public CompletableFuture<ProductResponse> getByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getProductByIdentityUseCase,
                id,
                Identity::new,
                ProductResponse::fromDomain);
    }

    @Override
    public CompletableFuture<List<ProductResponse>> getByMatchingName(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchProductsByNameUseCase,
                text,
                (arg) -> arg,
                ProductResponse::fromDomain);
    }
}
