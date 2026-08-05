package com.storyverse.administration.application;

import com.storyverse.administration.domain.FeatureFlag;
import com.storyverse.administration.domain.event.FeatureFlagChangedEvent;
import com.storyverse.administration.ports.FeatureFlagRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.domain.exception.ResourceNotFoundException;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ManageFeatureFlagUseCase {
    private final FeatureFlagRepository repository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public ManageFeatureFlagUseCase(FeatureFlagRepository repository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.repository = repository; this.eventPublisher = eventPublisher; this.securityContext = securityContext;
    }

    public List<FeatureFlag> listAll() { return repository.findAll(); }

    @Transactional
    public UUID create(String name, String description) {
        FeatureFlag flag = FeatureFlag.create(UUID.randomUUID(), name, description);
        repository.save(flag);
        return flag.getId();
    }

    @Transactional
    public void enable(UUID flagId) {
        UUID adminId = securityContext.requireUserId();
        FeatureFlag flag = repository.findById(flagId).orElseThrow(() -> new ResourceNotFoundException("FeatureFlag", flagId));
        flag.enable();
        repository.save(flag);
        eventPublisher.publish(new FeatureFlagChangedEvent(flagId, flag.getName(), "ENABLED", EventMetadata.createNew(adminId.toString())));
    }

    @Transactional
    public void disable(UUID flagId) {
        UUID adminId = securityContext.requireUserId();
        FeatureFlag flag = repository.findById(flagId).orElseThrow(() -> new ResourceNotFoundException("FeatureFlag", flagId));
        flag.disable();
        repository.save(flag);
        eventPublisher.publish(new FeatureFlagChangedEvent(flagId, flag.getName(), "DISABLED", EventMetadata.createNew(adminId.toString())));
    }

    public boolean isEnabled(String flagName, UUID userId) {
        return repository.findByName(flagName).map(f -> f.isEnabledForUser(userId)).orElse(false);
    }
}
