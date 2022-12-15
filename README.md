### 스프링 웹 소켓 학습을 위한 채팅 프로젝트

## 요구사항

### 채팅방
1. 사용자는 채팅방을 만들 수 있습니다.
2. 사용자는 현재 열려있는 채팅방을 조회할 수 있습니다.

### 채팅
1. 사용자는 채팅에 참여할 수 있습니다.
2. 채팅에는 최대 3명의 사용자가 참여할 수 있습니다.

### 내용
1. /chatting : 채팅 html 렌더링
2. /chat : 웹소켓 연결 및 메시지 전달
3. /listen : 서버에서 메세지를 받았을 때, 받은 메세지를 전달할 URL 

### 코드
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
