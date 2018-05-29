package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

import java.util.Set;

public class GetStoresByCousineIdentityUserCase implements UseCase<Identity, Set<Store>> {
    private CousineRepository repository;

    public GetStoresByCousineIdentityUserCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Store> execute(Identity id) {
        Set<Store> stores = repository.getStoresByIdentity(id);

        if (stores.isEmpty()) {
            throw new NotFoundException("No cousine found for identity: " + id.getNumber());
        }

        return stores;
    }
}
