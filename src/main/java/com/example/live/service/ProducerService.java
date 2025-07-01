//package com.example.live.service;
//
//import com.example.live.config.CommonProperties;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//public class ProducerService implements Runnable {
//
//    static Logger logger = LoggerFactory.getLogger(ProducerService.class);
//
//    CompletableFuture<?> future = new CompletableFuture<>();
//    private final JmsTemplate jmsTemplate;
//
//    @Autowired
//    public ProducerService(JmsTemplate jmsTemplate, CommonProperties commonProperties1) {
//        this.jmsTemplate = jmsTemplate;
//    }
//
//    @Override
//    public void run(){
//        // Logic for the producer service
//         try{
//             for(int i = 0; i < 1000; i++) {
//                 String message = "Message " + i;
//                 logger.info("Sending message: {}", message);
//                 jmsTemplate.convertAndSend(CommonProperties.QUEUE_NAME, message);
//             }
//         }catch(Exception e){
//            logger.error("Error in ProducerService: {}", e.getMessage());
//         }
//        // Add your producer logic here
//    }
//}
