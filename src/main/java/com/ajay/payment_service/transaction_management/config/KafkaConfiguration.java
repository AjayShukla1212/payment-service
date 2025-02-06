package com.ajay.payment_service.transaction_management.config;

import com.ajay.payment_service.transaction_management.constants.Constants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {
    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(Constants.TOPIC).build();
    }
}