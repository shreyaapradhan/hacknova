package com.raahi1.demo.dto;

/**
 * DTO for trusted contact login form.
 * Matches the HTML login fields: mobile + password
 */
public class TrustedContactLoginDTO {

    private String mobile;
    private String password;

    public String getMobile()   { return mobile; }
    public String getPassword() { return password; }

    public void setMobile(String mobile)     { this.mobile = mobile; }
    public void setPassword(String password) { this.password = password; }
}
