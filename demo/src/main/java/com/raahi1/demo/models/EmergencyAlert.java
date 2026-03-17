package com.raahi1.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tracks an active emergency for a Raahi user.
 * Created when user presses 991= or taps "No, I'm unsafe".
 */
@Entity
@Table(name = "emergency_alerts")
public class EmergencyAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The Raahi user's mobile who triggered the emergency
    @Column(name = "user_mobile", nullable = false, length = 10)
    private String userMobile;

    @Column(name = "user_name", length = 150)
    private String userName;

    // Latest GPS coordinates (updated every 5 seconds from user's device)
    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // ACTIVE = emergency ongoing, RESOLVED = user marked safe
    @Column(name = "status", length = 20)
    private String status; // "ACTIVE" | "RESOLVED"

    @Column(name = "triggered_at", updatable = false)
    private LocalDateTime triggeredAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        this.triggeredAt = LocalDateTime.now();
        this.status      = "ACTIVE";
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public Long getId()                  { return id; }
    public String getUserMobile()        { return userMobile; }
    public String getUserName()          { return userName; }
    public Double getLatitude()          { return latitude; }
    public Double getLongitude()         { return longitude; }
    public String getStatus()            { return status; }
    public LocalDateTime getTriggeredAt(){ return triggeredAt; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }

    // ── Setters ──────────────────────────────────────────────────────────
    public void setId(Long id)                       { this.id = id; }
    public void setUserMobile(String m)              { this.userMobile = m; }
    public void setUserName(String n)                { this.userName = n; }
    public void setLatitude(Double lat)              { this.latitude = lat; }
    public void setLongitude(Double lng)             { this.longitude = lng; }
    public void setStatus(String s)                  { this.status = s; }
    public void setResolvedAt(LocalDateTime t)       { this.resolvedAt = t; }
}
