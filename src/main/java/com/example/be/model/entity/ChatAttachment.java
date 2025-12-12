package com.example.be.model.entity;

import com.example.be.model.standard.BaseAuditableEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "CHAT_ATTACHMENT")
public class ChatAttachment extends BaseAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private ChatMessage message;

    @Column(name = "FILE_NAME", nullable = false, length = 255)
    private String fileName;

    @Column(name = "FILE_TYPE", length = 50)
    private String fileType;

    @Column(name = "FILE_URL", nullable = false, length = 500)
    private String fileUrl;
}
