package com.example.be.mapper.core;

import com.example.be.model.dto.service.response.UserDetailsResponse;
import com.example.be.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapperCore {

  public abstract UserDetailsResponse toUserDetailsResponse(User user);
}
