package com.storyverse.ai.adapters;

import com.storyverse.ai.domain.vo.AITaskType;
import com.storyverse.ai.ports.AIModelPort;
import com.storyverse.ai.ports.AIModelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stub AI model adapter for dev/test. Returns placeholder responses.
 * Production replaces with OpenAI/Gemini/Anthropic adapters.
 */
@Component
public class StubAIModelAdapter implements AIModelPort {
    private static final Logger log = LoggerFactory.getLogger(StubAIModelAdapter.class);

    @Override
    public AIModelResponse invoke(String prompt, AITaskType taskType, int maxTokens) {
        log.info("[AI STUB] task={} maxTokens={} inputLength={}", taskType, maxTokens, prompt.length());
        String stubResponse = "[AI Stub Response for " + taskType.name() + "] - Production will use real AI provider.";
        return AIModelResponse.success(stubResponse, prompt.length() / 4, stubResponse.length() / 4, 50);
    }
}
