package com.example.springEcommerce.repository;

import com.example.springEcommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Get all items for an order
    List<OrderItem> findByOrderId(Long orderId);
}
