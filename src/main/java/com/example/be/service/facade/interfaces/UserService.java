package com.example.be.service.facade.interfaces;

import com.example.be.model.dto.facade.response.UserDetailsResponse;

public interface UserService {

  UserDetailsResponse getUserDetails(String username);
}
