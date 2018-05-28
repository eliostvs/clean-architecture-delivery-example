package com.delivery.presenter.rest.api.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.usecases.UseCaseExecutor;
import com.delivery.core.usecases.cousine.GetAllCousinesUseCase;
import com.delivery.core.usecases.cousine.GetCousineByIdentityUserCase;
import com.delivery.core.usecases.cousine.SearchCousineByNameUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class CousineController implements CousineResource {
    private UseCaseExecutor useCaseExecutor;
    private GetAllCousinesUseCase getAllCousinesUseCase;
    private GetCousineByIdentityUserCase getCousineByIdentityUserCase;
    private SearchCousineByNameUseCase searchCousineByNameUseCase;

    public CousineController(UseCaseExecutor useCaseExecutor,
                             GetAllCousinesUseCase getAllCousinesUseCase,
                             GetCousineByIdentityUserCase getCousineByIdentityUserCase,
                             SearchCousineByNameUseCase searchCousineByNameUseCase) {
        this.useCaseExecutor = useCaseExecutor;
        this.getAllCousinesUseCase = getAllCousinesUseCase;
        this.getCousineByIdentityUserCase = getCousineByIdentityUserCase;
        this.searchCousineByNameUseCase = searchCousineByNameUseCase;
    }

    @Override
    public CompletableFuture<CousineResponse> getCousineById(@PathVariable Long id) {
        return useCaseExecutor.execute(
                getCousineByIdentityUserCase,
                id,
                Identity::new,
                CousineResponse::fromCousine);
    }

    @Override
    public CompletableFuture<List<CousineResponse>> getAllCousines() {
        return useCaseExecutor.execute(
                getAllCousinesUseCase,
                null,
                (arg) -> null,
                CousineResponse::fromCousine);
    }

    @Override
    public CompletableFuture<List<CousineResponse>> getByNameMatching(@PathVariable String text) {
        return useCaseExecutor.execute(
                searchCousineByNameUseCase,
                text,
                (searchText) -> searchText,
                CousineResponse::fromCousine);
    }
}
