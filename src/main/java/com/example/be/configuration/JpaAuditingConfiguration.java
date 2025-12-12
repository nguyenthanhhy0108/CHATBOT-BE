package com.example.be.configuration;

import com.example.be.model.entity.User;
import com.example.be.repository.interfaces.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    private static final ThreadLocal<UUID> currentUserId = new ThreadLocal<>();

    @Bean
    public AuditorAware<UUID> auditorProvider(UserRepository userRepository) {
        return new SpringSecurityAuditorAware(userRepository);
    }

    public static void setCurrentUserId(UUID userId) {
        currentUserId.set(userId);
    }

    public static void clearCurrentUserId() {
        currentUserId.remove();
    }

    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    private static class SpringSecurityAuditorAware implements AuditorAware<UUID> {
        private final UserRepository userRepository;

        @NotNull
        @Override
        public Optional<UUID> getCurrentAuditor() {
            // First check ThreadLocal for manually set userId (for permitAll endpoints)
            UUID threadLocalUserId = currentUserId.get();
            if (threadLocalUserId != null) {
                return Optional.of(threadLocalUserId);
            }

            // Then check SecurityContext for authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            
            String username = authentication.getName();
            if (username == null || username.isBlank()) {
                return Optional.empty();
            }
            
            return userRepository.findByUsername(username)
                    .map(User::getId);
        }
    }
}

