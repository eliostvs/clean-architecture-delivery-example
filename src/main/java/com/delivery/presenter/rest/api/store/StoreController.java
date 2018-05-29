package com.delivery.presenter.rest.api.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.store.GetAllStoresUseCase;
import com.delivery.core.usecases.store.GetStoreByIdentityUseCase;
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
    private GetStoreByIdentityUseCase getStoreByIdentityUseCase;

    public StoreController(UseCaseExecutor useCaseExecutor,
                           GetAllStoresUseCase getAllStoresUseCase,
                           SearchStoresByNameUseCase searchStoresByNameUseCase,
                           GetStoreByIdentityUseCase getStoreByIdentityUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllStoresUseCase = getAllStoresUseCase;
        this.searchStoresByNameUseCase = searchStoresByNameUseCase;
        this.getStoreByIdentityUseCase = getStoreByIdentityUseCase;
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

    @Override
    public CompletableFuture<StoreResponse> getStoreByIdentity(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoreByIdentityUseCase,
                id,
                Identity::new,
                StoreResponse::fromStore);
    }
}
