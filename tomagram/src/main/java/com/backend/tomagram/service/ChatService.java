package com.backend.tomagram.service;

import com.backend.tomagram.models.Conversation;
import com.backend.tomagram.models.Message;
import com.backend.tomagram.repository.ConversationRepository;
import com.backend.tomagram.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {
    private ConversationRepository conversationRepo;
    private MessageRepository messageRepo;

    public Conversation startPrivateConversation(String user1, String user2){
        Optional<Conversation> conversation = conversationRepo.
                findByParticipantsContaining(user1)
                .stream()
                .filter(c -> c.getParticipants().contains(user2))
                .findFirst();

        // if conversation already exists, so we'll return it
        if(conversation.isPresent()){
            return conversation.get();
        }

        // Create new conversation if it does not exist
        Conversation starterConversation = Conversation
                .builder()
                .participants(List.of(user1,user2))
                .build();

        return conversationRepo.save(starterConversation);
    }

    public Message sendMessage(String conversationId, String content, String sender){
        Message message = Message.builder()
                .conversationId(conversationId)
                .sender(sender)
                .content(content)
                .build();
        return messageRepo.save(message);
    }

    public List<Message> getMessages(String conversationId, LocalDateTime after){
        if(after == null){
            return messageRepo.findByConversationId(conversationId);
        }
        return messageRepo.findByConversationIdAndSentAtAfter(conversationId, after);
    }

}
