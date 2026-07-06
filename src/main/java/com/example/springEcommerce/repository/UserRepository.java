package com.example.springEcommerce.repository;

import com.example.springEcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// JpaRepository gives us save(), findAll(), findById() for free
public interface UserRepository extends JpaRepository<User, Long> {

    // We need one extra method: find user by email
    // Spring writes the SQL for us automatically!
    Optional<User> findByEmail(String email);
}