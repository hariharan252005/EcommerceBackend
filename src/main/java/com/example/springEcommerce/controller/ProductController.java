package com.example.springEcommerce.controller;

import com.example.springEcommerce.model.Product;
import com.example.springEcommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping
    public List<Product> getAllProduct(){
        return service.getAllProduct();
    }
    @PostMapping
    public Product addProduct(@Valid @RequestBody Product p){
        return service.addProduct(p);
    }
    @PostMapping("/bulk")
    public List<Product> addMoreProduct(@RequestBody List<Product> p){
        return service.addMoreProduct(p);
    }
    @GetMapping("/find/id/{id}")
    public Product findById(@PathVariable int id){
        return service.findById(id);
    }
    @GetMapping("/find/name/{name}")
    public Product findByName(@PathVariable String name){
        return service.findByName(name);
    }
    @GetMapping("/{category}")
    public List<Product> findByCategory(@PathVariable String category){
        return service.findByCategory(category);
    }
    @GetMapping("/{category}/{name}")
    public List<Product> findByCategoryAndName(@PathVariable String category,String name){
        return service.findByCategoryAndName(category,name);
    }
    @GetMapping("/count")
    public long countProducts(){
        return service.countProducts();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable int id){
         service.deleteById(id);
         return "Deleted Successfully";
    }
    @PutMapping("/update/{name}")
    public Product updateProduct(@PathVariable String name,@RequestBody Product product){
        return service.updateProduct(name,product);
    }
    @GetMapping("/page")
    public Page<Product> getProducts(
            @RequestParam int page,
            @RequestParam int size) {
        return service.getProducts(page, size);
    }
    @GetMapping("/sort/price")
    public List<Product> sortByPrice() {
        return service.getProductsSortedByPrice();
    }
    @GetMapping("/price/{min}/{max}")
    public List<Product> getProductBetweenPrice(@PathVariable Double min,@PathVariable Double max){
        return service.getProductsBetweenPrice(min,max);
    }
    @DeleteMapping("/deleteall")
    public String deleteAllProducts() {
        service.deleteAll();
        return "All products deleted successfully";
    }
}
