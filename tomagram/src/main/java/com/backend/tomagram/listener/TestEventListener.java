package com.backend.tomagram.listener;

import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestEventListener {
    /*@KafkaListener(topics = "tomagram", groupId = "groupId")
    void listener(String data){
        System.out.println("Listener received: " + data + " ym3alem..!!" );
    }*/
}
