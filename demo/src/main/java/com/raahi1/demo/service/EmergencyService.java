package com.raahi1.demo.service;

import com.raahi1.demo.dto.EmergencyTriggerDTO;
import com.raahi1.demo.dto.LocationUpdateDTO;
import com.raahi1.demo.models.EmergencyAlert;
import com.raahi1.demo.repository.EmergencyAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmergencyService {

    private final EmergencyAlertRepository repo;

    @Autowired
    public EmergencyService(EmergencyAlertRepository repo) {
        this.repo = repo;
    }

    // ── Trigger a new emergency ───────────────────────────────────────────
    public EmergencyAlert triggerEmergency(EmergencyTriggerDTO dto) {
        // If there's already an active alert, reuse it
        return repo.findByUserMobileAndStatus(dto.getUserMobile(), "ACTIVE")
            .orElseGet(() -> {
                EmergencyAlert alert = new EmergencyAlert();
                alert.setUserMobile(dto.getUserMobile());
                alert.setUserName(dto.getUserName());
                alert.setLatitude(dto.getLatitude());
                alert.setLongitude(dto.getLongitude());
                return repo.save(alert);
            });
    }

    // ── Update live location ──────────────────────────────────────────────
    public EmergencyAlert updateLocation(Long alertId, LocationUpdateDTO dto) {
        EmergencyAlert alert = repo.findById(alertId)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + alertId));
        alert.setLatitude(dto.getLatitude());
        alert.setLongitude(dto.getLongitude());
        return repo.save(alert);
    }

    // ── Resolve / mark safe ───────────────────────────────────────────────
    public EmergencyAlert resolve(Long alertId) {
        EmergencyAlert alert = repo.findById(alertId)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + alertId));
        alert.setStatus("RESOLVED");
        alert.setResolvedAt(LocalDateTime.now());
        return repo.save(alert);
    }

    // ── Get active alerts visible to a trusted contact ────────────────────
    public List<EmergencyAlert> getActiveAlertsForContact(String trustedMobile) {
        return repo.findActiveAlertsForTrustedContact(trustedMobile);
    }

    // ── Get single alert ──────────────────────────────────────────────────
    public EmergencyAlert getAlert(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));
    }
}
