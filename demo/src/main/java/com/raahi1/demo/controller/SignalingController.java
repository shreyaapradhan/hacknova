package com.raahi1.demo.controller;

import com.raahi1.demo.dto.SignalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * WebSocket (STOMP) controller for WebRTC signaling.
 *
 * Flow:
 *  1. User page connects to WebSocket, sends SDP offer  → /app/signal
 *  2. Server relays to trusted contact at              → /topic/signal/{userMobile}
 *  3. Trusted contact sends SDP answer back            → /app/signal
 *  4. Server relays answer to user at                  → /topic/signal/{trustedMobile}
 *  5. ICE candidates exchanged same way
 */
@Controller
public class SignalingController {

    private final SimpMessagingTemplate messaging;

    @Autowired
    public SignalingController(SimpMessagingTemplate messaging) {
        this.messaging = messaging;
    }

    /**
     * Receives a signal from either user or trusted contact,
     * and relays it to the intended recipient's topic.
     */
    @MessageMapping("/signal")
    public void relaySignal(SignalDTO signal) {
        // Relay to the topic that the recipient is subscribed to
        // The recipient subscribes to /topic/signal/{their own mobile}
        String destination = "/topic/signal/" + signal.getToMobile();
        messaging.convertAndSend(destination, signal);
    }
}
