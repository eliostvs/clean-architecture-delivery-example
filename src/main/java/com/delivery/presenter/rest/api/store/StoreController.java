package com.delivery.presenter.rest.api.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetProductsByStoreIdUseCase;
import com.delivery.core.usecases.store.GetStoreByIdUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
import com.delivery.presenter.rest.api.entities.ProductResponse;
import com.delivery.presenter.rest.api.entities.StoreResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class StoreController implements StoreResource {

    private UseCaseExecutor useCaseExecutor;
    private GetAllStoresUseCase getAllStoresUseCase;
    private SearchStoresByNameUseCase searchStoresByNameUseCase;
    private GetStoreByIdUseCase getStoreByIdUseCase;
    private GetProductsByStoreIdUseCase getProductsByStoreIdUseCase;

    public StoreController(UseCaseExecutor useCaseExecutor,
                           GetAllStoresUseCase getAllStoresUseCase,
                           SearchStoresByNameUseCase searchStoresByNameUseCase,
                           GetStoreByIdUseCase getStoreByIdUseCase,
                           GetProductsByStoreIdUseCase getProductsByStoreIdUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllStoresUseCase = getAllStoresUseCase;
        this.searchStoresByNameUseCase = searchStoresByNameUseCase;
        this.getStoreByIdUseCase = getStoreByIdUseCase;
        this.getProductsByStoreIdUseCase = getProductsByStoreIdUseCase;
    }

    @Override
    public CompletableFuture<List<StoreResponse>> getAll() {
        return useCaseExecutor.execute(
                getAllStoresUseCase,
                null,
                (arg) -> null,
                StoreResponse::from);
    }

    @Override
    public CompletableFuture<List<StoreResponse>> getAllStoresByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchStoresByNameUseCase,
                text,
                (arg) -> arg,
                StoreResponse::from);
    }

    @Override
    public CompletableFuture<StoreResponse> getStoreByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoreByIdUseCase,
                id,
                Identity::new,
                StoreResponse::from);
    }

    @Override
    public CompletableFuture<List<ProductResponse>> getProductsBy(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getProductsByStoreIdUseCase,
                id,
                Identity::new,
                ProductResponse::from);
    }
}
