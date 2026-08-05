package com.storyverse.community.application;

import com.storyverse.community.domain.Rating;
import com.storyverse.community.domain.event.StoryRatedEvent;
import com.storyverse.community.ports.RatingRepository;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class RateStoryUseCase {
    private final RatingRepository ratingRepository;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public RateStoryUseCase(RatingRepository ratingRepository, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.ratingRepository = ratingRepository;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID storyId, int score) {
        UUID userId = securityContext.requireUserId();
        var existing = ratingRepository.findByUserIdAndStoryId(userId, storyId);
        Rating rating;
        if (existing.isPresent()) {
            rating = existing.get();
            rating.updateScore(score);
        } else {
            rating = Rating.create(UUID.randomUUID(), userId, storyId, score);
        }
        ratingRepository.save(rating);
        eventPublisher.publish(new StoryRatedEvent(rating.getId(), userId, storyId, score, EventMetadata.createNew(userId.toString())));
    }
}
