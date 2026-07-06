package com.example.springEcommerce.repository;

import com.example.springEcommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Get all orders for a user
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}