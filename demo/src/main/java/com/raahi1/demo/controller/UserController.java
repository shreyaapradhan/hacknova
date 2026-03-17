package com.raahi1.demo.controller;

import com.raahi1.demo.dto.ApiResponseDTO;
import com.raahi1.demo.dto.UserRegistrationDTO;
import com.raahi1.demo.models.User;
import com.raahi1.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO> register(
            @RequestBody UserRegistrationDTO dto) {
        try {
            User saved = userService.registerUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO(true, "Registered successfully!", saved));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseDTO(false, ex.getMessage()));
        }
    }

    // ── NEW: called by index.html handleLogin() ──
    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<ApiResponseDTO> getByMobile(@PathVariable String mobile) {
        try {
            User user = userService.getUserByMobile(mobile);
            return ResponseEntity.ok(
                    new ApiResponseDTO(true, "Found", user));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO(false, ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO> getAllUsers() {
        return ResponseEntity.ok(
                new ApiResponseDTO(true, "Success", userService.getAllUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    new ApiResponseDTO(true, "Found", userService.getUserById(id)));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO(false, ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponseDTO(true, "Deleted successfully"));
    }
}