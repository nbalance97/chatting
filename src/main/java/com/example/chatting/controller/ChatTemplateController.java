package com.example.chatting.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.chatting.controller.dto.ChatMessageDto;

@Controller
public class ChatTemplateController {

    @GetMapping("/chatting")
    public String chat() {

        return "chattingroom"; // ThymeLeaf 의존 추가해주어야 template/chattingroom.html이 렌더링
    }

    @MessageMapping("/chat")
    @SendTo("/listen")
    public ChatMessageDto chattingWith(ChatMessageDto chatMessageDto) {
        /*
            클라이언트 측에서 subscribe 해둔 url로 sendTo를 지정해 두면, 해당 url로 메시지가 전송됩니다.
        */
        System.out.println("메시지가 도착했습니다." + chatMessageDto.getMessage());
        return new ChatMessageDto(chatMessageDto.getMessage());
    }
}
