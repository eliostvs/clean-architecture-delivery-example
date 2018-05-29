package com.delivery.core.usecases.store;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class GetProductsByStoreIdentityUseCase implements UseCase<Identity, List<Product>> {
    private StoreRepository repository;

    public GetProductsByStoreIdentityUseCase(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> execute(Identity id) {
        List<Product> products = repository.getProductsByIdentity(id);

        if (products.isEmpty()) {
            throw new NotFoundException("No store found by identity: " + id.getNumber());
        }

        return products;
    }
}
