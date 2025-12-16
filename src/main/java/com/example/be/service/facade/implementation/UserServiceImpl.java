package com.example.be.service.facade.implementation;

import com.example.be.mapper.facade.UserMapper;
import com.example.be.model.dto.facade.response.UserDetailsResponse;
import com.example.be.service.core.interfaces.UserServiceCore;
import com.example.be.service.facade.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

  private final UserServiceCore userServiceCore;

  private final UserMapper userMapper;

  @Override
  public UserDetailsResponse getUserDetails(String username) {
    return this.userMapper.toUserDetailsResponse(
        this.userServiceCore.findUserByUsername(username)
    );
  }
}
