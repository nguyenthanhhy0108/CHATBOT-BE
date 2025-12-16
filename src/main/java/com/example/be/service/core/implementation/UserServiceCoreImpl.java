package com.example.be.service.core.implementation;

import com.example.be.mapper.core.UserMapperCore;
import com.example.be.model.dto.service.response.UserDetailsResponse;
import com.example.be.repository.interfaces.UserRepository;
import com.example.be.service.core.interfaces.UserServiceCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceCoreImpl implements UserServiceCore {

  private final UserRepository userRepository;

  private final UserMapperCore userMapperCore;

  @Override
  public UserDetailsResponse findUserByUsername(String username) {
    return this.userMapperCore.toUserDetailsResponse(
        this.userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"))
    );
  }
}
