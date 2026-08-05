package com.storyverse.recommendation.application;

import com.storyverse.recommendation.domain.RecommendationFeedback;
import com.storyverse.recommendation.ports.RecommendationFeedbackRepository;
import com.storyverse.shared.security.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class SubmitFeedbackUseCase {
    private final RecommendationFeedbackRepository repository;
    private final SecurityContext securityContext;

    public SubmitFeedbackUseCase(RecommendationFeedbackRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
    }

    @Transactional
    public void execute(UUID storyId, String feedbackType) {
        UUID userId = securityContext.requireUserId();
        RecommendationFeedback feedback = RecommendationFeedback.create(UUID.randomUUID(), userId, storyId, feedbackType);
        repository.save(feedback);
    }
}
