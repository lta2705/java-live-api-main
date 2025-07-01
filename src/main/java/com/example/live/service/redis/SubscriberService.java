package com.example.live.service.redis;

import org.springframework.stereotype.Component;

@Component
public class SubscriberService {

    public void onMessage(String message, String channel) {
        System.out.println("[Redis SUBSCRIBE] Received on channel [" + channel + "]: " + message);
    }
}