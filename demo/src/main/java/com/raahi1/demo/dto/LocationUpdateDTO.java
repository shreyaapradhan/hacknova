package com.raahi1.demo.dto;

/** Sent every 5 seconds from user's device to update live location */
public class LocationUpdateDTO {
    private Double latitude;
    private Double longitude;

    public Double getLatitude()    { return latitude; }
    public Double getLongitude()   { return longitude; }
    public void setLatitude(Double lat)  { this.latitude = lat; }
    public void setLongitude(Double lng) { this.longitude = lng; }
}
