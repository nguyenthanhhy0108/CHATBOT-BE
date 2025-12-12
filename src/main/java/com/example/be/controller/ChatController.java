package com.example.be.controller;

import com.example.be.model.dto.facade.request.ChatMessageRequest;
import com.example.be.model.dto.facade.response.ChatMessageResponse;
import com.example.be.model.standard.ApiResponse;
import com.example.be.service.facade.interfaces.ChatService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ResponseEntity<ApiResponse<ChatMessageResponse>> chat(
            @Valid @RequestBody ChatMessageRequest chatMessageRequest
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ChatMessageResponse>builder()
                        .data(this.chatService.chat(chatMessageRequest))
                        .build()
        );
    }

}
