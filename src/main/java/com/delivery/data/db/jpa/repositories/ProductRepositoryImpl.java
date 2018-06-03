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
public class ProductRepositoryImpl implements ProductRepository {
    private JpaProductRepository repository;

    public ProductRepositoryImpl(JpaProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAll() {
        return repository
                .findAll()
                .stream()
                .map(ProductData::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> getById(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(ProductData::fromThis);
    }

    @Override
    public List<Product> searchByNameOrDescription(String searchText) {
        return repository
                .findByNameContainingOrDescriptionContainingAllIgnoreCase(searchText, searchText)
                .stream()
                .map(ProductData::fromThis)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProductsByStoreAndProductsId(Identity storeId, List<Identity> productsId) {
        return repository
                .findByStoreIdAndIdIsIn(storeId.getNumber(), createListOfLong(productsId))
                .stream()
                .map(ProductData::fromThis)
                .collect(Collectors.toList());
    }

    private List<Long> createListOfLong(List<Identity> productsId) {
        return productsId
                .stream()
                .map(Identity::getNumber)
                .collect(Collectors.toList());
    }
}
