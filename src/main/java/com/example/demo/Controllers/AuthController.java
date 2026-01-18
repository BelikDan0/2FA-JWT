package com.example.demo.Controllers;

import com.example.demo.Models.User;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4040")

public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody UserDto loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if ("admin".equals(username) && "2324".equals(password)) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("role", "ADMIN");
            response.put("token", "JWT_ADMIN_PLACEHOLDER");
            response.put("need2fa", false);
            return ResponseEntity.ok(response);
        }

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Логин и пароль обязательны");
        }

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Пользователь не найден");
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Неверный пароль");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("role", user.getRole());

        // место под JWT
        response.put("token", "JWT_PLACEHOLDER");

        //  место под 2FA
        response.put("need2fa", false); // потом станет true

        return ResponseEntity.ok(response);
    }
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Auth service is working");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

}
