package com.example.springEcommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    // Address details
    private String fullName;
    private String phone;
    private String address;
    private String city;
    private String pincode;

    // Order info
    private double totalAmount;
    private String status = "PLACED";  // PLACED, SHIPPED, DELIVERED

    // When order was placed
    private LocalDateTime createdAt = LocalDateTime.now();
}
