package com.example.be.mapper.facade;

import com.example.be.model.dto.facade.response.UserDetailsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

  public abstract UserDetailsResponse toUserDetailsResponse(
      com.example.be.model.dto.service.response.UserDetailsResponse userDetailsResponse);
}
