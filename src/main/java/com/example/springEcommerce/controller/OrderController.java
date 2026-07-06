package com.example.springEcommerce.controller;

import com.example.springEcommerce.model.*;
import com.example.springEcommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    // ── PLACE ORDER ───────────────────────────────────────────
    // POST /orders/place
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody Map<String, Object> body) {

        Long userId     = Long.valueOf(body.get("userId").toString());
        String fullName = body.get("fullName").toString();
        String phone    = body.get("phone").toString();
        String address  = body.get("address").toString();
        String city     = body.get("city").toString();
        String pincode  = body.get("pincode").toString();
        double total    = Double.parseDouble(body.get("totalAmount").toString());

        // Get cart items for this user
        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        // Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setFullName(fullName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setCity(city);
        order.setPincode(pincode);
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);

        // Save each cart item as order item
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemRepository.save(orderItem);
        }

        // Clear cart after order placed
        cartRepository.deleteByUserId(userId);

        return ResponseEntity.ok(savedOrder);
    }

    // ── GET all orders for a user ─────────────────────────────
    // GET /orders/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // ── GET items of a specific order ─────────────────────────
    // GET /orders/{orderId}/items
    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(@PathVariable Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
