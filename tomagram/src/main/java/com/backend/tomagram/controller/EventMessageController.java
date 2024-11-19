package com.backend.tomagram.controller;

import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class EventMessageController {
//    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publish(@RequestParam("msg") String message){
//        kafkaTemplate.send("tomagram", message);
    }
}
