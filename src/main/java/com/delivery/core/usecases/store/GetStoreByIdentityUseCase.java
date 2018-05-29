package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

public class GetStoreByIdentityUseCase implements UseCase<Identity, Store> {
    private StoreRepository repository;

    public GetStoreByIdentityUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Store execute(Identity id) {
        return repository
                .getByIdentity(id)
                .orElseThrow(() -> new NotFoundException("No store found by identity: " + id.getNumber()));
    }
}
