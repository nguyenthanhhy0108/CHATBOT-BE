package com.example.be.service.core.interfaces;


import com.example.be.model.dto.service.response.UserDetailsResponse;

public interface UserServiceCore {

  UserDetailsResponse findUserByUsername(String username);
}
