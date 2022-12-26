package com.example.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
* 웹소켓은 서버와 웹브라우저 간 양방향 전이중 연결
* 클라이언트나 서버가 연결을 닫기 전까지 연결이 계속해서 유지된다.
*  */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        // 클라이언트에서 websocket에 접속하는 Endpoint를 등록
        // withSockJS() -> websocket을 지원하지 않는 경우 fallback 옵션 활성화에 사용

        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS();
    }

    // (추측) 아래 코드는 @SendTo 어노테이션을 사용할 때 적합한 것 같습니다.
//    @Override
//    public void configureMessageBroker(final MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/listen");
//        //registry.setApplicationDestinationPrefixes(); // MessageMapping에 접두어 넣을 수 있음.
//    }
}
