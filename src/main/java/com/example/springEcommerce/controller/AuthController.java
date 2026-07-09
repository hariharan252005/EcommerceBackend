package com.example.springEcommerce.controller;

import com.example.springEcommerce.model.User;
import com.example.springEcommerce.repository.UserRepository;
import com.example.springEcommerce.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {

    // Spring automatically gives us these objects
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ── REGISTER ──────────────────────────────────────────────
    // POST /auth/register
    // Body: { "name":"Ayyan", "email":"a@a.com", "password":"123" }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // 1. Check email not already used
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        // 2. Encrypt password before saving
        // NEVER save plain text passwords!
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 3. Save to database
        userRepository.save(user);

        return ResponseEntity.ok("Registered successfully!");
    }

    // ── LOGIN ─────────────────────────────────────────────────
    // POST /auth/login
    // Body: { "email":"a@a.com", "password":"123" }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String email    = body.get("email");
        String password = body.get("password");

        // 1. Find user by email
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        User user = found.get();

        // 2. Check password matches
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.badRequest().body("Wrong password");
        }

        // 3. Create JWT token
        String token = jwtUtil.generateToken(email);

        // 4. Send token back to React
        return ResponseEntity.ok(Map.of(
                "token", token,
                "name",  user.getName(),
                "email", user.getEmail(),
                "userId", user.getId(),
                "role",   user.getRole()

        ));
    }
}
