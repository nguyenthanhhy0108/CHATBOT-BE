package com.example.be.controller;

import com.example.be.model.dto.facade.request.ChatRequest;
import com.example.be.model.dto.facade.response.ChatMessageHistoryItem;
import com.example.be.model.dto.facade.response.ChatResponse;
import com.example.be.model.standard.ApiResponse;
import com.example.be.service.facade.interfaces.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<ChatResponse>> chat(
            @Valid @RequestBody ChatRequest chatRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ChatResponse>builder()
                        .data(this.chatService.chat(chatRequest))
                        .build()
        );
    }

    @GetMapping("/chat/{sessionId}/messages")
    public ResponseEntity<ApiResponse<java.util.List<ChatMessageHistoryItem>>> getChatHistory(
            @PathVariable String sessionId
    ) {
        return ResponseEntity.ok(
                ApiResponse.<java.util.List<ChatMessageHistoryItem>>builder()
                        .data(this.chatService.getChatHistory(sessionId))
                        .build()
        );
    }

}
