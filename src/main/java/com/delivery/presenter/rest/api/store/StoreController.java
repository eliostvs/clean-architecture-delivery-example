package com.delivery.presenter.rest.api.store;

import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.SearchStoresByNameUseCase;
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

    public StoreController(UseCaseExecutor useCaseExecutor,
                           GetAllStoresUseCase getAllStoresUseCase,
                           SearchStoresByNameUseCase searchStoresByNameUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllStoresUseCase = getAllStoresUseCase;
        this.searchStoresByNameUseCase = searchStoresByNameUseCase;
    }

    @Override
    public CompletableFuture<List<StoreResponse>> getAll() {
        return useCaseExecutor.execute(
                getAllStoresUseCase,
                null,
                (arg) -> null,
                StoreResponse::fromStore);
    }

    @Override
    public CompletableFuture<List<StoreResponse>> getAllStoresByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchStoresByNameUseCase,
                text,
                (arg) -> arg,
                StoreResponse::fromStore);
    }
}
