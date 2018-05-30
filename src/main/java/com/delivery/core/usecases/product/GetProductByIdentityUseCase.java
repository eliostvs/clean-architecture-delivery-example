package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;

public class GetProductByIdentityUseCase implements UseCase<Identity, Product> {
    private ProductRepository repository;

    public GetProductByIdentityUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product execute(Identity id) {
        return repository
                .getByIdentity(id)
                .orElseThrow(() -> new NotFoundException("No product found by identity: " + id.getNumber()));
    }
}
