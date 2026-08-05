package com.storyverse.creator.application;

import com.storyverse.creator.domain.CreatorProfile;
import com.storyverse.creator.ports.CreatorProfileRepository;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class GetCreatorProfileUseCase {
    private final CreatorProfileRepository repository;

    public GetCreatorProfileUseCase(CreatorProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public CreatorProfile execute(UUID creatorId) {
        return repository.findById(creatorId)
                .orElseThrow(() -> new ResourceNotFoundException("CreatorProfile", creatorId));
    }

    @Transactional(readOnly = true)
    public CreatorProfile executeByUserId(UUID userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("CreatorProfile", userId));
    }
}
