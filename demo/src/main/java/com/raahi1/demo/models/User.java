package com.raahi1.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "mobile", nullable = false, unique = true)
    private String mobile;

    @Column(name = "building")
    private String building;

    @Column(name = "street")
    private String street;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "contact1")
    private String contact1;

    @Column(name = "contact2")
    private String contact2;

    @Column(name = "contact3")
    private String contact3;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ── Getters ──────────────────────────────
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getMobile() { return mobile; }
    public String getBuilding() { return building; }
    public String getStreet() { return street; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getContact1() { return contact1; }
    public String getContact2() { return contact2; }
    public String getContact3() { return contact3; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ── Setters ──────────────────────────────
    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setBuilding(String building) { this.building = building; }
    public void setStreet(String street) { this.street = street; }
    public void setState(String state) { this.state = state; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }
    public void setContact1(String contact1) { this.contact1 = contact1; }
    public void setContact2(String contact2) { this.contact2 = contact2; }
    public void setContact3(String contact3) { this.contact3 = contact3; }
}