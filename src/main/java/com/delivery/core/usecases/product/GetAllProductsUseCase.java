package com.delivery.core.usecases.product;

import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;

import java.util.List;

public class GetAllProductsUseCase implements UseCase<Void, List<Product>> {
    private ProductRepository repository;

    public GetAllProductsUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> execute(Void input) {
        return repository.getAll();
    }
}
