package com.backend.tomagram.config.application;

import com.backend.tomagram.config.filter.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // final to be automatically injected by spring by DI
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider; //a custom BEAN is created in ApplicationConfig

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
                .csrf(AbstractHttpConfigurer::disable) // Check Notion my CSRF Documentation
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        "/api/auth/**",
                                        "/v2/api-docs",
                                        "/v3/api-docs/**",
                                        "/configuration/ui",
                                        "/swagger-resources/**",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "swagger-ui/**"
                                ).permitAll()// make login, register routes as public routes
                                .anyRequest().authenticated()) // another route needs to be authenticated with a valid JWT
                .sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // our application implements stateless JWT authentication
                .authenticationProvider(authenticationProvider) // authenticationProvider determines how validate authentication process
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                // our custom JwtAuthenticationFilter will run before the built-in UsernamePasswordAuthenticationFilter
                // to verify, validate JWT

        return http.build();
    }
    /*
    The authenticationProvider method in your Spring Security configuration is responsible for defining how authentication is performed in your application.
    Specifically, it configures the strategy and components that will be used to verify user credentials and load user-specific data (such as roles and authorities).
    When a user tries to log in, typically by sending a POST request to an authentication endpoint (e.g., /login or /api/auth/authenticate)
    */
}
