package com.storyverse.creator.adapters;

import com.storyverse.creator.ports.CreatorProfileRepository;
import com.storyverse.shared.security.CreatorVerificationPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Adapter that implements the Story module's CreatorVerificationPort.
 * Bridges the Story module's need to verify creator status with the Creator module's data.
 */
@Component
public class CreatorVerificationAdapter implements CreatorVerificationPort {

    private final CreatorProfileRepository creatorProfileRepository;

    public CreatorVerificationAdapter(CreatorProfileRepository creatorProfileRepository) {
        this.creatorProfileRepository = creatorProfileRepository;
    }

    @Override
    public boolean isActiveCreator(UUID userId) {
        return creatorProfileRepository.findByUserId(userId)
                .map(profile -> profile.isActive())
                .orElse(false);
    }
}
