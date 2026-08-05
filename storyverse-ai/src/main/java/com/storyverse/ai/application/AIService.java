package com.storyverse.ai.application;

import com.storyverse.ai.domain.AIRequest;
import com.storyverse.ai.domain.event.AIResponseGeneratedEvent;
import com.storyverse.ai.domain.vo.*;
import com.storyverse.ai.ports.*;
import com.storyverse.infrastructure.event.EventPublisher;
import com.storyverse.shared.domain.event.EventMetadata;
import com.storyverse.shared.security.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.UUID;

/**
 * Main AI service used by all business modules.
 * Handles routing, caching, cost tracking, and provider abstraction.
 */
@Service
public class AIService {
    private static final Logger log = LoggerFactory.getLogger(AIService.class);
    private final AIModelPort modelPort;
    private final AIRequestRepository requestRepository;
    private final AICachePort cachePort;
    private final EventPublisher eventPublisher;
    private final SecurityContext securityContext;

    public AIService(AIModelPort modelPort, AIRequestRepository requestRepository,
                     AICachePort cachePort, EventPublisher eventPublisher, SecurityContext securityContext) {
        this.modelPort = modelPort;
        this.requestRepository = requestRepository;
        this.cachePort = cachePort;
        this.eventPublisher = eventPublisher;
        this.securityContext = securityContext;
    }

    public String execute(AITaskType taskType, String input, int maxTokens) {
        UUID userId = securityContext.requireUserId();

        // Check cache for deterministic tasks
        String cacheKey = taskType.name() + ":" + input.hashCode();
        var cached = cachePort.get(cacheKey);
        if (cached.isPresent()) {
            log.debug("AI cache hit for task={}", taskType);
            return cached.get();
        }

        AIRequest request = AIRequest.create(UUID.randomUUID(), userId, taskType, input);
        request.start(AIProvider.OPENAI); // Router would select provider in production

        AIModelResponse response = modelPort.invoke(input, taskType, maxTokens);

        if (response.success()) {
            request.complete(response.content(), response.inputTokens(), response.outputTokens(), response.latencyMs());
            cachePort.put(cacheKey, response.content(), 3600);
            eventPublisher.publish(new AIResponseGeneratedEvent(request.getId(), userId, taskType.name(),
                    request.getProvider().name(), response.inputTokens() + response.outputTokens(),
                    EventMetadata.createNew(userId.toString())));
        } else {
            request.fail(response.errorMessage());
            log.error("AI request failed: task={} error={}", taskType, response.errorMessage());
        }

        requestRepository.save(request);
        return response.success() ? response.content() : null;
    }
}
