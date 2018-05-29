package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.ProductData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaStoreRepository extends JpaRepository<StoreData, Long> {
    List<StoreData> findByNameContainingIgnoreCase(String name);

    @Query("select p from store s join s.products p where s.id = ?1")
    List<ProductData> findProductsById(Long id);
}
