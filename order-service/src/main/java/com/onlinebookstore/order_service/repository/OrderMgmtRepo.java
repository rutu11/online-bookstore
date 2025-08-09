package com.onlinebookstore.order_service.repository;

import com.onlinebookstore.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMgmtRepo extends JpaRepository<Order, Integer> {
}
