package com.backend.tomagram.config.datasource;


import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Primary
    @Bean(name = "interactionsMongoTemplate")
    public MongoTemplate mongoTemplate1(@Value("${spring.data.mongodb.interactions.uri}") String uri) {
        return new MongoTemplate(MongoClients.create(uri), "interactions_db");
    }


}

