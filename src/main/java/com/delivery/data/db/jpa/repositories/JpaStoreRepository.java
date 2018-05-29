package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.StoreData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStoreRepository extends JpaRepository<StoreData, Long> {
}
