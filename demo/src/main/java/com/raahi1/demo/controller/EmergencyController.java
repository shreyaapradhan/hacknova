package com.raahi1.demo.controller;

import com.raahi1.demo.dto.ApiResponseDTO;
import com.raahi1.demo.dto.EmergencyTriggerDTO;
import com.raahi1.demo.dto.LocationUpdateDTO;
import com.raahi1.demo.models.EmergencyAlert;
import com.raahi1.demo.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST endpoints for the emergency system.
 *
 * POST   /api/emergency/trigger                ← User triggers SOS
 * POST   /api/emergency/location/{alertId}     ← User sends live GPS
 * GET    /api/emergency/active/{trustedMobile} ← Trusted contact polls for alerts
 * GET    /api/emergency/alert/{alertId}        ← Get single alert (location refresh)
 * POST   /api/emergency/resolve/{alertId}      ← Mark user safe
 */
@RestController
@RequestMapping("/api/emergency")
@CrossOrigin(origins = "*")
public class EmergencyController {

    private final EmergencyService service;

    @Autowired
    public EmergencyController(EmergencyService service) {
        this.service = service;
    }

    // ── Trigger SOS ───────────────────────────────────────────────────────
    @PostMapping("/trigger")
    public ResponseEntity<ApiResponseDTO> trigger(
            @RequestBody EmergencyTriggerDTO dto) {
        EmergencyAlert alert = service.triggerEmergency(dto);
        return ResponseEntity.ok(
            new ApiResponseDTO(true, "Emergency triggered", alert));
    }

    // ── Update live GPS ───────────────────────────────────────────────────
    @PostMapping("/location/{alertId}")
    public ResponseEntity<ApiResponseDTO> updateLocation(
            @PathVariable Long alertId,
            @RequestBody LocationUpdateDTO dto) {
        EmergencyAlert alert = service.updateLocation(alertId, dto);
        return ResponseEntity.ok(
            new ApiResponseDTO(true, "Location updated", alert));
    }

    // ── Poll for active alerts (trusted contact dashboard) ─────────────────
    @GetMapping("/active/{trustedMobile}")
    public ResponseEntity<ApiResponseDTO> getActiveAlerts(
            @PathVariable String trustedMobile) {
        List<EmergencyAlert> alerts = service.getActiveAlertsForContact(trustedMobile);
        return ResponseEntity.ok(
            new ApiResponseDTO(true, "Active alerts", alerts));
    }

    // ── Get single alert (for location refresh) ───────────────────────────
    @GetMapping("/alert/{alertId}")
    public ResponseEntity<ApiResponseDTO> getAlert(
            @PathVariable Long alertId) {
        EmergencyAlert alert = service.getAlert(alertId);
        return ResponseEntity.ok(
            new ApiResponseDTO(true, "Alert data", alert));
    }

    // ── Resolve / mark safe ───────────────────────────────────────────────
    @PostMapping("/resolve/{alertId}")
    public ResponseEntity<ApiResponseDTO> resolve(
            @PathVariable Long alertId) {
        EmergencyAlert alert = service.resolve(alertId);
        return ResponseEntity.ok(
            new ApiResponseDTO(true, "Emergency resolved", alert));
    }
}
