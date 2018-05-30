package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<ProductData, Long> {
}
