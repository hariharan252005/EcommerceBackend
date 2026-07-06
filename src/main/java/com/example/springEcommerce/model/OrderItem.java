package com.example.springEcommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;      // which order this belongs to
    private Long productId;
    private String productName;
    private String imageUrl;
    private double price;
    private int quantity;
}
