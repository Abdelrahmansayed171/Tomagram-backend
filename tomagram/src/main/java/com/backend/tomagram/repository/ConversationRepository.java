package com.backend.tomagram.repository;

import com.backend.tomagram.models.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends MongoRepository <Conversation, String> {
    List<Conversation> findByParticipantsContaining(String username);
}
