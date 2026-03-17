package com.raahi1.demo.dto;

/**
 * Used for WebRTC peer connection signaling over WebSocket.
 * type: "offer" | "answer" | "ice-candidate"
 * payload: SDP string or ICE candidate JSON string
 * fromMobile: sender's mobile (to route back)
 * toMobile: recipient's mobile
 */
public class SignalDTO {
    private String type;
    private String payload;
    private String fromMobile;
    private String toMobile;

    public String getType()        { return type; }
    public String getPayload()     { return payload; }
    public String getFromMobile()  { return fromMobile; }
    public String getToMobile()    { return toMobile; }

    public void setType(String t)          { this.type = t; }
    public void setPayload(String p)       { this.payload = p; }
    public void setFromMobile(String f)    { this.fromMobile = f; }
    public void setToMobile(String t)      { this.toMobile = t; }
}
