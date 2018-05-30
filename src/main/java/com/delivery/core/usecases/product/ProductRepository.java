package com.delivery.core.usecases.product;

import com.delivery.core.domain.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> getAll();
}
