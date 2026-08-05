package com.storyverse.recommendation.application;

import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.recommendation.domain.RecommendationItem;
import com.storyverse.recommendation.domain.event.RecommendationServedEvent;
import com.storyverse.recommendation.domain.vo.RecommendationType;
import com.storyverse.recommendation.ports.RecommendationEngine;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class GetRecommendationsUseCase {
    private final RecommendationEngine engine;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public GetRecommendationsUseCase(RecommendationEngine engine, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.engine = engine;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    public List<RecommendationItem> execute(String type, int limit) {
        UUID userId = securityContext.requireUserId();
        RecommendationType recType = RecommendationType.valueOf(type.toUpperCase());
        List<RecommendationItem> items = switch (recType) {
            case PERSONALIZED -> engine.getPersonalized(userId, limit);
            case TRENDING -> engine.getTrending("WEEKLY", limit);
            case NEW_RELEASES -> engine.getNewReleases(limit);
            default -> engine.getPersonalized(userId, limit);
        };
        eventPublisher.publish(new RecommendationServedEvent(UUID.randomUUID(), userId, type, items.size(),
                EventMetadata.createNew(userId.toString())));
        return items;
    }
}
