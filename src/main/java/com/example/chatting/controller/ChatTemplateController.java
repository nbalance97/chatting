package com.example.chatting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.chatting.controller.dto.ChatMessageDto;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class ChatTemplateController {

    private final SimpMessagingTemplate template;

    @GetMapping("/chatting")
    public String chat() {

        return "chattingroom"; // ThymeLeaf 의존 추가해주어야 template/chattingroom.html이 렌더링
    }

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
}
