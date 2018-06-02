package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<ProductData, Long> {
    List<ProductData> findByNameContainingOrDescriptionContainingAllIgnoreCase(String name, String description);

    List<ProductData> findByStoreIdAndIdIsIn(Long id, List<Long> ids);
}
