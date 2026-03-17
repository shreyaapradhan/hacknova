package com.raahi1.demo.dto;

/**
 * Sent back to the parent dashboard after login.
 * Contains only safe, non-sensitive fields of the linked Raahi user.
 */
public class LinkedUserDTO {

    private Long   id;
    private String fullName;
    private String mobile;
    private String state;
    private String city;

    // Constructor used in UserRepository projection query
    public LinkedUserDTO(Long id, String fullName, String mobile,
                         String state, String city) {
        this.id       = id;
        this.fullName = fullName;
        this.mobile   = mobile;
        this.state    = state;
        this.city     = city;
    }

    public Long   getId()       { return id; }
    public String getFullName() { return fullName; }
    public String getMobile()   { return mobile; }
    public String getState()    { return state; }
    public String getCity()     { return city; }
}
