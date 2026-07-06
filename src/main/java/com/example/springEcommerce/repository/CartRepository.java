package com.example.springEcommerce.repository;

import com.example.springEcommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<CartItem, Long> {

    // Get all cart items for a user
    List<CartItem> findByUserId(Long userId);

    // Find a specific product in a user's cart
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    // Delete all cart items for a user (after order placed)
    @Transactional
    void deleteByUserId(Long userId);
}