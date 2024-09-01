package com.backend.tomagram.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerAliasConfig {

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("usersTransactionManager") PlatformTransactionManager transactionManager) {
        return transactionManager;
    }
}