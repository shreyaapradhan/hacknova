package com.raahi1.demo.dto;

/** Sent by user's device to trigger emergency */
public class EmergencyTriggerDTO {
    private String userMobile;
    private String userName;
    private Double latitude;
    private Double longitude;

    public String getUserMobile()  { return userMobile; }
    public String getUserName()    { return userName; }
    public Double getLatitude()    { return latitude; }
    public Double getLongitude()   { return longitude; }

    public void setUserMobile(String m)  { this.userMobile = m; }
    public void setUserName(String n)    { this.userName = n; }
    public void setLatitude(Double lat)  { this.latitude = lat; }
    public void setLongitude(Double lng) { this.longitude = lng; }
}
