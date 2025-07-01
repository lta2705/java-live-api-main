package com.example.live.config;

import jakarta.annotation.PostConstruct;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import java.util.Map;

@Configuration
public class JmsConfiguration {

    static Logger logger = LoggerFactory.getLogger(JmsConfiguration.class);

    @Value("${spring.artemis.url}")
    private String brokerUrl;

    @Value("${spring.artemis.user}")
    private String brokerUsername;

    @Value("${spring.artemis.password}")
    private String brokerPassword;

    @PostConstruct
    public void init() {
        logger.info("Jms broker URL: {}", brokerUrl);
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUser(brokerUsername);
        connectionFactory.setPassword(brokerPassword);

        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setSessionTransacted(true);
        template.setDeliveryPersistent(true);

        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("2-10");
        factory.setPubSubDomain(false);
        factory.setSessionTransacted(true);
        factory.setAutoStartup(true);
        factory.setErrorHandler(t -> logger.error("JMS Error: ", t));
        factory.setReceiveTimeout(5000L); // Set a timeout for receiving messages
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return factory;
    }

    @EventListener
    public void handleContextRefreshedEvent(ContextRefreshedEvent event) {
        logger.info("Application started. JMS Listener should be active.");

        Map<String, DefaultMessageListenerContainer> containers = event.getApplicationContext()
                .getBeansOfType(DefaultMessageListenerContainer.class);

        for (Map.Entry<String, DefaultMessageListenerContainer> entry : containers.entrySet()) {
            logger.info("JMS Listener [{}] is listening on: {}", entry.getKey(), entry.getValue().getDestination());
        }
    }
}
