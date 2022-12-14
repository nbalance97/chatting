package com.example.chatting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatTemplateController {

    @GetMapping("/chatting")
    public String chat() {

        return "chattingroom"; // ThymeLeaf 의존 추가해주어야 template/chattingroom.html이 렌더링
    }
}
