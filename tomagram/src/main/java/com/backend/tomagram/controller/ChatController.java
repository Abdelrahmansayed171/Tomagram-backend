package com.backend.tomagram.controller;

import com.backend.tomagram.models.Conversation;
import com.backend.tomagram.models.Message;
import com.backend.tomagram.service.ChatService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/start")
    public ResponseEntity<Conversation> startConversation(@RequestParam("usr1") String usr1, @RequestParam("usr2") String usr2){
        return ResponseEntity.ok(chatService.startPrivateConversation(usr1, usr2));
    }

    @PostMapping("/send/{conversationId}")
    public ResponseEntity<Message> sendMessage(@PathVariable String conversationId,
                                               @RequestParam("content") String content,
                                               @RequestParam("sender") String sender){
        return ResponseEntity.ok(chatService.sendMessage(conversationId, content, sender));
    }

    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String conversationId,
                                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after){
        return ResponseEntity.ok(chatService.getMessages(conversationId,after));
    }

    @PutMapping("/seen/{conversationId}/{username}")
    public ResponseEntity<List<Message>> markConversationAsRead(@PathVariable String conversationId,
                                                                @PathVariable String username){
        return ResponseEntity.ok(chatService.markMessageAsRead(conversationId, username));
    }

}
