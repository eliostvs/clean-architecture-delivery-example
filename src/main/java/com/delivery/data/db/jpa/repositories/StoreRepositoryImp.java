package com.delivery.data.db.jpa.repositories;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;
import com.delivery.core.domain.Store;
import com.delivery.core.usecases.store.StoreRepository;
import com.delivery.data.db.jpa.entities.ProductData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StoreRepositoryImp implements StoreRepository {
    private JpaStoreRepository repository;

    public StoreRepositoryImp(JpaStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Store> getAll() {
        return repository
                .findAll()
                .parallelStream()
                .map(StoreData::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Store> searchByName(String searchText) {
        return repository
                .findByNameContainingIgnoreCase(searchText)
                .parallelStream()
                .map(StoreData::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Store> getByIdentity(Identity id) {
        return repository
                .findById(id.getNumber())
                .map(StoreData::toDomain);
    }

    @Override
    public List<Product> getProductsByIdentity(Identity id) {
        return repository
                .findProductsById(id.getNumber())
                .parallelStream()
                .map(ProductData::from)
                .collect(Collectors.toList());
    }
}
