package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.NotFoundException;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;

public class GetProductByIdUseCase implements UseCase<Identity, Product> {
    private ProductRepository repository;

    public GetProductByIdUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product execute(Identity id) {
        return repository
                .getById(id)
                .orElseThrow(() -> new NotFoundException("Product " + id.getNumber() + " not found"));
    }
}
