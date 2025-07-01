package com.example.live.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    // This class is intended to consume messages from a queue or topic.
    // You can implement methods to receive messages, process them, and handle any necessary logic.
    static Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    // Example method to receive a message
    @JmsListener(destination = "${spring.artemis.queue-name}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(String message) {
        // Logic to process the received message
        logger.info("Received message: {}", message);

    }

    // Add more methods as needed for your application logic
}
