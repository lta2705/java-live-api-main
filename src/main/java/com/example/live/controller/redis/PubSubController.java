package com.example.live.controller.redis;

import com.example.live.service.redis.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/pubsub")
public class PubSubController {
        @Autowired
        private PublisherService publisherService;

        @PostMapping("/send")
        public String sendMessage(@RequestParam String message) {
            publisherService.publish(message);
            return "Message published: " + message;
        }

}
