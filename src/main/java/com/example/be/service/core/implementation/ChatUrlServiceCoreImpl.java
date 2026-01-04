package com.example.be.service.core.implementation;

import com.example.be.mapper.core.ChatUrlMapperCore;
import com.example.be.model.dto.service.request.ChatUrlCreationRequest;
import com.example.be.model.entity.ChatUrl;
import com.example.be.repository.interfaces.ChatUrlRepository;
import com.example.be.service.core.interfaces.ChatUrlServiceCore;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatUrlServiceCoreImpl implements ChatUrlServiceCore {

  private final ChatUrlRepository chatUrlRepository;

  private final ChatUrlMapperCore chatUrlMapperCore;

  @Override
  public List<ChatUrl> createChatUrlEntity(List<ChatUrlCreationRequest> chatUrlCreationRequests) {
    return this.chatUrlRepository.saveAllAndFlush(
            chatUrlCreationRequests.stream().map(chatUrlMapperCore::toEntity).toList());
  }
}
