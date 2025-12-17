package com.example.be.controller;

import com.example.be.model.dto.facade.request.ChatSessionCreateRequest;
import com.example.be.model.dto.facade.response.ChatSessionResponse;
import com.example.be.model.standard.ApiResponse;
import com.example.be.service.facade.interfaces.ChatSessionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatSessionController {

  private final ChatSessionService chatSessionService;

  @GetMapping("/user/chats")
  public ResponseEntity<ApiResponse<List<ChatSessionResponse>>> getAllChatSessionsForUser() {
    return ResponseEntity.ok(
        ApiResponse.<List<ChatSessionResponse>>builder()
            .data(this.chatSessionService.getAllChatSessionsOfUser())
            .build()
    );
  }

  @PostMapping("/user/chats")
  public ResponseEntity<ApiResponse<ChatSessionResponse>> createChatSessionForUser(
      @Valid @RequestBody ChatSessionCreateRequest request
  ) {
    return ResponseEntity.ok(
        ApiResponse.<ChatSessionResponse>builder()
            .data(this.chatSessionService.createChatSession(request))
            .build()
    );
  }
}
