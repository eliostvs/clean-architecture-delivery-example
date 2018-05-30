package com.delivery.core.usecases.product;

import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchProductsByNameUseCase implements UseCase<String, List<Product>> {
    private ProductRepository repository;

    public SearchProductsByNameUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> execute(String text) {
        return repository.searchByName(text);
    }
}
