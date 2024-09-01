package com.backend.tomagram.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MySQLDataSourceConfig {
    @Primary
    @Bean(name = "usersDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.users")
    public DataSource usersDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "postsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.posts")
    public DataSource postsDataSource(){
        return DataSourceBuilder.create().build();
    }
}
