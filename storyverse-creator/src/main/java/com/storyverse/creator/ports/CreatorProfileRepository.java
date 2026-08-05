package com.storyverse.creator.ports;

import com.storyverse.creator.domain.CreatorProfile;
import java.util.Optional;
import java.util.UUID;

public interface CreatorProfileRepository {
    Optional<CreatorProfile> findById(UUID id);
    Optional<CreatorProfile> findByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
    CreatorProfile save(CreatorProfile profile);
    void delete(UUID id);
}
