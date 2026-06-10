package com.example.springEcommerce.repository;

import com.example.springEcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findByName(String name);

    List<Product> findByCategory(String category);

    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findByCategoryAndName(String category, String name);
}
