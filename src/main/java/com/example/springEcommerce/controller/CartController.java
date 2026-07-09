package com.example.springEcommerce.controller;

import com.example.springEcommerce.model.CartItem;
import com.example.springEcommerce.model.Product;
import com.example.springEcommerce.repository.CartRepository;
import com.example.springEcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // ── GET all cart items for a user ─────────────────────────
    // GET /cart/{userId}
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // ── ADD item to cart ──────────────────────────────────────
    // POST /cart/add
    // Body: { "userId": 1, "productId": 2 }
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Long> body) {

        Long userId    = body.get("userId");
        Long productId = body.get("productId");

        // Check product exists
        Optional<Product> productOpt = productRepository.findById(Math.toIntExact(productId));
        if (productOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        Product product = productOpt.get();

        // Check if this product already in user's cart
        Optional<CartItem> existing = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent()) {
            // Already in cart — just increase quantity
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + 1);
            cartRepository.save(item);
            return ResponseEntity.ok(item);
        } else {
            // Not in cart — add new item
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            newItem.setPrice(product.getPrice());
            newItem.setProductName(product.getName());
            newItem.setImageUrl(product.getImageUrl());
            cartRepository.save(newItem);
            return ResponseEntity.ok(newItem);
        }
    }

    // ── REMOVE item from cart ─────────────────────────────────
    // DELETE /cart/remove/{cartItemId}
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartItemId) {
        cartRepository.deleteById(cartItemId);
        return ResponseEntity.ok("Item removed");
    }

    // ── UPDATE quantity ───────────────────────────────────────
    // PUT /cart/update/{cartItemId}
    // Body: { "quantity": 3 }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateQuantity(
            @PathVariable Long cartItemId,
            @RequestBody Map<String, Integer> body) {

        int quantity = body.get("quantity");

        Optional<CartItem> itemOpt = cartRepository.findById(cartItemId);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart item not found");
        }

        CartItem item = itemOpt.get();

        // If quantity is 0 or less, remove the item
        if (quantity <= 0) {
            cartRepository.deleteById(cartItemId);
            return ResponseEntity.ok("Item removed");
        }

        item.setQuantity(quantity);
        cartRepository.save(item);
        return ResponseEntity.ok(item);
    }

    // ── CLEAR entire cart for a user ──────────────────────────
    // DELETE /cart/clear/{userId}
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        cartRepository.deleteByUserId(userId);
        return ResponseEntity.ok("Cart cleared");
    }
}