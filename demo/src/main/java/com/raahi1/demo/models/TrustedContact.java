package com.raahi1.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a trusted/parent contact who can log in
 * and view the Raahi users who listed them as an emergency contact.
 */
@Entity
@Table(name = "trusted_contacts")
public class TrustedContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "mobile", nullable = false, unique = true, length = 10)
    private String mobile;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ── Getters ──────────────────────────────────────────────────────────
    public Long getId()            { return id; }
    public String getFullName()    { return fullName; }
    public String getMobile()      { return mobile; }
    public String getEmail()       { return email; }
    public String getPassword()    { return password; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ── Setters ──────────────────────────────────────────────────────────
    public void setId(Long id)             { this.id = id; }
    public void setFullName(String n)      { this.fullName = n; }
    public void setMobile(String m)        { this.mobile = m; }
    public void setEmail(String e)         { this.email = e; }
    public void setPassword(String p)      { this.password = p; }
    public void setCreatedAt(LocalDateTime c) { this.createdAt = c; }
}
