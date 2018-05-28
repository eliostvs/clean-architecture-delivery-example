package com.delivery.data.db.jpa.cousine;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCousineRepository extends JpaRepository<CousineData, Long> {
    List<CousineData> findByNameContainingIgnoreCase(String search);
}
