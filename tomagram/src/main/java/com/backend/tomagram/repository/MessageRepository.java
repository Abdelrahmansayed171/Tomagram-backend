package com.backend.tomagram.repository;

import com.backend.tomagram.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationId(String conversationId);
    List<Message> findByConversationIdAndSentAtAfter(String conversationId, LocalDateTime after);
}
