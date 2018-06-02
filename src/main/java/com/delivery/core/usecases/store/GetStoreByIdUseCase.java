package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

public class GetStoreByIdUseCase implements UseCase<Identity, Store> {
    private StoreRepository repository;

    public GetStoreByIdUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Store execute(Identity id) {
        return repository
                .getById(id)
                .orElseThrow(() -> new NotFoundException("No store found by identity: " + id.getNumber()));
    }
}
