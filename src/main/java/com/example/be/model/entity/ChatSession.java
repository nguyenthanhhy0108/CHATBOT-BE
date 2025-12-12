package com.example.be.model.entity;

import java.util.List;
import java.util.UUID;

import com.example.be.model.standard.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CHAT_SESSION")
public class ChatSession extends BaseAuditableEntity {

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column()
    private String title;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatMessage> messages;
}
