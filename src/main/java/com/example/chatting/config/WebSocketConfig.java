package com.example.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
* 웹소켓은 서버와 웹브라우저 간 양방향 전이중 연결
* 클라이언트나 서버가 연결을 닫기 전까지 연결이 계속해서 유지된다.
*
*  */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS(); // 브라우저가 WebSocket을 지원하지 않는 경우 대체 메시징 옵션 사용
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/listen");
        //registry.setApplicationDestinationPrefixes(); // MessageMapping에 접두어 넣을 수 있음.
    }
}
