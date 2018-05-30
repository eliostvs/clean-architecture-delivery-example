package com.delivery.core.usecases.product;

import com.delivery.core.domain.Product;
import com.delivery.core.usecases.UseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchProductsByNameOrDescriptionUseCase implements UseCase<String, List<Product>> {
    private ProductRepository repository;

    public SearchProductsByNameOrDescriptionUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> execute(String searchText) {
        return repository.searchByNameOrDescription(searchText);
    }
}
