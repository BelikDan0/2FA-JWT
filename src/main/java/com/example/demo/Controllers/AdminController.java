package com.example.demo.Controllers;

import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestHeader(value = "Authorization", required = false) String auth) {

        if (auth == null || !auth.equals("Bearer JWT_ADMIN_PLACEHOLDER")) {
            return ResponseEntity.status(403).body("Access denied");
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/users")
    public UserDto createUser(@RequestBody UserDto dto) {
        return userService.createUser(dto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
