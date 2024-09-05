package com.backend.tomagram.config.database;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Primary
    @Bean(name = "usersEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("usersDataSource") DataSource dataSource) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"); // Specify the dialect

        // Enable SQL logging
        props.put("hibernate.show_sql", true); // Log the SQL statements
        props.put("hibernate.format_sql", true); // Format SQL statements for better readability
        props.put("hibernate.use_sql_comments", true); // Optional: Add comments to SQL statements for easier tracing
        props.put("hibernate.type", "TRACE"); // Enable logging for binding parameters to SQL queries
        return builder
                .dataSource(dataSource)
                .packages("com.backend.tomagram.models.users") // Adjust package to your entity package
                .persistenceUnit("users")
                .properties(props)
                .build();
    }

    @Primary
    @Bean(name = "usersTransactionManager")
    public PlatformTransactionManager usersTransactionManager(
            @Qualifier("usersEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "postsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postsEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("postsDataSource") DataSource dataSource) {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect"); // Specify the dialect
        return builder
                .dataSource(dataSource)
                .packages("com.backend.tomagram.models.posts")
                .persistenceUnit("posts")
                .properties(props)
                .build();
    }

    @Bean(name = "postsTransactionManager")
    public PlatformTransactionManager postsTransactionManager(
            @Qualifier("postsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
