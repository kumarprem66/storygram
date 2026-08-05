package com.storyverse.ai.ports;

import com.storyverse.ai.domain.AIRequest;
import java.util.*;

public interface AIRequestRepository {
    AIRequest save(AIRequest request);
    Optional<AIRequest> findById(UUID id);
    List<AIRequest> findByUserId(UUID userId);
}
