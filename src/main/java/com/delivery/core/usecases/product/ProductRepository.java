package com.delivery.core.usecases.product;

import com.delivery.core.domain.Identity;
import com.delivery.core.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAll();

    Optional<Product> getById(Identity id);

    List<Product> searchByNameOrDescription(String searchText);

    List<Product> findProductsByStoreAndProductsId(Identity storeId, List<Identity> productsId);
}
