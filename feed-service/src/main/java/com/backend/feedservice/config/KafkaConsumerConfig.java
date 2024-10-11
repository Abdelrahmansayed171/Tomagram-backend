package com.backend.feedservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.xml.sax.ErrorHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}") // from application.properties
    private String bootstrapServers;

    public Map<String, Object> consumerConfig(){
        HashMap<String, Object> props = new HashMap<>();
        // setup bootstrap server connection with the producer
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // define key, value data types to be stored in kafka
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // use Json Deserializer to deserialize objects from JSON
        // JsonDeserializer.class.getName() refers to the class name of the JSON object you want to parse.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>(Object.class));
    }

    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, Object>> factory(
            ConsumerFactory<String, Object> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }


}
