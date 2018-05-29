package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.CousineData;
import com.delivery.data.db.jpa.entities.StoreData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaCousineRepository extends JpaRepository<CousineData, Long> {
    List<CousineData> findByNameContainingIgnoreCase(String search);

    @Query("select s from cousine c join c.stores s where c.id = ?1")
    List<StoreData> findStoresById(Long id);
}
