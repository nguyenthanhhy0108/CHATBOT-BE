package com.example.be.repository.interfaces;

import com.example.be.model.entity.ChatMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    java.util.List<ChatMessage> findBySession_IdOrderByCreatedAtAsc(UUID sessionId);
}

