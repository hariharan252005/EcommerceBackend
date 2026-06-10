package com.example.springEcommerce.service;

import com.example.springEcommerce.exception.ProductNotFoundException;
import com.example.springEcommerce.model.Product;
import com.example.springEcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository repo;

    public Product addProduct(Product p){
        return repo.save(p);
    }
    public List<Product> getAllProduct(){
        return repo.findAll();
    }
    public Product findById(int id){
        return repo.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found with ID: " + id));
    }
    public Product findByName(String name) {
        return repo.findByName(name);
    }
    public List<Product> findByCategory(String category){
        return repo.findByCategory(category);
    }
    public void deleteById(int id){
         repo.deleteById(id);
    }

    public Product updateProduct(String name, Product product) {
        Product existingProduct = repo.findByName(name);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setDescription(product.getDescription());
            return repo.save(existingProduct);
        }
        return null;
    }
    public long countProducts() {
        return repo.count();
    }
    public Page<Product> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable);
    }
    public List<Product> getProductsSortedByPrice() {
        return repo.findAll(Sort.by("price"));
    }
    public List<Product> getProductsBetweenPrice(Double min,Double max){
        return repo.findByPriceBetween(min,max);
    }

    public List<Product> findByCategoryAndName(String category, String name) {
        return repo.findByCategoryAndName(category,name);
    }
}
