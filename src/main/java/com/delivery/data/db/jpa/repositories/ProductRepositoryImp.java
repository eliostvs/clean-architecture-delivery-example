package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.usecases.product.ProductRepository;
import com.delivery.data.db.jpa.entities.ProductData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImp implements ProductRepository {
    private JpaProductRepository repository;

    public ProductRepositoryImp(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return repository
                .findAll()
                .parallelStream()
                .map(ProductData::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getByIdentity(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(ProductData::toDomain);
    }

    @Override
    public List<Product> searchByName(String searchText) {
        return repository
                .findByNameContainingIgnoreCase(searchText)
                .parallelStream()
                .map(ProductData::toDomain)
                .collect(Collectors.toList());
    }
}
