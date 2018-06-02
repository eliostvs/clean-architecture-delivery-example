package com.delivery.core.usecases.cousine;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class GetStoresByCousineIdUseCase implements UseCase<Identity, List<Store>> {
    private CousineRepository repository;

    public GetStoresByCousineIdUseCase(CousineRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> execute(Identity id) {
        List<Store> stores = repository.getStoresById(id);

        if (stores.isEmpty()) {
            // TODO: Use a simpler exception message
            throw new NotFoundException("Cousine " + id.getNumber() + " not found");
        }

        return stores;
    }
}
