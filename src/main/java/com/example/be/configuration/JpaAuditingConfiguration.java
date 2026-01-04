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
    private static final ThreadLocal<Boolean> lookupInProgress = new ThreadLocal<>();

    @Bean
    public AuditorAware<UUID> auditorProvider(UserRepository userRepository) {
        return new SpringSecurityAuditorAware(userRepository);
    }

    public static void setCurrentUserId(UUID userId) {
        currentUserId.set(userId);
    }

    public static void clearCurrentUserId() {
        currentUserId.remove();
        lookupInProgress.remove();
    }

    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    private static class SpringSecurityAuditorAware implements AuditorAware<UUID> {
        private final UserRepository userRepository;

        @NotNull
        @Override
        public Optional<UUID> getCurrentAuditor() {
            // First check ThreadLocal for manually set userId
            UUID threadLocalUserId = currentUserId.get();
            if (threadLocalUserId != null) {
                return Optional.of(threadLocalUserId);
            }

            // Prevent infinite recursion
            if (Boolean.TRUE.equals(lookupInProgress.get())) {
                return Optional.empty();
            }

            try {
                lookupInProgress.set(true);

                // Get authentication from SecurityContext
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null || !authentication.isAuthenticated()) {
                    return Optional.empty();
                }

                String username = authentication.getName();
                if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
                    return Optional.empty();
                }

                // Look up user and cache the result
                Optional<UUID> userId = userRepository.findByUsername(username)
                    .map(User::getId);

                // Cache the userId for subsequent auditing calls in the same transaction
                userId.ifPresent(currentUserId::set);

                return userId;

            } finally {
                lookupInProgress.remove();
            }
        }
    }
}