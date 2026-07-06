package com.example.springEcommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which user added this item
    private Long userId;

    // Which product was added
    private Long productId;

    // How many of this product
    private int quantity;

    // We save price at the time of adding
    // (in case product price changes later)
    private double price;

    // Product name — saved so we don't need to join tables
    private String productName;

    // Product image — saved for display
    private String imageUrl;
}