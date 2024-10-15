package com.backend.tomagram.config.application;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic feedTopic() {
        return TopicBuilder.name("tomagram-feed").build();
    }

    @Bean
    public NewTopic updateTopic() {
        return TopicBuilder.name("tomagram-update").build();
    }

    @Bean
    public NewTopic seenTopic() {
        return TopicBuilder.name("tomagram-seen").build();
    }
}
