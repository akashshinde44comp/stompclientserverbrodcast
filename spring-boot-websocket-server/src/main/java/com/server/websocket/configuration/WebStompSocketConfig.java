package com.server.websocket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebStompSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic","/queue");
    config.setApplicationDestinationPrefixes("/app/server");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    //registry.addEndpoint("").addInterceptors(new HttpHandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
    registry.addEndpoint("/server-ws").setAllowedOrigins("*").withSockJS();
  
  }
}