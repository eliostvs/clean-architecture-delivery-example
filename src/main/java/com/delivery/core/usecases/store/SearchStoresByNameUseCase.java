package com.delivery.core.usecases.store;

import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class SearchStoresByNameUseCase implements UseCase<String, List<Store>> {
    private StoreRepository repository;

    public SearchStoresByNameUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> execute(String searchText) {
        return repository.searchByName(searchText);
    }
}
