package com.raahi1.demo.controller;

import com.raahi1.demo.dto.*;
import com.raahi1.demo.models.TrustedContact;
import com.raahi1.demo.service.TrustedContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for the Trusted Contact (Parent Dashboard) flow.
 *
 * Endpoints:
 *   POST /api/trusted/signup          ← Create account
 *   POST /api/trusted/login           ← Sign in
 *   GET  /api/trusted/linked/{mobile} ← Get Raahi users who listed this number
 */
@RestController
@RequestMapping("/api/trusted")
@CrossOrigin(origins = "*")
public class TrustedContactController {

    private final TrustedContactService service;

    @Autowired
    public TrustedContactController(TrustedContactService service) {
        this.service = service;
    }

    // ── POST /api/trusted/signup ──────────────────────────────────────────
    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDTO> signup(
            @RequestBody TrustedContactSignupDTO dto) {
        try {
            TrustedContact saved = service.signup(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDTO(true, "Account created successfully!", saved));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO(false, ex.getMessage()));
        }
    }

    // ── POST /api/trusted/login ───────────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(
            @RequestBody TrustedContactLoginDTO dto) {
        try {
            TrustedContact tc = service.login(dto);

            // Fetch linked Raahi users right after login
            List<LinkedUserDTO> linkedUsers =
                service.getLinkedUsers(tc.getMobile());

            // Build response payload
            java.util.Map<String, Object> payload = new java.util.HashMap<>();
            payload.put("contact", tc);
            payload.put("linkedUsers", linkedUsers);

            return ResponseEntity.ok(
                new ApiResponseDTO(true, "Login successful", payload));

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponseDTO(false, ex.getMessage()));
        }
    }

    // ── GET /api/trusted/linked/{mobile} ─────────────────────────────────
    // Returns all Raahi users who listed this mobile as emergency contact
    @GetMapping("/linked/{mobile}")
    public ResponseEntity<ApiResponseDTO> getLinkedUsers(
            @PathVariable String mobile) {

        List<LinkedUserDTO> users = service.getLinkedUsers(mobile);

        if (users.isEmpty()) {
            return ResponseEntity.ok(
                new ApiResponseDTO(false,
                    "No Raahi users have listed this number as an emergency contact.",
                    users));
        }
        return ResponseEntity.ok(
            new ApiResponseDTO(true,
                "Found " + users.size() + " linked user(s)", users));
    }
}
