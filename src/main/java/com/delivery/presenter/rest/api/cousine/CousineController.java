package com.delivery.presenter.rest.api.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetStoresByCousineIdentityUserCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import com.delivery.presenter.rest.api.entities.CousineResponse;
import com.delivery.presenter.rest.api.entities.StoreResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CousineController implements CousineResource {
    private UseCaseExecutor useCaseExecutor;
    private GetAllCousinesUseCase getAllCousinesUseCase;
    private GetStoresByCousineIdentityUserCase getStoresByCousineIdentityUserCase;
    private SearchCousineByNameUseCase getAllCousinesByNameMatching;

    public CousineController(UseCaseExecutor useCaseExecutor,
                             GetAllCousinesUseCase getAllCousinesUseCase,
                             GetStoresByCousineIdentityUserCase getStoresByCousineIdentityUserCase,
                             SearchCousineByNameUseCase getAllCousinesByNameMatching) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllCousinesUseCase = getAllCousinesUseCase;
        this.getStoresByCousineIdentityUserCase = getStoresByCousineIdentityUserCase;
        this.getAllCousinesByNameMatching = getAllCousinesByNameMatching;
    }

    @Override
    public CompletableFuture<List<StoreResponse>> getStoresByCousineId(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getStoresByCousineIdentityUserCase,
                id,
                Identity::new,
                StoreResponse::fromDomain);
    }

    @Override
    public CompletableFuture<List<CousineResponse>> getAllCousines() {
        return useCaseExecutor.execute(
                getAllCousinesUseCase,
                null,
                (arg) -> null,
                CousineResponse::fromDomain);
    }

    @Override
    public CompletableFuture<List<CousineResponse>> getAllCousinesByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                getAllCousinesByNameMatching,
                text,
                (searchText) -> searchText,
                CousineResponse::fromDomain);
    }
}
