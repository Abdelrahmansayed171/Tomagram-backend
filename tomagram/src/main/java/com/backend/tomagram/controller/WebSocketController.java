package com.backend.tomagram.controller;

import com.backend.tomagram.models.Message;
import com.backend.tomagram.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final ChatService chatService;

    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/{conversationId}") // response will be sent into channel
    public Message sendMessage(@DestinationVariable String conversationId, Message message) throws Exception{
        return chatService.sendMessage(conversationId, message.getSender(), message.getContent());
    }

}
