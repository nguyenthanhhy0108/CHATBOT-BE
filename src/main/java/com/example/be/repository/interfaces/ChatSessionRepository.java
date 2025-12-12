package com.example.be.repository.interfaces;

import com.example.be.model.entity.ChatSession;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID> {

    @NotNull Optional<ChatSession> findById(@NotNull UUID id);

}
