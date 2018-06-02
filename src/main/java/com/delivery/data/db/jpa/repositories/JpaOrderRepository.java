package com.delivery.data.db.jpa.repositories;

import com.delivery.data.db.jpa.entities.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<OrderData, Long> {
}
