package com.example.be.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.be.model.standard.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CHAT_MESSAGE")
public class ChatMessage extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SESSION_ID", nullable = false)
    private ChatSession session;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "SENDER", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatAttachment> attachments;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ChatUrl> urls;

    public void addUrl(ChatUrl url) {
        if (this.urls == null) {
            this.urls = new ArrayList<>();
        }
        urls.add(url);
        url.setMessage(this);
    }

    public void removeUrl(ChatUrl url) {
        urls.remove(url);
        url.setMessage(null);
    }

}
