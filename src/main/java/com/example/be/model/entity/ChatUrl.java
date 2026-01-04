package com.example.be.model.entity;

import com.example.be.model.standard.BaseAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@Entity(name = "CHAT_URL")
@NoArgsConstructor
@AllArgsConstructor
public class ChatUrl extends BaseAuditableEntity {

  @Column(name = "URL", nullable = false)
  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MESSAGE_ID", nullable = false)
  private ChatMessage message;
}
