package com.example.be.controller;

import com.example.be.model.dto.facade.response.UserIdResponse;
import com.example.be.model.security.CustomUserDetails;
import com.example.be.model.standard.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  @GetMapping("/user/me")
  public ResponseEntity<ApiResponse<UserIdResponse>> getCurrentUser(
      Authentication authentication,
      HttpServletResponse response
  ) {
    if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails principal)) {
      return ResponseEntity.status(401).build();
    }

    String userId = principal.getUserId().toString();

    // 设置 HttpOnly Cookie，前端 JS 无法读取，但浏览器会自动携带
    ResponseCookie userIdCookie = ResponseCookie.from("user-id", urlEncode(userId))
        .httpOnly(true)
        .path("/")
        .build();

    response.addHeader(HttpHeaders.SET_COOKIE, userIdCookie.toString());

    return ResponseEntity.ok(
        ApiResponse.<UserIdResponse>builder()
            .data(UserIdResponse.builder().userId(userId).build())
            .build()
    );
  }

  private String urlEncode(String value) {
    return java.net.URLEncoder.encode(value, StandardCharsets.UTF_8);
  }
}


