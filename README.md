### 스프링 웹 소켓 학습을 위한 채팅 프로젝트

## 요구사항

### 채팅
1. 사용자는 채팅에 참여할 수 있습니다.
2. 채팅에는 최대 3명의 사용자가 참여할 수 있습니다.

### 내용
1. /chatting : 채팅 html 렌더링
2. /chat : 웹소켓 연결 및 메시지 전달
3. /listen : 서버에서 메세지를 받았을 때, 받은 메세지를 전달할 URL 

### 코드
### Configuration
1. /chat을 엔드포인트로 지정하고, /listen을 메세지 브로커로 설정합니다.
``` Java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/chat");
        registry.addEndpoint("/chat").withSockJS(); 
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/listen");
        //registry.setApplicationDestinationPrefixes(); 
    }
}
```



### Controller
1. /chat으로 들어오는 메세지를 수신합니다. 이 때 받는 형식에 맞추어서 chatMessageDto를 구성합니다.
2. /listen으로 subscribe 하고있는 웹소켓에게 형식에 맞추어서 데이터를 전달합니다.

``` Java
   @MessageMapping("/chat")
   @SendTo("/listen")
   public ChatMessageDto chatMessageDto(ChatMessageDto chatMessageDto) {
       /*
       클라이언트 측에서 subscribe 해둔 url로 sendTo를 지정해 두면, 해당 url로 메시지가 전송됩니다.
       */
       System.out.println("메시지가 도착했습니다." + chatMessageDto.getMessage());
       return new ChatMessageDto(chatMessageDto.getMessage());
    }
```

### 구현 Log
- SendTo 어노테이션을 제거하고, SimpMessagingTemplate을 직접 사용하여 방을 구분하였습니다.
  - 방 코드가 2c14인 경우, 클라이언트는 /listen/2c14를 subscribe 하는 방식입니다.
``` Java

private final SimpMessagingTemplate template;

@MessageMapping("/chat")
public void chattingWith(ChatMessageDto chatMessageDto) {
    /*
        클라이언트 측에서 subscribe 해둔 url로 sendTo를 지정해 두면, 해당 url로 메시지가 전송됩니다.
        @SendTo("/listen")

        다만, SendTo 어노테이션은 url을 동적으로 설정할 수 없습니다.
        url을 유동적으로 설정하기 위해서는 SimpMessagingTemplate를 사용합니다.
    */

    template.convertAndSend("/listen/" + chatMessageDto.getRoomId(),
            new ChatMessageDto(chatMessageDto.getRoomId(), chatMessageDto.getMessage()));
    System.out.println(chatMessageDto.getRoomId() + "번 방에 메시지가 도착했습니다 : " + chatMessageDto.getMessage());
}
```