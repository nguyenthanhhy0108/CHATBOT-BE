package com.example.be.helper;

import com.example.be.model.security.CustomUserDetails;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("security")
public class SecurityHelper {

  public UUID getUserId() {
    Authentication auth =
        SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
      return null;
    }

    return ((CustomUserDetails) auth.getPrincipal()).getUserId();
  }

  public boolean hasUserId() {
    return getUserId() != null;
  }
}