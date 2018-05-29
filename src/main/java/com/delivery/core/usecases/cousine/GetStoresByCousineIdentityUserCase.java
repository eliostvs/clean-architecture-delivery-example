package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class GetStoresByCousineIdentityUserCase implements UseCase<Identity, List<Store>> {
    private CousineRepository repository;

    public GetStoresByCousineIdentityUserCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> execute(Identity id) {
        List<Store> stores = repository.getStoresByIdentity(id);

        if (stores.isEmpty()) {
            throw new NotFoundException("No cousine found for identity: " + id.getNumber());
        }

        return stores;
    }
}
