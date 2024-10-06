package com.backend.tomagram.config.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//      Enables a simple message broker that supports broadcasting messages to a specific topic.
//      This allows multiple clients to subscribe to a topic and receive messages sent to that topic.
        registry.enableSimpleBroker("/topic"); // CHANNEL

//      Sets the prefix for message destinations.
//      This prefix will be prepended to message destinations specified in the client's WebSocket messages.
        registry.setApplicationDestinationPrefixes("/app");
    }

    // This method registers WebSocket endpoints that clients can connect to
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //  Adds a WebSocket endpoint at the path /chat.
        registry.addEndpoint("/chat");
    }
}
