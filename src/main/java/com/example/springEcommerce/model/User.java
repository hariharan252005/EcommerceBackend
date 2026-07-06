package com.example.springEcommerce.model;

import jakarta.persistence.*;
import lombok.Data;

// @Data = auto creates getters & setters (lombok)
// @Entity = this class = database table
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // auto number: 1, 2, 3...

    private String name;      // full name

    @Column(unique = true)    // no two users same email
    private String email;

    private String password;  // will be encrypted, never plain text

    private String role = "USER";  // USER or ADMIN
}