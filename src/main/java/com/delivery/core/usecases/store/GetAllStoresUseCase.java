package com.delivery.core.usecases.store;

import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class GetAllStoresUseCase implements UseCase<Void, List<Store>> {
    private StoreRepository repository;

    public GetAllStoresUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> execute(Void input) {
        return repository.getAll();
    }
}
